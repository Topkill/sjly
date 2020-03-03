package com.zpj.widget.setting;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.zpj.widget.setting.R;

public abstract class ZSettingItem<T extends ZSettingItem> extends BaseSettingItem<T> {

    protected String mTitleText;
    protected float mTitleTextSize;
    protected int mTitleTextColor;

    protected String mInfoText;
    protected float mInfoTextSize;
    protected int mInfoTextColor;

    protected String mRightText;
    protected float mRightTextSize;
    protected int mRightTextColor;

    protected Drawable mLeftIcon;
    protected Drawable mRightIcon;

    protected boolean showInfoButton;
    protected boolean showRightText;
    protected boolean showUnderLine;



    public ZSettingItem(Context context) {
        this(context, null);
    }

    public ZSettingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZSettingItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initAttribute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SimpleSettingItem);
        mTitleText = array.getString(R.styleable.SimpleSettingItem_z_setting_titleText);
        if (TextUtils.isEmpty(mTitleText)) {
            mTitleText = "Title";
        }
        tvTitle.setText(mTitleText);

        mTitleTextSize = array.getDimension(R.styleable.SimpleSettingItem_z_setting_titleTextSize, 16);

        mTitleTextColor = array.getColor(R.styleable.SimpleSettingItem_z_setting_titleTextColor, Color.parseColor("#222222"));

        Drawable background = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_background);
        mLeftIcon = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_leftIcon);
        mRightIcon = array.getDrawable(R.styleable.SimpleSettingItem_z_setting_rightIcon);
        array.recycle();


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZSettingItem);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
//            if (attr == R.styleable.ZSettingItem_z_setting_titleText) {
//                mTitleText = a.getString(attr);
//                tvTitle.setText(mTitleText);
//            } else if (attr == R.styleable.ZSettingItem_z_setting_titleTextSize) {
//                mTitleTextSize = a.getFloat(attr, 16);
//            } else if (attr == R.styleable.ZSettingItem_z_setting_titleTextColor) {
//                mTitleTextColor = a.getColor(attr, Color.BLACK);
//            }
            if (attr == R.styleable.ZSettingItem_z_setting_infoText) {
                mInfoText = a.getString(attr);
                if (!TextUtils.isEmpty(mInfoText)) {
                    tvInfo.setVisibility(VISIBLE);
                    tvInfo.setText(mInfoText);
                }
            } else if (attr == R.styleable.ZSettingItem_z_setting_infoTextSize) {
                mInfoTextSize = a.getFloat(attr, 12);
            } else if (attr == R.styleable.ZSettingItem_z_setting_infoTextColor) {
                mInfoTextColor = a.getColor(attr, Color.LTGRAY);
            }
//            else if (attr == R.styleable.ZSettingItem_z_setting_leftIcon) {
//                mLeftIcon = a.getDrawable(attr);
//            } else if (attr == R.styleable.ZSettingItem_z_setting_rightIcon) {
//                mRightIcon = a.getDrawable(attr);
//            }
            else if (attr == R.styleable.ZSettingItem_z_setting_showUnderLine) {
                showUnderLine = a.getBoolean(attr, false);
            } else if (attr == R.styleable.ZSettingItem_z_setting_showRightText) {
                showRightText = a.getBoolean(attr, false);
            } else if (attr == R.styleable.ZSettingItem_z_setting_rightText) {
                mRightText = a.getString(attr);
            } else if (attr == R.styleable.ZSettingItem_z_setting_rightTextSize) {
                mRightTextSize = a.getFloat(attr, 14);
            } else if (attr == R.styleable.ZSettingItem_z_setting_rightTextColor) {
                mRightTextColor = a.getColor(attr, Color.GRAY);
            } else if (attr == R.styleable.ZSettingItem_z_setting_showInfoBtn) {
                showInfoButton = a.getBoolean(attr, false);
            }
        }
        a.recycle();

        if (background == null) {
            TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackground});
            background = typedArray.getDrawable(0);
            typedArray.recycle();
        }

        setBackground(background);
    }

    public void setTitleText(String mTitleText) {
        this.mTitleText = mTitleText;
        tvTitle.setText(mTitleText);
    }

    public void setInfoText(String mInfoText) {
        this.mInfoText = mInfoText;
        tvInfo.setText(mInfoText);
    }

    public void setRightText(String mRightText) {
        
        if (inflatedRightText instanceof TextView) {
            this.mRightText = mRightText;
            ((TextView) inflatedRightText).setText(mRightText);
        }
    }

    public void setLeftIcon(Drawable mLeftIcon) {
        this.mLeftIcon = mLeftIcon;
        if (inflatedLeftIcon == null) {
            inflateLeftIcon(vsLeftIcon);
        } else if (inflatedLeftIcon instanceof ImageView) {
            ((ImageView) inflatedLeftIcon).setImageDrawable(mLeftIcon);
        }
    }
}

