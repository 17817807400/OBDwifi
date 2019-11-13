package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myobd.R;

/**
 * Created by DWF on 2019/8/27.
 */

public class F_main extends Fragment {
    private View rootview;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate( R.layout.f_main, container,
                false);
        return rootview;
    }

    public interface father {
        void Publicbuton(int id);

    }

    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {


    }
}