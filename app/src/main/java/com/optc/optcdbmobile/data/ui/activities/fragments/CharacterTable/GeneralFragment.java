package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

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


//TODO Move Class and socket inside UnitDialog in order to show them inside the image

public class GeneralFragment extends Fragment {

    private UnitProxy unit;

    public static GeneralFragment newInstance(UnitProxy unit) {

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
        View root = inflater.inflate(R.layout.fragment_general_info, container, false);

        TextView class1TextView, class2TextView, socketsTextView, comboTextView, maxlvlTextView, maxexpTextView;
        class1TextView = root.findViewById(R.id.class1);
        class2TextView = root.findViewById(R.id.class2);
        socketsTextView = root.findViewById(R.id.sockets);
        comboTextView = root.findViewById(R.id.combo);
        maxlvlTextView = root.findViewById(R.id.maxlevel);
        maxexpTextView = root.findViewById(R.id.maxexp);

        class1TextView.setText(String.format("Class1: %s", unit.getClass1()));
        class2TextView.setText(String.format("Class2: %s", unit.getClass2()));
        socketsTextView.setText(String.format("Socket: %d", unit.getSocket()));
        comboTextView.setText(String.format("Combo: %d", unit.getCombo()));
        maxlvlTextView.setText(String.format("Level Max: %d", unit.getLevelMax()));
        maxexpTextView.setText(String.format("Exp Max: %s", unit.getExpToMax()));

        return root;
    }
}
