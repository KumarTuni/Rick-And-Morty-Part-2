package com.example.rickandmorty.view.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.DialogCharacterDetailsBinding;
import com.example.rickandmorty.model.data.Character;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

/**
 * Created by Rohin on 7/15/2019 @7:16 AM.
 */
public class DetailsFragmentDialog extends DialogFragment {

    private Character character;
    private DialogCharacterDetailsBinding binding;

    public DetailsFragmentDialog(Character character) {
        this.character = character;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_character_details, container, false);
        binding.setCharacter(character);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
