package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.ui.activities.MainViewModel;

import java.util.List;

public class CharacterTableFragment extends Fragment {


    CharacterTableAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new CharacterTableAdapter(getContext());

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getUnits().observe(this, new Observer<List<Unit>>() {
            @Override
            public void onChanged(@Nullable List<Unit> units) {
                adapter.setList(units);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.character_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        boolean card_on = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(Constants.Settings.pref_card_layout_key, false);
        RecyclerView.LayoutManager layoutManager = card_on ? new GridLayoutManager(getContext(), 2) //TODO Some math for calculating efficent number of columns
                : new LinearLayoutManager(getContext());

        final RecyclerView recyclerView = view.findViewById(R.id.character_table_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


}
