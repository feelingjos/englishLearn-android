package com.feeljcode.wordlearn.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;


/**
 * User: Feeljcode
 * Date: 2019/8/27
 * Time: 23:49
 * Description:
 */
public class ItemSelectRadio extends LinearLayout implements Checkable {

    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean mChecked;

    public ItemSelectRadio(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public void setChecked(boolean b) {

        Log.e("setChecked",b + "");

        if (b != mChecked){
            mChecked = b;
            refreshDrawableState();
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        Log.e("toggle","toggle");
        setChecked(!mChecked);
    }


    @Override
    protected int[] onCreateDrawableState(int extraSpace) {

        Log.e("onCreateDrawableState",extraSpace + "");

        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);

        if (isChecked()) mergeDrawableStates(drawableState, CHECKED_STATE_SET);

        Log.e("onCreateDrawableState", JSON.toJSONString(drawableState));

        return drawableState;
    }
}
