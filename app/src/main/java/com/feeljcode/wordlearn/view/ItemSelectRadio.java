package com.feeljcode.wordlearn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * User: Feeljcode
 * Date: 2019/8/27
 * Time: 23:49
 * Description:
 */
public class ItemSelectRadio extends LinearLayout implements Checkable {
    private boolean isChecked = false;
    private Context context;

    private TextView textView;

    public ItemSelectRadio(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        int lens = getChildCount();

        Log.e("长度",lens + "");

        for (int i = 0, len = getChildCount(); i < len; i++) {
            View child = getChildAt(i);
            Log.e("控件id" ,"" + child.getId());
        }

    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        changeColor(checked);

        //Log.e("setChecked",textView.getText().toString()  +  checked);

    }

    @Override
    public boolean isChecked() {

        //Log.e("isChecked",textView.getText().toString()  +  isChecked);

        return isChecked;
    }

    @Override
    public void toggle() {
        this.isChecked = !this.isChecked;
        changeColor(this.isChecked);

        //Log.e("toggle",textView.getText().toString()  +  isChecked);

    }

    private void changeColor(boolean isChecked) {

        //Log.e("选中情况",isChecked + "");

        //根据check的状态切换颜色
        if (isChecked) {

            /*ColorDrawable viewColor = (ColorDrawable) getBackground();
             int colorId = viewColor.getColor();


            Log.e("颜色",colorId + "");*/
            setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));

            /*ColorDrawable viewColor2 = (ColorDrawable) getBackground();
            int colorId2 = viewColor2.getColor();


            Log.e("颜色2",colorId2 + "");*/

        } else {
            setBackgroundColor(getResources().getColor(android.R.color.transparent));
            //setBackgroundColor(getResources().getColor(R.color.tab_checked));
        }
    }

}
