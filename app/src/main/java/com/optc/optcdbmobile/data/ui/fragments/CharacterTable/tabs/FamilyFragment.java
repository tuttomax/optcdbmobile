package com.optc.optcdbmobile.data.ui.fragments.CharacterTable.tabs;

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
import com.optc.optcdbmobile.data.database.entities.LocationInformation;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.ui.fragments.CharacterTable.DropsAdapter;
import com.optc.optcdbmobile.data.ui.fragments.CharacterTable.UnitDialogViewModel;
import com.optc.optcdbmobile.data.ui.general.UnitHelper;

import java.util.List;

public class FamilyFragment extends Fragment {

    private final DropsAdapter.OnDropsItemAdapterEvents ON_DROPS_ITEM_ADAPTER_EVENTS =
            new DropsAdapter.OnDropsItemAdapterEvents() {
                @Override
                public void OnClick(int locationId) {
                    //TODO Implement me
                }

                @Override
                public void OnLoadThumb(ImageView thumbView, int thumbId) {


                    Glide.with(getContext())
                            .load(API.getThumb(thumbId))
                            .transition(DrawableTransitionOptions.withCrossFade())

                            .apply(new RequestOptions()
                                    .override(UnitHelper.THUMB_WIDTH, UnitHelper.THUMB_HEIGHT)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .error(R.drawable.ic_nothumb)
                                    .fitCenter()
                            ).into(thumbView);

                }

            };
    int id;
    DropsAdapter adapter;
    UnitDialogViewModel viewModel;

    public static FamilyFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(UnitHelper.UNIT_ID, id);
        FamilyFragment fragment = new FamilyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getArguments().getInt(UnitHelper.UNIT_ID);

        viewModel = ViewModelProviders.of(getActivity()).get(UnitDialogViewModel.class);
        viewModel.familyDrops.observe(this, new Observer<List<LocationInformation>>() {
            @Override
            public void onChanged(@Nullable List<LocationInformation> locationInformations) {
                adapter.setList(locationInformations);
            }
        });

        adapter = new DropsAdapter(getContext());
        adapter.setEvents(ON_DROPS_ITEM_ADAPTER_EVENTS);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_unit_family, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_family);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getFamilyDropsOf(id);
    }
}
