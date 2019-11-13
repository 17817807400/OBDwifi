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
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.app.FragmentManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import fragment.F_DTC;
import fragment.F_IUPR;
import fragment.F_carinfo;
import fragment.F_cold;
import fragment.F_envior;
import fragment.F_fasttest;
import fragment.F_login;
import fragment.F_main;
import fragment.F_obdreport;
import fragment.F_realtime;
import fragment.F_rev;
import wifi.DWFwifi;

public class MainActivity extends AppCompatActivity implements F_main.father,GestureDetector.OnGestureListener{

    private FragmentManager fragmentManager;
    public MyMath myMath;
    private Animation animation;
    private int PAGE=1;
    public int Connected_Success=0;
    public int unConnected_RS232=1;
    public int unConnected_Sockt=2;
    private FrameLayout frameLayout;
    private LinearLayout page1;
    private LinearLayout menu;
    private ScrollView page3;
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


    F_obdreport f_obdreport;
    F_main f_main;
    F_carinfo f_carinfo;
    F_cold f_cold;
    F_DTC f_dtc;
    F_envior f_envior;
    F_fasttest f_fasttest;
    F_IUPR f_iupr;
    F_login f_login;
    F_realtime f_realtime;
    F_rev f_rev;

    private GestureDetector mGestureDetector;

    private void Init(){
        //wifi初始化
        dwFwifi=new DWFwifi ( MainActivity.this);

        animation=new Animation ();
        //次界面管理器初始化
        fragmentManager = getFragmentManager ();
        FragmentTransaction transaction = fragmentManager.beginTransaction ();
        f_main=new F_main ();
        transaction.add ( R .id.content,f_main);
        transaction.commit ();

        //标题的UI界面初始化
        //初始化:
        //对象类型TextView 自定义名home  = findViewById(R.id.###);  ###是activity自定义的id
         page1=findViewById ( R .id.page1);
        frameLayout=findViewById ( R.id.content );
        menu=findViewById ( R .id.menu);
         mGestureDetector=new GestureDetector (this, this);
        frameLayout.setOnTouchListener ( new View.OnTouchListener () {
             @Override
             public boolean onTouch(View view, MotionEvent motionEvent) {
                 mGestureDetector.onTouchEvent ( motionEvent );
                 return true;
             }
         } );
        final TextView home=findViewById ( R .id.home);
        //自定义home . setOnClickListener 为设置按钮功能
        home.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                //按钮的功能在这里实现

                //这是切换碎片界面的方法

                animation.ScaleX ( home,0.8f,1f,500 );
                animation.ScaleY ( home,0.8f,1f,500 );
                FragmentTransaction transaction = fragmentManager.beginTransaction ();
                hideFragment (  transaction);
                transaction.show ( f_main );
                transaction.commit ();

            }
        } );
        final TextView connect=findViewById ( R.id.connect );
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
                animation.ScaleX ( connect,0.8f,1f,500 );
                animation.ScaleY ( connect,0.8f,1f,500 );
                //跳转到wifi连接界面
                startActivity ( new Intent ( android.provider.Settings.ACTION_WIFI_SETTINGS ) );
            }
        } );
        final Button bt_obd=findViewById ( R.id.bt_obd );
        final Button bt_detail=findViewById ( R .id.bt_detail );
        final TextView back=findViewById ( R .id.back);
        back.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Menu ( back.getId () );
                animation.ScaleX ( back,0.8f,1f,500 );
                animation.ScaleY ( back,0.8f,1f,500 );
            }
        } );
        bt_obd.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Menu ( bt_obd.getId () );

            }
        } );
        bt_detail.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Menu ( bt_detail.getId () );
            }
        } );
    }
    //菜单特效
    public void Menu(int id){
        float distance=-300;
        switch (id){
            case R.id.back:
                if(PAGE>1){
                    animation.TranslateX ( page1,(PAGE-1)*-300f,(PAGE-2)*-300,500, 0);
                    PAGE--;
                }
                break;
            case R.id.bt_obd:
                if(PAGE<3){
                    animation.TranslateX ( page1,(PAGE-1)*-300,(PAGE)*-300f,500, 0);
                    PAGE++;
                }
                break;
            case R.id.bt_detail:
                if(PAGE<3){
                    animation.TranslateX ( page1,(PAGE-1)*-300,(PAGE)*-300f,500, 0);
                    PAGE++;
                }
                break;
        }

        //右移动画

    }

    //控制碎片显示
    public void Publicbuton(int id){
     /*
        //转换工具:固定格式
        FragmentTransaction transaction = fragmentManager.beginTransaction ();
        //隐藏所有其他界面
        hideFragment ( transaction );
        //判断是哪个id传进来的值
        switch (id){
            //如果是###
            case R.id.bt_login:
                //隐藏其他界面
                //准备显示Login界面,但先判断Login是否=空
                if(f_login==null){
                    //如果是则新建   :必要流程,否则会报错
                    f_login=new F_login ();
                    //添加
                    transaction.add ( R .id.content,f_login);
                }else{
                    //否则不必添加,直接显示
                    transaction.show ( f_login);
                }
                break;
            case R.id.bt_main:
                if(f_main==null){
                    f_main=new F_main ();
                    transaction.add ( R .id.content,f_main);
                }else{
                    transaction.show ( f_main);
                }
                break;
            case R.id.bt_carinfo:
                if(f_carinfo==null){
                    f_carinfo=new F_carinfo ();
                    transaction.add ( R .id.content,f_carinfo);
                }else{
                    transaction.show ( f_carinfo);
                }
                break;
            case R.id.bt_dtc:
                if(f_dtc==null){
                    f_dtc=new F_DTC ();
                    transaction.add ( R .id.content,f_dtc);
                }else{
                    transaction.show ( f_dtc);
                }
                break;
            case R.id.bt_enviro:
                if(f_envior==null){
                    f_envior=new F_envior ();
                    transaction.add ( R .id.content,f_envior);
                }else{
                    transaction.show ( f_envior);
                }
                break;
            case R.id.bt_fasttest:
                if(f_fasttest==null){
                    f_fasttest=new F_fasttest ();
                    transaction.add ( R .id.content,f_fasttest);
                }else{
                    transaction.show ( f_fasttest);
                }
                break;
            case R.id.bt_iupr:
                if(f_iupr==null){
                    f_iupr=new F_IUPR ();
                    transaction.add ( R .id.content,f_iupr);
                }else{
                    transaction.show ( f_iupr);
                }
                break;
            case R.id.bt_obdreport:
                if(f_obdreport==null){
                    f_obdreport=new F_obdreport ();
                    transaction.add ( R .id.content,f_obdreport);
                }else{
                    transaction.show ( f_obdreport);
                }
                break;
            case R.id.bt_realtime:
                if(f_realtime==null){
                    f_realtime=new F_realtime ();
                    transaction.add ( R .id.content,f_realtime);
                }else{
                    transaction.show ( f_realtime);
                }
                break;
            case R.id.bt_rev:
                if(f_rev==null){
                    f_rev=new F_rev ();
                    transaction.add ( R .id.content,f_rev);
                }else{
                    transaction.show ( f_rev);
                }
                break;
        }
        //工具结果提交,进行界面切换,没有提交则不切换
        transaction.commit ();
        */
    }
    //隐藏所有碎片
    public void hideFragment(FragmentTransaction transaction){
        if (f_carinfo != null) {
            transaction.hide ( f_carinfo );
        }
        if (f_cold != null) {
            transaction.hide ( f_cold );
        }
        if (f_dtc != null) {
            transaction.hide ( f_dtc );
        }
        if (f_envior != null) {
            transaction.hide ( f_envior );
        }
        if (f_fasttest != null) {
            transaction.hide ( f_fasttest );
        }
        if (f_login != null) {
            transaction.hide ( f_login );
        }
        if (f_main != null) {
            transaction.hide ( f_main );
        }
        if (f_obdreport != null) {
            transaction.hide ( f_obdreport );
        }
        if (f_realtime != null) {
            transaction.hide ( f_realtime );
        }
        if (f_rev != null) {
            transaction.hide ( f_rev );
        }

    }


    //RS232数据处理
    private static boolean Judge_Handle_SuccessOrNot() {
        if (DWFwifi.SEND_WHAT.equals ( "1" )) {
            //getChart
            if (RECEIVE.length () >= 2004 && RECEIVE.substring ( 0, 2 ).equals ( "1" )) {
                //在FRAGMENT设置一个WORK函数  用全局静态变量RECEIVE来提取
            }
        }
        return false;
    }

    //线程延时函数
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

    //线程数据接收
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
        if (keyCode == KeyEvent.KEYCODE_BACK&&f_login!=null&&f_login.keyboardUtil!=null) {
            if (f_login.keyboardUtil.isShow()) {
                f_login.keyboardUtil.hideKeyboard();
            } else {

            }
        }
        return false;
    }


    //手势识别
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }
    //识别滑动
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(motionEvent.getRawX ()-motionEvent1.getRawX ()<-120&&menu.getX ()<0){

            animation.TranslateXBounce ( menu,-300,0,500,0 );
        }
        else  if(motionEvent.getRawX ()-motionEvent1.getRawX ()>120&&menu.getX ()>=0){
            animation.TranslateXBounce ( menu,0,-300,500,0 );
        }
        return true;
    }
}
