package com.example.administrator.myobd;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by DWF on 2018/10/11.
 */

public class MyConnectThread extends Thread {

    private Socket socket;
    private MyMath myMath;
    private int FLAG=1;
    private Handler mHandler;
    private int PCorMIC=0;
    private int fromPC=1;
    private int fromMIC=0;
    public MyConnectThread(Socket socket, MyMath myMath, Handler handler, int PCorMIC){
        this.socket=socket;
        mHandler=handler;
        this.myMath=myMath;
        this.PCorMIC=PCorMIC;
    }

    public void run() {
        try {

            InputStream input = socket.getInputStream ();
            if (input == null) {
                Log.w ( "my", "输入流空" );
            }
            byte[] buff = new byte[1024];
            //输入流循环接收
            while (FLAG == 1) {
                //把byte【】转str
                String str = null;
                //读出原始值
                int bytes = input.read ( buff );
                //新建对象保存
                if (bytes > 0) {
                    byte[] text = new byte[bytes];
                    for (int i = 0; i < bytes; i++) {
                        text[i] = buff[i];
                    }
                    if(PCorMIC==0){
                        str = myMath.bytesToHexString ( text );
                        Message msg = mHandler.obtainMessage ();
                        msg.what = 999;//GET_STRING_BY_MIC;
                        msg.obj = str;
                        mHandler.sendMessage ( msg );
                        Log.e ( "MyConnectThread", "readMIC："+str );}
                    if(PCorMIC==1){
                        str =new  String( text );
                        Message msg1 = mHandler.obtainMessage ();
                        msg1.what = 999;//GET_STRING_BY_PC;
                        msg1.obj = str;
                        mHandler.sendMessage ( msg1 );
                        Log.e ( "MyConnectThread", "readPC："+str );
                    }
                    if (FLAG == 0/*ALL_FINISH*/)
                        break;
                }
            }
        } catch (IOException e) {
            Message msg = mHandler.obtainMessage ();
            msg.what = 408;
            mHandler.sendMessage ( msg );
            e.printStackTrace ();
            Log.e ( "MyConnectThread", "read "+e.toString () );
        }
    }

    public void write(byte[] bytes) {
        if(FLAG==1){
        OutputStream output;
        try {
            output = socket.getOutputStream ();
            if (output == null) {
                Log.e ( "MyConnectThread", "write" );
            } else {
                Log.e ( "MyConnectThread", "writeSuccess:"+myMath.bytesToHexString ( bytes ) );
                output.write ( bytes );
            }
        } catch (IOException e) {
            Message msg = mHandler.obtainMessage ();
            msg.what = 408;
            mHandler.sendMessage ( msg );
            Log.e ( "MyConnectThread", "write" );
            e.printStackTrace ();
            getThreadGroup ().destroy ();
        }}
    }

    //关闭流
    public void cancel() {
        FLAG=0;
        try {
            Thread.sleep ( 2000 );
        } catch (InterruptedException e) {
            e.printStackTrace ();
        }
        try {

            socket.close ();
            socket=null;
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
