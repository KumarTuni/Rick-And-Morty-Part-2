package com.example.rickandmorty.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.ItemEpisodeLayoutBinding;
import com.example.rickandmorty.model.data.Episode;
import com.example.rickandmorty.view.callbacks.RecyclerViewItemClickListner;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rohin on 5/22/2019 @5:26 AM.
 */
public class EpisodesAdapter extends PagedListAdapter<Episode, EpisodesAdapter.ItemViewHolder> {

    private static DiffUtil.ItemCallback<Episode> diffCallback = new DiffUtil.ItemCallback<Episode>() {
        @Override
        public boolean areItemsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Episode oldItem, @NonNull Episode newItem) {
            return oldItem.equals(newItem);
        }
    };
    private int lastPosition = -1;
    private RecyclerViewItemClickListner<Episode> itemClickListner;

    public EpisodesAdapter(RecyclerViewItemClickListner<Episode> itemClickListner) {
        super(diffCallback);
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEpisodeLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_episode_layout, parent, false);
        binding.setItemClickListner(itemClickListner);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(getItem(position));
        setAnimation(holder.itemView, position);

    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.slide_enter_anim);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemEpisodeLayoutBinding binding;

        public ItemViewHolder(ItemEpisodeLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Episode episode) {
            binding.setEpisode(episode);
            binding.executePendingBindings();
        }
    }

}
