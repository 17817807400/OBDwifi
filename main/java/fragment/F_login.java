package fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.myobd.KeyboardUtil;
import com.example.administrator.myobd.R;

/**
 * Created by DWF on 2019/8/27.
 */

public class F_login extends Fragment {
    private View rootview;
    private EditText carnum;
    private EditText tester;
    private Button carlogo;
    private EditText carcolor;
    private EditText cartype;
    public KeyboardUtil keyboardUtil;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate( R.layout.f_login, container,
                false);
        return rootview;
    }
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        carnum=rootview.findViewById ( R.id.carnum );
        tester=rootview.findViewById ( R.id.tester );
        cartype=rootview.findViewById ( R.id.cartype );

        carnum.setOnTouchListener ( new View.OnTouchListener () {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (keyboardUtil == null) {
                    keyboardUtil = new KeyboardUtil(getActivity (), carnum);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                } else {
                    keyboardUtil.showKeyboard();
                }
                return false;
            }
        } );
        cartype.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder ( getActivity ()).setCancelable ( false )
                        .setSingleChoiceItems ( new String[]{"特殊", "小型汽车", "大型汽车", "新能源汽车", "新能源大型汽车"}, 0, new DialogInterface.OnClickListener () {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i) {
                                    case 0:
                                        cartype.setText ( "未知" );
                                        break;
                                    case 1:
                                        cartype.setText ( "小型汽车" );
                                        break;
                                    case 2:
                                        cartype.setText ( "大型汽车" );
                                        break;
                                    case 3:
                                        cartype.setText ( "新能源汽车" );
                                        break;
                                    case 4:
                                        cartype.setText ( "新能源大型汽车" );
                                        break;
                                }
                            }
                        } )
                        .setTitle ( "请确认你的汽车类型" )
                        .setNegativeButton ( "取消", null )
                        .setPositiveButton ( "确定", null ).show ();
            }
        } );



    }

}