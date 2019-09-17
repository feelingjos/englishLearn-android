package com.feeljcode.wordlearn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;


/**
 * User: Feeljcode
 * Date: 2019/8/27
 * Time: 23:49
 * Description:
 */
public class ItemSelectRadio extends LinearLayout implements Checkable {
    private boolean isChecked = false;
    private Context context;

    public ItemSelectRadio(Context context) {
        super(context, null);
        this.context = context;
    }

    public ItemSelectRadio(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ItemSelectRadio(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;
        changeColor(checked);
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        this.isChecked = !this.isChecked;
        changeColor(this.isChecked);
    }

    private void changeColor(boolean isChecked) {
        //根据check的状态切换颜色
        if (isChecked) {
            setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
        } else {
            setBackgroundColor(getResources().getColor(android.R.color.transparent));
        }
    }

}
