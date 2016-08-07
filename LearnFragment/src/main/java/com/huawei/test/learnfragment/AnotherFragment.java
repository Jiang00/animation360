package com.huawei.test.learnfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/3/5.
 */
public class AnotherFragment extends Fragment {

    private static final String TAG =AnotherFragment.class.getName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_another,container,false);

        root.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行后退操作
                getFragmentManager().popBackStack();
            }
        });

        return root;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "AnotherFragment onDestroy");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "AnotherFragment onPause");

    }


}
