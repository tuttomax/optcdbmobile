package com.optc.optcdbmobile.data.database.threading;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.optc.optcdbmobile.R;

public class ListTaskDialog extends DialogFragment {

    public final static String TAG = "ListTaskDialog";
    private final ListTaskAdapter adapter = new ListTaskAdapter();


    public static ListTaskDialog newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title_key", title);
        ListTaskDialog fragment = new ListTaskDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NO_TITLE | DialogFragment.STYLE_NO_INPUT | DialogFragment.STYLE_NO_FRAME, R.style.BaseAppTheme_Dialog);
        setCancelable(false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.tasks_dialog_layout, container, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        root.setLayoutParams(params);

        TextView titleView = (TextView) root.findViewById(android.R.id.title);
        titleView.setText(getArguments().getString("title_key"));

        RecyclerView recyclerView = root.findViewById(R.id.tasks_dialog_list);
        setupRecycleView(recyclerView);

        return root;
    }

    private void setupRecycleView(RecyclerView view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getDialog().getContext());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        view.setAdapter(adapter);
    }

    public TaskDelegate getAdapterDelegate() {
        return adapter;
    }

    @Override
    public void dismiss() {
        adapter.termination();
        super.dismiss();
    }
}
