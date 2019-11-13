package wifi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by DWF on 2019/2/21.
 */

public class WifiConnect extends DWFwifi{
    private String targetWifi = "null";
    private String ipaddress = "123456";
    private WifiInfo wifiInfo;
    private List<ScanResult> scanResults;
    private WifiManager wifiManager = null;
    private WifiConfiguration config;
    private DhcpInfo dhcpInfo;
    private String WIFI_Name = " ";
    private String PASS_WORD = " ";
    private Handler handler;
    protected static final int NONE_PASSWORD = 0;
    protected static final int WEP = 1;
    protected static final int WPA = 2;
    private Context context;
    public WifiConnect(WifiManager wifiManager, List<ScanResult> scanResults, String WIFI_Name, String PASS_WORD, Context context){
        super(context);
        this.wifiManager=wifiManager;
        this.scanResults=scanResults;
        this.WIFI_Name=WIFI_Name;
        this.PASS_WORD=PASS_WORD;
        this.context=context;
    }

    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    public WifiConfiguration createWifiInfo(String SSID, String password, int type) {
        Log.w ( "createWifiInfo", "SSID = " + SSID + "password " + password + "type =" + type );
        WifiConfiguration config = new WifiConfiguration ();
        config.allowedAuthAlgorithms.clear ();
        config.allowedGroupCiphers.clear ();
        config.allowedKeyManagement.clear ();
        config.allowedPairwiseCiphers.clear ();
        config.allowedProtocols.clear ();
        config.SSID = "\"" + SSID + "\"";
        if (type == NONE_PASSWORD) {
            config.wepKeys[0] = "\"" + "\"";
            config.allowedKeyManagement.set ( WifiConfiguration.KeyMgmt.NONE );
            config.wepTxKeyIndex = 0;
        } else if (type == WEP) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set ( WifiConfiguration.AuthAlgorithm.SHARED );
            config.allowedGroupCiphers
                    .set ( WifiConfiguration.GroupCipher.CCMP );
            config.allowedGroupCiphers
                    .set ( WifiConfiguration.GroupCipher.TKIP );
            config.allowedGroupCiphers
                    .set ( WifiConfiguration.GroupCipher.WEP40 );
            config.allowedGroupCiphers
                    .set ( WifiConfiguration.GroupCipher.WEP104 );
            config.allowedKeyManagement.set ( WifiConfiguration.KeyMgmt.NONE );
            config.wepTxKeyIndex = 0;
        } else if (type == WPA) {
            config.preSharedKey = "\"" + password + "\"";
            config.hiddenSSID = true;
            config.allowedAuthAlgorithms
                    .set ( WifiConfiguration.AuthAlgorithm.OPEN );
            config.allowedGroupCiphers
                    .set ( WifiConfiguration.GroupCipher.TKIP );
            config.allowedKeyManagement
                    .set ( WifiConfiguration.KeyMgmt.WPA_PSK );
            config.allowedPairwiseCiphers
                    .set ( WifiConfiguration.PairwiseCipher.TKIP );
            // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            config.allowedGroupCiphers
                    .set ( WifiConfiguration.GroupCipher.CCMP );
            config.allowedPairwiseCiphers
                    .set ( WifiConfiguration.PairwiseCipher.CCMP );
            config.status = WifiConfiguration.Status.ENABLED;
        } else {
            return null;
        }
        return config;
    }

    public void connectWifi() {

        int MY_FLAG = -1;
        for (int i = 0; i < scanResults.size (); i++) {
            if (scanResults.get ( i ).SSID.equals ( "\"" + WIFI_Name + "\"" ) || scanResults.get ( i ).SSID.equals ( WIFI_Name )) {
                MY_FLAG = i;
            }
        }
        //扫描结果含有target
        if (MY_FLAG != -1) {
            targetWifi = scanResults.get ( MY_FLAG ).BSSID;

            //内部逻辑不懂跳过
            dhcpInfo = wifiManager.getDhcpInfo ();
            ipaddress = intIP2StringIP ( dhcpInfo.serverAddress );

            //首次判断目前是否连接上了
            if (wifiInfo != null)
                //目前已连接的wifi不等于target则断开
                if (!targetWifi.equals ( wifiInfo.getBSSID () )) {
                    wifiManager.disableNetwork ( wifiInfo.getNetworkId () );
                } else {
                    ConnectInit ();
                    return;
                }


            //如果没连接则判断该wifi加密类型
            String Encrypted = scanResults.get ( MY_FLAG ).capabilities;
            int type = NONE_PASSWORD;
            if (!TextUtils.isEmpty ( Encrypted )) {
                if (Encrypted.contains ( "WPA" ) || Encrypted.contains ( "wpa" )) {
                    type = WPA;
                    config = createWifiInfo ( scanResults.get ( MY_FLAG ).SSID, PASS_WORD, WPA );
                } else if (Encrypted.contains ( "WEP" ) || Encrypted.contains ( "wep" )) {
                    type = WEP;
                } else {
                    type = NONE_PASSWORD;
                }
            }

            //创建一个连接密码
            if (config == null) {
                //如果不是公开的wifi则需要写入密码
                if (type != NONE_PASSWORD) {
                    final EditText editText = new EditText ( context );
                    editText.setText ( PASS_WORD );
                    final int finalType = type;
                    final int MY_FLAG1 = MY_FLAG;
                    new AlertDialog.Builder ( context )
                            .setView ( editText )
                            .setTitle ( "输入Wifi密码" )
                            .setNegativeButton ( "取消", null )
                            .setPositiveButton ( "确定", new DialogInterface.OnClickListener () {
                                public void onClick(DialogInterface dialog, int which) {
                                    config = createWifiInfo ( scanResults.get ( MY_FLAG1 ).SSID, editText.getText ().toString (), finalType );

                                    int netID = wifiManager.addNetwork ( config );
                                    wifiManager.enableNetwork ( netID, true );

                                    if (config != null && targetWifi.equals ( wifiInfo.getBSSID () )){
                                        ConnectInit ();
                                    }
                                }
                            } ).show ();
                    return;
                } else {//公开则密码为空“”
                    config = createWifiInfo ( scanResults.get ( MY_FLAG ).SSID, "", type );
                }
            }

            //连接
            //connect ( config );
            int netID = wifiManager.addNetwork ( config );
            wifiManager.enableNetwork ( netID, true );

            if (wifiInfo != null && targetWifi.equals ( wifiInfo.getBSSID () )) {
                ConnectInit ();
            } else {
                Toast.makeText ( context, "未连接到目标wifi", Toast.LENGTH_SHORT );
            }
        } else {
            Toast.makeText ( context, "没扫描到目标wifi", Toast.LENGTH_SHORT );
        }
    }
}
