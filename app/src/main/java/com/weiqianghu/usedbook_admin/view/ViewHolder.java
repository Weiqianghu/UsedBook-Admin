package com.weiqianghu.usedbook_admin.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tb.emoji.EmojiUtil;
import com.weiqianghu.usedbook_admin.view.view.IRecycleViewItemClickListener;

/**
 * Created by 胡伟强 on 2016/1/18.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews = null;
    private static View mConvertView;

    private ViewHolder(View view) {
        super(view);
        mConvertView = view;
        this.mViews = new SparseArray<View>();
    }


    public static ViewHolder get(View view) {
        return new ViewHolder(view);
    }


    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean isChecked) {
        CheckBox view = getView(viewId);
        view.setChecked(isChecked);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public ViewHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);

        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    public void setViewVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public ViewHolder setImageForSimpleDraweeViewUri(int viewId, Uri uri) {
        SimpleDraweeView draweeView = getView(viewId);
        draweeView.setImageURI(uri);
        return this;
    }

    public void setRating(int viewId, int value) {
        RatingBar ratingBar = getView(viewId);
        if (value > 0 && value <= 5) {
            ratingBar.setRating(value);
        }
    }

    public void setEmojiText(int viewId, String text, Context context) {
        TextView textView = getView(viewId);
        EmojiUtil.displayTextView(textView, text, context);
    }

}
