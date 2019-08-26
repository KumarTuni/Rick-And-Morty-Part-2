package com.example.rickandmorty.view.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rickandmorty.R;
import com.example.rickandmorty.view.callbacks.RetryListner;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rohin on 5/29/2019 @9:01 AM.
 */
public class PagedRecyclerView extends RelativeLayout {

    private Context ctx;
    private PagedListAdapter adapter;
    public RecyclerView recyclerView;
    private LoadView loadingMoreView;
    private CardView cardView;
    private TextView noResultTxtV;
    private LoadView loadingView;
    private RetryListner retryListner;

    public PagedRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.ctx = context;
        init(attrs);
    }

    public PagedRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.ctx = context;
        init(attrs);

    }

    private void init(AttributeSet attrs) {

        LayoutInflater.from(ctx).inflate(R.layout.paged_recyclerview_with_events, this);
        recyclerView = findViewById(R.id.dataRV);
        cardView = findViewById(R.id.cardView);
        noResultTxtV = findViewById(R.id.noResult);
        loadingMoreView = findViewById(R.id.loadingMoreView);
        loadingView = findViewById(R.id.loadingView);
        loadingView.setRetryListner(retryListner);
        loadingMoreView.setRetryListner(retryListner);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.addItemDecoration(new DividerItemDecoration(ctx, DividerItemDecoration.VERTICAL));

        TypedArray typedArray =ctx.obtainStyledAttributes(attrs,R.styleable.PagedRecyclerView);
        int color = typedArray.getResourceId(R.styleable.PagedRecyclerView_prv_background_color,R.color.cardBackground);
        cardView.setCardBackgroundColor(ContextCompat.getColor(ctx,color));
        typedArray.recycle();

    }

    public void setAdapter(PagedListAdapter adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
    }

    public void error(String errorMsg) {
        noResultTxtV.setVisibility(GONE);
        cardView.setVisibility(VISIBLE);
        loadingView.setVisibility(VISIBLE);
        loadingView.error(errorMsg);

    }

    public void empty() {
        noResultTxtV.setVisibility(VISIBLE);
        cardView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        loadingMoreView.setVisibility(GONE);
    }

    public void success() {
        noResultTxtV.setVisibility(GONE);
        cardView.setVisibility(VISIBLE);
        loadingMoreView.setVisibility(GONE);
        loadingView.setVisibility(GONE);
        recyclerView.requestLayout();

    }

    public void loading() {
        noResultTxtV.setVisibility(GONE);
        cardView.setVisibility(VISIBLE);
        loadingMoreView.setVisibility(GONE);
        loadingView.setVisibility(VISIBLE);
        loadingView.loading();
    }

    public void loadingMore() {
        noResultTxtV.setVisibility(GONE);
        cardView.setVisibility(VISIBLE);
        loadingMoreView.setVisibility(VISIBLE);
        loadingMoreView.loading();
    }

    public void loadingMoreError(String errorMessage) {
        noResultTxtV.setVisibility(GONE);
        cardView.setVisibility(VISIBLE);
        loadingMoreView.setVisibility(VISIBLE);
        loadingMoreView.error(errorMessage);
    }

    public void loadNextPage() {
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void setRetryListner(RetryListner retryListner) {
        this.retryListner = retryListner;
        loadingView.setRetryListner(retryListner);
        loadingMoreView.setRetryListner(retryListner);

    }

    public void setOnScrollListner(RecyclerView.OnScrollListener onScrollListner) {
        recyclerView.addOnScrollListener(onScrollListner);
    }

    public void setNestedScroll(boolean nestedScroll) {
        recyclerView.setNestedScrollingEnabled(nestedScroll);
    }
}


