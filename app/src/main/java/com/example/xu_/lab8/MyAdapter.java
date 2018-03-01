package com.example.xu_.lab8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xu_ on 2017/12/20.
 */

public class MyAdapter extends BaseAdapter {
    public ArrayList<Item> list;
    public Context context;
    public OnItemClickListener onItemClickListener;

    public MyAdapter(Context context, ArrayList<Item> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final View res;
        ViewHolder viewHolder;
        if(view==null){
            viewHolder = new ViewHolder();
            res = LayoutInflater.from(context).inflate(R.layout.item, null);
            viewHolder.name = (TextView)res.findViewById(R.id.name_item);
            viewHolder.birth = (TextView)res.findViewById(R.id.birth_item);
            viewHolder.gift = (TextView)res.findViewById(R.id.gift_item);
            res.setTag(viewHolder);
        }
        else{
            res = view;
            viewHolder = (ViewHolder)res.getTag();
        }

        if(onItemClickListener!=null){
            res.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(res, position);
                }
            });
            res.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(res, position);
                    return true;
                }
            });
        }
        viewHolder.name.setText(list.get(position).name);
        viewHolder.birth.setText(list.get(position).birth);
        viewHolder.gift.setText(list.get(position).gift);

        return res;
    }

    public static class ViewHolder{
        TextView name;
        TextView birth;
        TextView gift;
    }

    interface OnItemClickListener{
        void onItemClick(View view, int pos);
        void onItemLongClick(View view, int pos);
    }

    @Override
    public Item getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
