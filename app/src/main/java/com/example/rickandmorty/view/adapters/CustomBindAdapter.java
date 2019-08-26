package com.example.rickandmorty.view.adapters;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rickandmorty.R;
import com.example.rickandmorty.view.utils.LoadView;
import com.example.rickandmorty.view.utils.PagedRecyclerView;

import androidx.databinding.BindingAdapter;
import androidx.paging.PagedListAdapter;

/**
 * Created by Rohin on 8/24/2019 @8:18 PM.
 */
public class CustomBindAdapter {

    @BindingAdapter({"adapter"})
    public static void adapter(PagedRecyclerView pagedRecyclerView, PagedListAdapter pagedListAdapter) {
        pagedRecyclerView.setAdapter(pagedListAdapter);
    }

    @BindingAdapter({"dead"})
    public static void dead(ImageView imageView,String isDead){
        if(!isDead.equalsIgnoreCase("Alive")) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

            imageView.setColorFilter(filter);
        }
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl))
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions().override(512, 512))
                    .into(view);
        else
            view.setImageResource(R.drawable.no_image);
    }

    @BindingAdapter({"lv_error_msg"})
    public static void lv_error_msg(LoadView loadView,String errorMsg){
        if (!TextUtils.isEmpty(errorMsg)) {
            loadView.setErrorMsg(errorMsg);
            loadView.error(errorMsg);
        }
    }
    @BindingAdapter({"lv_status"})
    public static void lv_status(LoadView loadView,int status){
        if(status == 1)
            loadView.loading();
        else
            loadView.error();
    }
}
