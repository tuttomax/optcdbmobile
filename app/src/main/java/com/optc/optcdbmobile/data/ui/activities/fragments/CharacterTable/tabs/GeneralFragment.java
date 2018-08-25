package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;
import com.optc.optcdbmobile.data.ui.activities.general.UnitParcelable;


//TODO Move Class and socket inside UnitDialog in order to show them inside the image

public class GeneralFragment extends Fragment {

    private UnitParcelable unit;

    public static GeneralFragment newInstance(UnitParcelable unit) {

        Bundle args = new Bundle();
        args.putParcelable(UnitHelper.UNIT_PARCELLABLE, unit);
        GeneralFragment fragment = new GeneralFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        unit = args.getParcelable(UnitHelper.UNIT_PARCELLABLE);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_unit_general, container, false);

        TextView comboTextView, maxlvlTextView, maxexpTextView,
                levelmaxTextView,
                hplvl1TextView,
                hpmaxTextView,
                atklvl1TextView,
                atkmaxTextView,
                rcvvl1TextView,
                rcvmaxTextView;

        comboTextView = root.findViewById(R.id.combo);
        maxlvlTextView = root.findViewById(R.id.maxlevel);
        maxexpTextView = root.findViewById(R.id.maxexp);

        levelmaxTextView = root.findViewById(R.id.lvlmax);
        hplvl1TextView = root.findViewById(R.id.hplvl1);
        hpmaxTextView = root.findViewById(R.id.hplvlmax);
        atklvl1TextView = root.findViewById(R.id.atklvl1);
        atkmaxTextView = root.findViewById(R.id.atklvlmax);
        rcvvl1TextView = root.findViewById(R.id.rcvlvl1);
        rcvmaxTextView = root.findViewById(R.id.rcvlvlmax);


        comboTextView.setText(String.format("Combo: %d", unit.getCombo()));
        maxlvlTextView.setText(String.format("Level Max: %d", unit.getLevelMax()));
        maxexpTextView.setText(String.format("Exp Max: %s", unit.getExpToMax()));

        levelmaxTextView.setText(String.valueOf(unit.getLevelMax()));
        hplvl1TextView.setText(String.valueOf(unit.getHpLevel1()));
        hpmaxTextView.setText(String.valueOf(unit.getMaxHp()));
        atklvl1TextView.setText(String.valueOf(unit.getAtkLevel1()));
        atkmaxTextView.setText(String.valueOf(unit.getMaxAtk()));
        rcvvl1TextView.setText(String.valueOf(unit.getRcvLevel1()));
        rcvmaxTextView.setText(String.valueOf(unit.getMaxRcv()));

        return root;
    }
}
