package com.huawei.test.learnfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by Administrator on 2016/3/9.
 */
public class JikexueyuanWebViewFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.jikexueyuan_webview,container,false);
        WebView webView  = (WebView) view.findViewById(R.id.wv);
        webView.loadUrl("http://www.jikexueyuan.com");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
