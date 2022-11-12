package com.mnt.tx.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mnt.tx.R;
import com.mnt.tx.data.Table;
import com.mnt.tx.widget.IconView;

import java.util.List;

@SuppressLint("NewApi")
public class TableAdapter extends BaseAdapter {

    List<Table> tables;
    Activity activity;

    public TableAdapter(List<Table> tables, Activity activity) {
        this.tables = tables;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return tables.size();
    }

    @Override
    public Object getItem(int i) {
        return tables.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_table, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.loadData(tables.get(i));
        viewHolder.actionView(tables.get(i));
        return convertView;
    }

    private class ViewHolder{
        private IconView ivIcon;
        private TextView tvName;
        private View v;

        public ViewHolder(View v){
            this.v = v;
            connectView();
        }

        private void connectView(){
            this.tvName = this.v.findViewById(R.id.tv_name);
            this.ivIcon = this.v.findViewById(R.id.iv_icon);
        }

        public void loadData(Table data){
            this.tvName.setText(data.getName());
            if(data.isActive()){
                this.tvName.setTextColor(activity.getResources().getColor(R.color.green));
                this.ivIcon.setTextColor(activity.getResources().getColor(R.color.green));
            }else{
                this.tvName.setTextColor(activity.getResources().getColor(R.color.gray));
                this.ivIcon.setTextColor(activity.getResources().getColor(R.color.gray));
            }
        }

        public void actionView(Table data){
            this.v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(data.isActive()) return;
                    tables.forEach((item) -> {
                        item.setActive(false);
                    });
                    data.setActive(true);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
