package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.tabs;

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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.EvolutionAdapter;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.UnitDialog;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.UnitDialogViewModel;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EvolutionsFragment extends Fragment {
    private final EvolutionAdapter.OnEvolutionItemAdapterEvent ON_EVOLUTION_ITEM_ADAPTER_EVENT =
            new EvolutionAdapter.OnEvolutionItemAdapterEvent() {
                @Override
                public void onClick(int databaseId) {
                    if (id == databaseId) return;
                    //TODO Handle Material-NonUnit click
                    UnitDialog.newInstance(databaseId).show(getChildFragmentManager(), UnitDialog.class.getSimpleName());
                }

                @Override
                public void onLoadThumb(ImageView v, int id) {
                    Glide.with(getContext())
                            .load(API.getThumb(id))
                            .transition(DrawableTransitionOptions.withCrossFade())

                            .apply(new RequestOptions()
                                    .override(UnitHelper.THUMB_WIDTH, UnitHelper.THUMB_HEIGHT)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_nothumb)
                                    .fitCenter()
                            ).into(v);
                }
            };
    private int id;
    private EvolutionAdapter toAdapter;
    private EvolutionAdapter fromAdapter;
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

        toAdapter = new EvolutionAdapter(getContext(), EvolutionAdapter.TO);
        fromAdapter = new EvolutionAdapter(getContext(), EvolutionAdapter.FROM);

        viewModel.evolvesTo.observe(this, new Observer<List<Evolution>>() {
            @Override
            public void onChanged(@Nullable List<Evolution> evolutions) {
                toAdapter.setEvolutions(evolutions);
                toAdapter.setOnEvolutionItemAdapterEvent(ON_EVOLUTION_ITEM_ADAPTER_EVENT);
            }
        });

        viewModel.evolvesFrom.observe(this, new Observer<List<Evolution>>() {
            @Override
            public void onChanged(@Nullable List<Evolution> evolutions) {
                fromAdapter.setEvolutions(evolutions);
                fromAdapter.setOnEvolutionItemAdapterEvent(ON_EVOLUTION_ITEM_ADAPTER_EVENT);

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
