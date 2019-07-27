package com.feeljcode.wordlearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feeljcode.wordlearn.R;
import com.feeljcode.wordlearn.entity.WordMenu;

import java.util.List;

/**
 * User: Feeljcode
 * Date: 2019/7/27
 * Time: 12:20
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
            view = LayoutInflater.from(context).inflate(R.layout.word_spinner_bottom,viewGroup,false);
            holder = new ViewHolder();
            /*holder.word = (TextView) view.findViewById(R.id.word);*/
            holder.synG = (TextView) view.findViewById(R.id.synG);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        holder.synG.setText(data.get(i).getName());

        return view;
    }

    static class ViewHolder{
        TextView word;//id
        TextView synG;//操作
    }
}