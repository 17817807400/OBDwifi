package com.example.administrator.myobd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import java.util.logging.Handler;


public class DWFwifi extends MainActivity {

    public int SingleLock_ConnectInit_MIC=0;
    private List <ScanResult> scanResults;
    private WifiManager wifiManager;
    private WifiConnect wifiConnect;
    private Context context;
    public String RECEIVE_WHAT;
    public static String SEND_WHAT="send";
    public MyConnectThread myConnectThread;
    private static String WIFI_Name;
    private static String WIFI_PASSWORD = "";
    private static String WIFI_IP="";
    private static int SEND_STATE=0;
    private static int WIFI_PORT=0;

    static public int SEND_INFINITE = -1;
    static public int SEND_PAUSE = 0;
    static public int SEND_ONE = 1;
    /**
     * 邓文枫
     * 生成此类后需执行:     DWFwifi.scanWifi()
     *
     * @param context       传入 xxxx.this
     */
    public DWFwifi(Context context) {

        wifiManager = (WifiManager) context.getApplicationContext ().getSystemService ( Context.WIFI_SERVICE );
        this.context = context;
 //     InitBroadcast ( context );
    }

    public static void SendInfinite(){
        SEND_STATE=-1;
    }
    public static void SendPause(){
        SEND_STATE=0;
    }
    public static void SendOneTime(){
        SEND_STATE=1;
    }

    public static void SendWhat(String whatUsend){
        SEND_WHAT=whatUsend;
    }

    public static void setInfo( /*String a, String b,*/String c,int d){
/*        WIFI_Name = a;
        WIFI_PASSWORD = b;*/
        WIFI_IP=c;
        WIFI_PORT=d;
    }
    /**
     * 开始动作
     */
    public void scanWifi() {
/*        if (!wifiManager.isWifiEnabled ()) {
            //如果wifi没打开，则打开
            wifiManager.setWifiEnabled ( true );
            if (wifiManager.isWifiEnabled ()) {
                wifiManager.startScan ();
            } else if (!wifiManager.isWifiEnabled ()) {
                // scanner ();
                Toast.makeText ( context, "未开启wifi", Toast.LENGTH_SHORT ).show ();
            }
        } else {
            Toast.makeText ( context, "Start scanner", Toast.LENGTH_SHORT ).show ();
            wifiManager.startScan ();
        }
 */
        ConnectInit ();
    }
/*
    private void InitBroadcast(Context context) {
        IntentFilter filter = new IntentFilter ();
        filter.addAction ( BluetoothAdapter.ACTION_DISCOVERY_STARTED );
        // 结束查找
        filter.addAction ( BluetoothAdapter.ACTION_DISCOVERY_FINISHED );
        filter.addAction ( "android.bluetooth.device.action.PAIRING_REQUEST" );
        // 查找设备
        filter.addAction ( BluetoothDevice.ACTION_FOUND );
        filter.addAction ( BluetoothDevice.ACTION_BOND_STATE_CHANGED );

        filter.addAction ( WifiManager.SCAN_RESULTS_AVAILABLE_ACTION );
        //wifi的开启，关闭
        filter.addAction ( WifiManager.WIFI_STATE_CHANGED_ACTION );
        //连接的状态改变，是否连接到了有效wifi，获得消息
        filter.addAction ( WifiManager.NETWORK_STATE_CHANGED_ACTION );
        //监听网络数据，wifi和4g。是否打开
        filter.addAction ( ConnectivityManager.CONNECTIVITY_ACTION );

        context.registerReceiver ( mReceiver, filter );
    }*/
/*

    private BroadcastReceiver mReceiver = new BroadcastReceiver () {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction ();
            //如果扫描成功
            if (action.equals ( WifiManager.SCAN_RESULTS_AVAILABLE_ACTION )) {

                scanResults = wifiManager.getScanResults ();
                Log.w ( "MYreceiver", "getResults" );
                //wifiInfo只能获得已经连接设备的wifi信息，ScanResuls能获得wifi源消息
                wifiConnect = new WifiConnect ( wifiManager, scanResults, WIFI_Name, WIFI_PASSWORD, context );
                wifiConnect.connectWifi ();
            }
            //如果网络状态改变
            else if (action.equals ( WifiManager.NETWORK_STATE_CHANGED_ACTION )) {
                //info.获得许多的该wifi内容
                NetworkInfo info = intent.getParcelableExtra ( WifiManager.EXTRA_NETWORK_INFO );
                if (info.getState ().equals ( NetworkInfo.State.DISCONNECTED )) {

                    Log.w ( "MYreceiver", "斷開wif" );
                }
                //如果已连接
                if (info.getState ().equals ( NetworkInfo.State.CONNECTED )) {
                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo wifiInfo = cm.getActiveNetworkInfo();

                    if (WIFI_Name != null&&wifiInfo!=null){
                        //如果等于目标设备
                        if ((wifiInfo.getExtraInfo ().equals ( WIFI_Name )) || wifiInfo.getExtraInfo ().equals ( "\"" + WIFI_Name + "\"" )) {
                           // ConnectInit ();
                        } else {
                            //wifiManager.disconnect ();
                            Log.w ( "MYreceiver", "isConnected but not target" + wifiInfo.getExtraInfo () + "  \"" + WIFI_Name + "\"" );
                        }
                    }else {
                        //初始化
                        Log.w ( "MYreceiver", "isConnected but not target"  );
                    }
                } else {
                    //如果未连接
                    NetworkInfo.DetailedState state = info.getDetailedState ();
                    if (state == state.CONNECTING) {
                        Log.w ( "MYreciver", "connecting" );
                    } else if (state == state.AUTHENTICATING) {
                        Log.w ( "MYreciver", "getid" );
                    } else if (state == state.OBTAINING_IPADDR) {
                        Log.w ( "MYreciver", "getip" );
                    } else {
                        Log.w ( "MYreciver", "failed to connect" );
                    }
                    if (state == state.CONNECTED) {
                        Log.w ( "MYreciver", "connected" );
                    }
                }

            }

        }
    };
*/


    //通信初始化 被DWFwifi调用
    public void ConnectInit() {
        //等于0表示可进入
        if (SingleLock_ConnectInit_MIC == 1) {
            Toast.makeText ( context, "不可接入", Toast.LENGTH_LONG ).show ();
            return;
        }
        //不可进入
        SingleLock_ConnectInit_MIC = 1;
        Toast.makeText ( context, "尝试接入", Toast.LENGTH_LONG ).show ();
        new Thread () {
            public void run() {
                Socket socket;
                try {
                    Log.w ( "DWFwifi", WIFI_IP+"   "+WIFI_PORT );
                    socket = new Socket (  );
                    SocketAddress address=new InetSocketAddress ( WIFI_IP,WIFI_PORT  );
                    socket.connect (address, 30000 );
                    socket.setSoTimeout(30000);
                    if (socket != null) {
                        Log.w ( "DWFwifi", "new thread" );
                        myConnectThread = new MyConnectThread ( socket, myMath, handler, 0 );
                        if (socket.isConnected ()) {
                            Log.w ( "DWFwifi", "thread connect" );

                            myConnectThread.start ();
                            delay ( 0, Connected_Success, null );
                            myConnectThread.write ( myMath.HexString2Bytes ( SEND_WHAT ) );
                            socket.close ();//@@@@@@@@@@@@@@@@@@@@@@超级重要，释放端口，socket=serverSocket情况下，socket.close是无法关闭serversocket的
                            Communication_MIC ();
                            SingleLock_ConnectInit_MIC = 1;
                        } else {
                            SingleLock_ConnectInit_MIC = 0;
                            socket.close ();
                            //如果没连接就退出
                            delay ( 0, unConnected_RS232, null );
                        }
                    }
                } catch (Exception e) {

                    SingleLock_ConnectInit_MIC = 0;
                    Log.w ( "DWFwifi", e.toString () );
                    delay ( 0, unConnected_Sockt, null );
                    e.printStackTrace ();
                }
            }


            //发送内容的逻辑

            /**
             * @SEND_STATE 全局變量, 你需要發送的內容
             * SEND_INFINITE 代表无线发送
             * SEND_PAUSE 代表暂停发送
             *
             */
            public void Communication_MIC() {
                while (true) {
                    if (SEND_STATE == SEND_INFINITE) {
                        //通信核心逻辑
                        myConnectThread.write ( myMath.HexString2Bytes ( SEND_WHAT ) );
                        //延时时间
                        try {
                            Thread.sleep ( 250 );
                        } catch (InterruptedException e) {
                            e.printStackTrace ();
                        }
                    } else if (SEND_STATE==SEND_ONE) {

                        try {
                            Thread.sleep ( 300 );
                        } catch (InterruptedException e) {
                            e.printStackTrace ();
                        }
                        Log.e ( "flb100", "communicateMIC send one time" + SEND_WHAT );
                        myConnectThread.write ( myMath.HexString2Bytes ( SEND_WHAT ) );
                        SEND_STATE =SEND_PAUSE;//发送完进入暂停
                    }else if (SEND_STATE == SEND_PAUSE) {
                        while (SEND_STATE == SEND_PAUSE) {//等待跳出
                            //线程延时
                            try {
                                Thread.sleep ( 300 );
                            } catch (InterruptedException e) {
                                e.printStackTrace ();
                            }
                        }
                    }
                }
            }
        }.start ();
    }

}