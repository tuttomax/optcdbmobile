package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Captain;
import com.optc.optcdbmobile.data.database.entities.CaptainDescription;
import com.optc.optcdbmobile.data.database.entities.SailorDescription;
import com.optc.optcdbmobile.data.database.entities.Special;
import com.optc.optcdbmobile.data.database.entities.SpecialDescription;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SpecialFragment extends Fragment {

    private int id;
    UnitDialogViewModel viewModel;

    Captain captain;
    List<CaptainDescription> captainDescriptions;

    Special special;
    List<SpecialDescription> specialDescriptions;

    List<SailorDescription> sailorDescriptions;

    public static SpecialFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(UnitHelper.UNIT_ID, id);
        SpecialFragment fragment = new SpecialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt(UnitHelper.UNIT_ID);

        viewModel = ViewModelProviders.of(this).get(UnitDialogViewModel.class);

        try {
            captain = viewModel.getCaptain(id);
            captainDescriptions = viewModel.getCaptainDescriptions(id);

            special = viewModel.getSpecial(id);
            specialDescriptions = viewModel.getSpecialDescriptions(id);

            sailorDescriptions = viewModel.getSailorDescriptions(id);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_unit_abilities, container, false);
        CaptainTextView captainTextView = root.findViewById(R.id.captain_informative);
        SpecialTextView specialTextView = root.findViewById(R.id.special_informative);
        SailorTextView sailorTextView = root.findViewById(R.id.sailor_informative);

        captainTextView.setCaptain(captain).setCaptainDescription(captainDescriptions).buildText();
        specialTextView.setSpecial(special).setSpecialDescriptions(specialDescriptions).buildText();
        sailorTextView.setSailorDescriptions(sailorDescriptions).buildText();

        return root;
    }


}
