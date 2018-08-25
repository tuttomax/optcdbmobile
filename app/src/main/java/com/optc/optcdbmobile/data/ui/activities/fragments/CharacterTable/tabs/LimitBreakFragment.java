package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.tabs;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Limit;
import com.optc.optcdbmobile.data.database.entities.Potential;
import com.optc.optcdbmobile.data.database.entities.PotentialDescription;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.UnitDialogViewModel;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.LimitTextView;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.PotentialTextView;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LimitBreakFragment extends Fragment {
    List<Potential> potentials;
    List<PotentialDescription> potentialDescriptions;
    List<Limit> limits;
    private int id;

    public static LimitBreakFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(UnitHelper.UNIT_ID, id);
        LimitBreakFragment fragment = new LimitBreakFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = getArguments().getInt(UnitHelper.UNIT_ID);
        UnitDialogViewModel viewModel = ViewModelProviders.of(getActivity()).get(UnitDialogViewModel.class);

        try {
            potentials = viewModel.getPotentials(id);
            potentialDescriptions = viewModel.getPotentialDescriptions(id);
            limits = viewModel.getLimits(id);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_unit_limit_break, container, false);
        LimitTextView limitTextView = root.findViewById(R.id.limit_informative);
        PotentialTextView potentialTextView = root.findViewById(R.id.potential_informative);

        limitTextView.setLimits(limits).buildText();
        potentialTextView.setPotentials(potentials).setPotentialDescriptions(potentialDescriptions).buildText();

        return root;
    }
}
