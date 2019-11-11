package com.example.administrator.myobd;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.app.FragmentManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements FragmentMain.father{

    private FragmentManager fragmentManager;
    public MyMath myMath;


    public int Connected_Success=0;
    public int unConnected_RS232=1;
    public int unConnected_Sockt=2;
//    private String SEND_WHAT;
    DWFwifi dwFwifi;
    public View dialog;
    public String ipaddr;
    public int ipprot;
    //当处于SEND_STATE=SEND_PAUSE时,SEND_ONETIME=true就能触发发送一次的指令.
    //例如 setSEND_WHAT(XXX) 然后 SEND_ONETIME=true
    static public String RECEIVE ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Init ();
    }

    FragmentLogin fragmentLogin;
    FragmentTest fragmentTest;
    FragmentMain fragmentMain;

    private void Init(){
        //wifi初始化
        dwFwifi=new DWFwifi ( MainActivity.this);

        //次界面管理器初始化
        fragmentManager = getFragmentManager ();
        FragmentTransaction transaction = fragmentManager.beginTransaction ();
        fragmentMain=new FragmentMain ();
        transaction.add ( R .id.content,fragmentMain);
        transaction.commit ();

        //标题的UI界面初始化
        //初始化:
        //对象类型TextView 自定义名home  = findViewById(R.id.###);  ###是activity自定义的id
        TextView home=findViewById ( R .id.home);
        //自定义home . setOnClickListener 为设置按钮功能
        home.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                //按钮的功能在这里实现

                //这是切换碎片界面的方法
                FragmentTransaction transaction = fragmentManager.beginTransaction ();
                if (fragmentTest != null) {
                    transaction.hide ( fragmentTest );
                }
                if (fragmentLogin != null) {
                    transaction.hide ( fragmentLogin );
                }
                transaction.show ( fragmentMain );
                transaction.commit ();

            }
        } );
        TextView connect=findViewById ( R.id.connect );
        connect.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                dialog = getLayoutInflater ().inflate(R.layout.connectmsg,null);
                new AlertDialog.Builder ( MainActivity.this ).setView ( dialog).setNegativeButton ( "放弃", null).setPositiveButton ( "连接", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //初始化文本编辑框
                        EditText wifiipaddress=dialog.findViewById ( R .id.wifipadress);
                        EditText wifiport=dialog.findViewById ( R .id.wifiport);
                        //获取 文本编辑框的内容 .toString()表示转化为文本类型
                        ipaddr=wifiipaddress.getText ().toString ();
                        //     Interger.valueOf  表示转换为int类型
                        ipprot=Integer.valueOf ( wifiport.getText ().toString () );
                        //   设置端口和地址
                        DWFwifi.setInfo (ipaddr,ipprot);
                        //   连接wifi
                        dwFwifi.scanWifi ();
                    }
                }).show ();

                //跳转到wifi连接界面
                startActivity ( new Intent ( android.provider.Settings.ACTION_WIFI_SETTINGS ) );
            }
        } );
        //登记按钮初始化
        final Button login = findViewById ( R .id.main_login);
         login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                //login.getID为登记按钮的ID
                //把ID传给Publicbutton子程序
                Publicbuton ( login.getId () );

            }
        } );
         //检测按钮初始化
        final Button test=findViewById ( R .id.main_test);
        test.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

            }
        } );
        //上传按钮初始化
        final Button upload=findViewById ( R .id.main_upload);
        upload.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

            }
        } );
        //更新按钮初始化
        final Button update=findViewById ( R .id.main_update);
        update.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {

            }
        } );



    }


    //控制碎片显示
    public void Publicbuton(int id){
        //转换工具:固定格式
        FragmentTransaction transaction = fragmentManager.beginTransaction ();
        //隐藏所有其他界面
        hideFragment ( transaction );
        //判断是哪个id传进来的值
        switch (id){
            //如果是###
            case R.id.main_login:
                //隐藏其他界面

                //准备显示Login界面,但先判断Login是否=空
                if(fragmentLogin==null){
                    //如果是则新建   :必要流程,否则会报错
                    fragmentLogin=new FragmentLogin ();
                    //添加
                    transaction.add ( R .id.content,fragmentLogin);
                }else{
                    //否则不必添加,直接显示
                    transaction.show ( fragmentLogin);
                }
                break;
            case R.id.main_test:
                if(fragmentLogin==null){
                    fragmentLogin=new FragmentLogin ();
                    transaction.add ( R .id.content,fragmentTest);
                }else{
                    transaction.show ( fragmentLogin);
                }
                break;
        }
        //工具结果提交,进行界面切换,没有提交则不切换
        transaction.commit ();
    }

    public void hideFragment(FragmentTransaction transaction){
        transaction.hide (  );

    }

    private static boolean Judge_Handle_SuccessOrNot() {
        if (DWFwifi.SEND_WHAT.equals ( "1" )) {
            //getChart
            if (RECEIVE.length () >= 2004 && RECEIVE.substring ( 0, 2 ).equals ( "1" )) {
                //在FRAGMENT设置一个WORK函数  用全局静态变量RECEIVE来提取
            }
        }
        return false;
    }

    public void delay(final int time, final int flag, final Object obj) {
        if (time != 0) {
            new Thread () {
                public void run() {
                    try {
                        Thread.sleep ( time );
                    } catch (InterruptedException e) {
                        e.printStackTrace ();
                    }
                    Message msg = handler.obtainMessage ();
                    msg.what = flag;
                    if (obj != null)
                        msg.obj = obj;
                    handler.sendMessage ( msg );
                }
            }.start ();
        } else {
            Message msg = handler.obtainMessage ();
            msg.what = flag;
            if (obj != null)
                msg.obj = obj;
            handler.sendMessage ( msg );
        }
    }

    public static Handler handler = new Handler () {
        public void handleMessage(Message msg) {
            super.handleMessage ( msg );
            if(msg.what==2){
                Log.w ( "handle", "通信失败" );
            }else if(msg.what==999){

                RECEIVE=(String)msg.obj;
                Log.w ( "handle", "handleMessage: RECEIVE"+RECEIVE );
                Judge_Handle_SuccessOrNot();

            }
        }
    };

    //返回键隐藏键盘
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&&fragmentLogin!=null&&fragmentLogin.keyboardUtil!=null) {
            if (fragmentLogin.keyboardUtil.isShow()) {
                fragmentLogin.keyboardUtil.hideKeyboard();
            } else {

            }
        }
        return false;
    }
}
