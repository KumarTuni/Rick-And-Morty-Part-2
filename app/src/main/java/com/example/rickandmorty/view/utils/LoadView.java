package com.example.rickandmorty.view.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rickandmorty.R;
import com.example.rickandmorty.view.callbacks.RetryListner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleableRes;

/**
 * Created by Rohin on 5/29/2019 @12:49 PM.
 */
public class LoadView extends FrameLayout {

    private Context ctx;
    private ProgressBar progressBar;
    private LinearLayout loadmore_errorlayout;
    private ImageButton loadmore_retry;
    private TextView loadmore_errortxt;
    private RetryListner retryListner;
    private String errorMsg;

    public final static int LOADING = 1;
    public final static int ERROR = 2;

    public LoadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
        init(attrs);
    }

    public LoadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        LayoutInflater.from(ctx).inflate(R.layout.load_view_layout, this);
        progressBar = findViewById(R.id.loadmore_progress);
        loadmore_errorlayout = findViewById(R.id.loadmore_errorlayout);
        loadmore_errortxt = findViewById(R.id.loadmore_errortxt);
        loadmore_retry = findViewById(R.id.loadmore_retry);

        loadmore_retry.setOnClickListener(v -> {
            if (retryListner != null)
                retryListner.onRetryClick();
        });
        loadmore_errorlayout.setOnClickListener(v -> {
            if (retryListner != null)
                retryListner.onRetryClick();
        });

    }

    public void loading() {
        progressBar.setVisibility(VISIBLE);
        loadmore_errorlayout.setVisibility(GONE);
    }

    public void error(String errorMsg) {
        progressBar.setVisibility(GONE);
        loadmore_errorlayout.setVisibility(VISIBLE);
        if (!TextUtils.isEmpty(errorMsg))
            loadmore_errortxt.setText(errorMsg);
    }

    public void error() {
        error(errorMsg);
    }

    public void setRetryListner(RetryListner retryListner) {
        this.retryListner = retryListner;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
