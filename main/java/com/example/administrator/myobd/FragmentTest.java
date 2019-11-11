package com.example.administrator.myobd;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by DWF on 2019/8/27.
 */

public class FragmentTest extends Fragment{
    private View rootview;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate( R.layout.fragment_test, container,
                false);
        return rootview;
    }
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

    }
}
