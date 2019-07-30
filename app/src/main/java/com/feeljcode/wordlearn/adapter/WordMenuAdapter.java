package com.feeljcode.wordlearn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.feeljcode.wordlearn.R;
import com.feeljcode.wordlearn.WordAddActivity;
import com.feeljcode.wordlearn.entity.WordMenu;

import java.util.List;

/**
 * User: Feeljcode
 * Date: 2019/7/27
 * Time: 22:00
 * Description:  菜单列表
 */
public class WordMenuAdapter extends BaseAdapter {

    private List<WordMenu> data;
    private Context context;

    public WordMenuAdapter(List<WordMenu> data,Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.word_spinner_layout,viewGroup,false);
            holder = new ViewHolder();
            holder.text_id = (TextView) view.findViewById(R.id.text_id);
            holder.operation = (TextView) view.findViewById(R.id.operation);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        holder.operation.setText(data.get(i).getName());
        holder.text_id.setText(data.get(i).getId().toString());

        holder.operation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WordAddActivity.class);
                context.startActivity(intent);
                Toast.makeText(context,"同步", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    static class ViewHolder{
        TextView text_id;//id
        TextView operation;//操作
    }
}
