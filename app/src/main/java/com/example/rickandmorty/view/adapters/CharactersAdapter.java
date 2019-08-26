package com.example.rickandmorty.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.ItemCharachterLayoutBinding;
import com.example.rickandmorty.model.data.Character;
import com.example.rickandmorty.view.callbacks.RecyclerViewItemClickListner;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Rohin on 7/5/2019 @3:51 AM.
 */
public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.ItemViewHolder> {

    private List<Character> data = new ArrayList<>();
    private RecyclerViewItemClickListner<Character> itemClickListner;

    public CharactersAdapter(RecyclerViewItemClickListner<Character> itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    public void setData(final List<Character> contactItemList) {
        if (this.data.size() ==  0) {
            this.data.addAll(contactItemList);
            notifyItemRangeInserted(0, contactItemList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return contactItemList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return data.get(oldItemPosition).equals(
                            contactItemList.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Character newItem = contactItemList.get(newItemPosition);
                    Character oldItem = data.get(oldItemPosition);
                    return newItem.equals(oldItem);
                }
            });
            this.data = contactItemList;
            result.dispatchUpdatesTo(this);
        }
    }


    @NonNull
    @Override
    public CharactersAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCharachterLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_charachter_layout, parent, false);
        binding.setItemClickListner(itemClickListner);
        return new CharactersAdapter.ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersAdapter.ItemViewHolder holder, int position) {
        holder.bind(data.get(position));

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemCharachterLayoutBinding binding;

        public ItemViewHolder(ItemCharachterLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Character character) {
            binding.setCharacter(character);
            binding.executePendingBindings();
        }
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

}
