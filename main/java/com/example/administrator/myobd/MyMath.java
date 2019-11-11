package com.example.administrator.myobd;

import java.text.DecimalFormat;

/**
 * Created by DWF on 2018/10/11.
 */

public class MyMath {

    /*
平均数求法
* */
    public float avrage(float newNum, float oldAvr, int numN) {
        float newAvr = (numN * oldAvr + newNum) / (numN + 1);
        return newAvr;
    }

    public int avrage(int newNum, int oldAvr, int numN) {
        int newAvr = (numN * oldAvr + newNum) / (numN + 1);
        return newAvr;
    }

    public float max(float newNum, float oldmax) {
        if (newNum > oldmax)
            return newNum;
        else
            return oldmax;
    }

    /*
    *
    * 输出时调用
    * */
    public static byte[] HexString2Bytes(String src) {
        if (null == src || 0 == src.length ()) {
            return null;
        }
        byte[] ret = new byte[src.length () / 2];
        byte[] tmp = src.getBytes ();
        for (int i = 0; i < (tmp.length / 2); i++) {
            ret[i] = uniteBytes ( tmp[i * 2], tmp[i * 2 + 1] );
        }
        return ret;
    }

    /*
    解析必要类
    * */
    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode ( "0x" + new String ( new byte[]{src0} ) ).byteValue ();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode ( "0x" + new String ( new byte[]{src1} ) ).byteValue ();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    /*输入时调用
    * 输入hex byte[]
    * 返回字符串
    * */
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer ( bArray.length );
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString ( 0xFF & bArray[i] );
            if (sTemp.length () < 2)
                sb.append ( 0 );
            sb.append ( sTemp.toUpperCase () );
        }
        return sb.toString ();
    }

    /*解析时调用
    *
    * */
    public static int HexStringToInt(String str, int index) {
        int temp = 0;
        if (index == 2) {
            temp = Integer.parseInt ( str.substring ( 0, 2 ), 16 ) * 256 + Integer.parseInt ( str.substring ( 2, 4 ), 16 );
        } else {
            temp = Integer.parseInt ( str.substring ( 0, 2 ), 16 );
        }
        return temp;
    }

    /*
    * 校验
    * */
    public boolean cs(String rec) {
        int sum = 0;
        for (int i = 0; i < rec.length () / 2 - 1; i++) {
            sum = HexStringToInt ( rec.substring ( i * 2, i * 2 + 2 ), 1 ) + sum;
        }
        if (sum % 255 >= 255 - HexStringToInt ( rec.substring ( rec.length () - 2, rec.length () ), 1 ))
            return true;
        else
            return false;
    }

    public String return_cs(String rec) {
        int sum = 0;
        for (int i = 0; i < rec.length () / 2; i++) {
            sum = Integer.valueOf ( rec.substring ( i * 2, i * 2 + 2 ), 1 ) + sum;
        }
        return Integer.toString ( 255 - sum % 255 );
    }

    /*
    * 保留小数点后两位的黑科技
    * */
    public String atleast_two(String a) {
        if (a.indexOf ( "." ) + 3 < a.length ()) {
            DecimalFormat decimalFormat  = new DecimalFormat ( "#0.00" );
            return decimalFormat.format ( Double.valueOf ( a ));
        } else
            a = a;

        return a;
    }




}
