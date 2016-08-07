package com.example.administrator.learnrv;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/3/16.
 */
class MyAdapter extends RecyclerView.Adapter {


    class ViewHolder extends RecyclerView.ViewHolder {
        //这样来让TextView和ViewHolder进行一个关联

        private View root;
        private TextView tvTitle, tvContent;

        public ViewHolder(View root) {
            super(root);

            tvTitle = (TextView) root.findViewById(R.id.tvTitle);
            tvContent = (TextView) root.findViewById(R.id.tvContent);

        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvContent() {

            return tvContent;

        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cell, null));


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
        CellData cellData  = data[position];
        vh.getTvTitle().setText(cellData.getTitle());
        vh.getTvContent().setText(cellData.getContent());


    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    private CellData[] data  = new CellData[]{new CellData("极客学院","IT职业教育"),new CellData("我是一个大傻逼","哈哈哈哈"),new CellData("极客学院","IT职业教育"),new CellData("极客学院","IT职业教育"),new CellData("极客学院","IT职业教育"),new CellData("极客学院","IT职业教育"),new CellData("极客学院","IT职业教育"),new CellData("极客学院","IT职业教育"),new CellData("极客学院","IT职业教育")};


    //private String[] data = new String[]{"Hello", "jikexueyuan", "ime"};
}
