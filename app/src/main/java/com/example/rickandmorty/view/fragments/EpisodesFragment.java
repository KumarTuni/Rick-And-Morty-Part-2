package com.example.rickandmorty.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rickandmorty.R;
import com.example.rickandmorty.databinding.FragmentEpisodesBinding;
import com.example.rickandmorty.view.adapters.EpisodesAdapter;
import com.example.rickandmorty.viewModel.EpisodesViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

/**
 * Created by Rohin on 8/24/2019 @8:16 PM.
 */
public class EpisodesFragment extends Fragment {

    private EpisodesViewModel viewModel;
    private FragmentEpisodesBinding binding;
    private EpisodesAdapter adapter;
    private NavController navController;


    public EpisodesFragment() {
        adapter = new EpisodesAdapter(itemValue -> {
            StringBuilder ids = new StringBuilder();
            for (String st : itemValue.getCharacters()) {
                ids.append(",").append(st.substring(st.lastIndexOf("/") + 1));
            }
            navController.navigate(
                    EpisodesFragmentDirections.toDetails(ids.toString(), itemValue.getName(), itemValue.getId())
            );
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.NewInstanceFactory().create(EpisodesViewModel.class);
        //    viewModel.loadInitialPage();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_episodes, container, false);
        binding.setEpisodesAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        binding.pagedRV.setRetryListner(() -> viewModel.retry());

        viewModel.getNetworkLiveData().observe(this, listAPIResponseAPIResponse -> {
            if (listAPIResponseAPIResponse.isEmpty()) {
                binding.pagedRV.empty();
            } else if (listAPIResponseAPIResponse.isHasError())
                if (listAPIResponseAPIResponse.getReturnData().getInfo().getPageIdx() == 1)
                    binding.pagedRV.error(listAPIResponseAPIResponse.getErrorMsg());
                else
                    binding.pagedRV.loadingMoreError(listAPIResponseAPIResponse.getErrorMsg());
            else if (listAPIResponseAPIResponse.isRunning())
                if (listAPIResponseAPIResponse.getReturnData().getInfo().getPageIdx() == 1)
                    binding.pagedRV.loading();
                else
                    binding.pagedRV.loadingMore();
            else
                binding.pagedRV.success();
        });
        viewModel.getEpisodesListLiveData().observe(this, episodes -> adapter.submitList(episodes));
    }
}
