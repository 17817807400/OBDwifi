package com.example.administrator.myobd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

/**
 * Created by DWF on 2018/10/11.
 */

public class PublicDialog {
    /*
   * 特别内容定制了对话款UI
   *
   * */
    private Context context;
    public PublicDialog(Context context){
        this.context=context;
    }
    public void myDialogQCLX(final TextView lx) {
        new AlertDialog.Builder ( context).setCancelable ( false )
                .setSingleChoiceItems ( new String[]{"特殊", "小型汽车", "大型汽车", "新能源汽车", "新能源大型汽车"}, 0, new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                lx.setText ( "未知" );
                                break;
                            case 1:
                                lx.setText ( "小型汽车" );
                                break;
                            case 2:
                                lx.setText ( "大型汽车" );
                                break;
                            case 3:
                                lx.setText ( "新能源汽车" );
                                break;
                            case 4:
                                lx.setText ( "新能源大型汽车" );
                                break;
                        }
                    }
                } )
                .setTitle ( "请确认你的汽车类型" )
                .setNegativeButton ( "取消", null )
                .setPositiveButton ( "确定", null ).show ();
    }
}
