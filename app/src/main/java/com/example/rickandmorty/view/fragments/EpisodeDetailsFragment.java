package com.example.rickandmorty.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.FragmentEpisodeDetailsBinding;
import com.example.rickandmorty.view.adapters.CharactersAdapter;
import com.example.rickandmorty.view.adapters.CharactersPagedAdapter;
import com.example.rickandmorty.viewModel.CharactersViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by Rohin on 8/24/2019 @8:16 PM.
 */
public class EpisodeDetailsFragment extends Fragment {

    private CharactersViewModel viewModel;
    private FragmentEpisodeDetailsBinding binding;
    private CharactersAdapter adapter;
    private NavController navController;

    private String ids;
    private int episodeId;

    public EpisodeDetailsFragment() {
        adapter = new CharactersAdapter(itemValue -> {
            DetailsFragmentDialog detailsFragmentDialog = new DetailsFragmentDialog(itemValue);
            detailsFragmentDialog.show(getFragmentManager(), "details");
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ids = EpisodeDetailsFragmentArgs.fromBundle(getArguments()).getIds();
        episodeId = EpisodeDetailsFragmentArgs.fromBundle(getArguments()).getEpisodeId();

        viewModel = new ViewModelProvider.NewInstanceFactory().create(CharactersViewModel.class);
        //    viewModel.loadInitialPage();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_episode_details, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        binding.loadingView.setRetryListner(() -> viewModel.loadCharacters(ids,episodeId));

        binding.pagedRV.setAdapter(adapter);
        binding.pagedRV.setHasFixedSize(false);
        binding.pagedRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        viewModel.getNetworkResponse().observe(this, listAPIResponseAPIResponse -> {
            binding.setError(listAPIResponseAPIResponse.isHasError());
            binding.setIsLoading(listAPIResponseAPIResponse.isRunning());
            binding.setEmpty(listAPIResponseAPIResponse.isEmpty());
            binding.setSuccess(listAPIResponseAPIResponse.isSuccess());
            binding.setErrorMsg(listAPIResponseAPIResponse.getErrorMsg());

            if (listAPIResponseAPIResponse.isSuccess())
                adapter.setData(listAPIResponseAPIResponse.getReturnData());
        });

        viewModel.loadCharacters(ids, episodeId);
    }
}
