package com.feeljcode.wordlearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feeljcode.wordlearn.R;
import com.feeljcode.wordlearn.entity.WordItem;

import java.util.List;

/**
 * User: Feeljcode
 * Date: 2019/7/19
 * Time: 21:16
 */
public class WordAdapter extends BaseAdapter {

    private List<WordItem> data;
    private Context context;

    /**
     * 刷新数据
     * @param newDate
     */
    public void setRefresh(List<WordItem> newDate){
        this.data = newDate;
        notifyDataSetChanged();
    }

    public WordAdapter(Context context,List<WordItem> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.new_item_word,viewGroup,false);
            holder = new ViewHolder();
            holder.word = (TextView) view.findViewById(R.id.word);
            holder.type = (TextView) view.findViewById(R.id.type);
            holder.phoneticSymbol = (TextView) view.findViewById(R.id.phoneticSymbol);
            holder.translate = (TextView) view.findViewById(R.id.translate);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        holder.word.setText(data.get(position).getWord());
        holder.type.setText(data.get(position).getType());
        holder.phoneticSymbol.setText(data.get(position).getPhoneticSymbol());
        holder.translate.setText(data.get(position).getTranslate());

        return view;
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
    public int getCount() {
        return data.size();
    }

    static class ViewHolder{
        TextView word;//单词
        TextView phoneticSymbol;//音标
        TextView type;//类型
        TextView translate;//翻译
    }

}
