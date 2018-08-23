package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EvolutionsFragment extends Fragment {
    private int id;


    private EvolutionRecyclerViewAdapter toAdapter;
    private EvolutionRecyclerViewAdapter fromAdapter;
    private UnitDialogViewModel viewModel;

    public static EvolutionsFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(UnitHelper.UNIT_ID, id);
        EvolutionsFragment fragment = new EvolutionsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt(UnitHelper.UNIT_ID);

        viewModel = ViewModelProviders.of(this).get(UnitDialogViewModel.class);

        toAdapter = new EvolutionRecyclerViewAdapter(getContext(), EvolutionRecyclerViewAdapter.TO);
        fromAdapter = new EvolutionRecyclerViewAdapter(getContext(), EvolutionRecyclerViewAdapter.FROM);

        viewModel.evolvesTo.observe(this, new Observer<List<Evolution>>() {
            @Override
            public void onChanged(@Nullable List<Evolution> evolutions) {
                toAdapter.setEvolutions(evolutions);
            }
        });

        viewModel.evolvesFrom.observe(this, new Observer<List<Evolution>>() {
            @Override
            public void onChanged(@Nullable List<Evolution> evolutions) {
                fromAdapter.setEvolutions(evolutions);
            }
        });


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_unit_evolutions, container, false);


        RecyclerView.LayoutManager l1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager l2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        RecyclerView evolvesToRecycler = root.findViewById(R.id.evolves_to_recycler);
        evolvesToRecycler.setAdapter(toAdapter);
        evolvesToRecycler.setLayoutManager(l1);

        RecyclerView evolvesFromRecycler = root.findViewById(R.id.evolves_from_recycler);
        evolvesFromRecycler.setAdapter(fromAdapter);
        evolvesFromRecycler.setLayoutManager(l2);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            viewModel.getEvolvesFrom(id);
            viewModel.getEvolvesTo(id);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
