package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.database.filters.Filter;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.ui.activities.MainViewModel;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.FilterContext;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.FilterInfo;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.FilterType;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.commands.NormalCommand;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;
import com.optc.optcdbmobile.data.ui.activities.general.UnitParcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterTableFragment extends Fragment implements FilterContext {


    private final static Pattern onlyNumber = Pattern.compile("^\\d+?$");
    private final static Pattern onlyString = Pattern.compile("^\\w+?$");
    private final List<FilterInfo> filters = new ArrayList<>();
    private final List<Filter> activatedFilter = new ArrayList<>();


    private final DiffCharacterTableAdapter.OnUnitItemAdapterEvents ON_UNIT_ITEM_ADAPTER_EVENTS = new DiffCharacterTableAdapter.OnUnitItemAdapterEvents() {
        @Override
        public void onClick(Unit unit) {
            UnitDialog.newInstance(new UnitParcelable(unit)).show(getChildFragmentManager(), UnitDialog.class.getSimpleName());
        }

        @Override
        public void onLoadThumb(ImageView view, int id) {
            Glide.with(getContext())
                    .load(API.getThumb(id))
                    .transition(DrawableTransitionOptions.withCrossFade())

                    .apply(new RequestOptions()
                            .override(UnitHelper.THUMB_WIDTH, UnitHelper.THUMB_HEIGHT)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_nothumb)
                            .fitCenter()
                    ).into(view);
        }
    };
    DiffCharacterTableAdapter adapter;
    private RecyclerView recyclerView;
    private MainViewModel mainViewModel;
    private int scrollPosition = 0;
    private boolean hasChanged = false;
    private boolean firstLaunch;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        firstLaunch = true;
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.units.observe(this, new Observer<List<Unit>>() {
            @Override
            public void onChanged(@Nullable List<Unit> units) {
                Snackbar.make(getView(), String.format("Found %d characters", units.size()), Snackbar.LENGTH_SHORT).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {

                        Snackbar.make(transientBottomBar.getView(), "Wait a moment...", Snackbar.LENGTH_LONG);
                        super.onDismissed(transientBottomBar, event);
                    }
                });
                adapter.submitList(units);

            }
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_character_table, menu);

        MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Write ID or name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                Matcher matcherNumber = onlyNumber.matcher(query);
                Matcher matcherString = onlyString.matcher(query);

                if (matcherNumber.find()) {
                    String position = query.substring(matcherNumber.start(), matcherNumber.end());
                    scrollPosition = Integer.valueOf(position);
                    recyclerView.getLayoutManager().scrollToPosition(scrollPosition);
                    //TODO Create ItemDecorator: ScrollToPositionItemDecorator

                } else if (matcherString.find()) {
                    String name = query.substring(matcherString.start(), matcherString.end());
                    mainViewModel.getUnitsWithName(name);

                    hasChanged = true;
                    firstLaunch = false;
                }
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (hasChanged) {
                    mainViewModel.getUnits();
                    hasChanged = false;
                    firstLaunch = false;
                }
                return false;
            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new DiffCharacterTableAdapter(getContext());
        adapter.setOnUnitItemAdapterEvents(ON_UNIT_ITEM_ADAPTER_EVENTS);

        recyclerView = view.findViewById(R.id.character_table_recycler_view);


        /*
         * Always true cause a BUG
         * BUG: Prevent NotifyItemInserted to update UI.
         * https://stackoverflow.com/questions/39683237/android-recyclerview-adapter-notifyiteminserted-and-notifyitemmoved-at-index-0/40373122#40373122
         */
        recyclerView.setHasFixedSize(!firstLaunch);
        Log.i(CharacterTableFragment.class.getSimpleName(), "Settings setHasFixedSize to " + !firstLaunch);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(CharacterTableFragment.this).resumeRequests();
                } else {
                    Glide.with(CharacterTableFragment.this).pauseAllRequests();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        mainViewModel.getUnits();


        initFilters();
        initUiFilters(view);


    }

    private void initFilters() {
        filters.add(new FilterInfo(FilterType.HEADER | FilterType.COLOR));
        filters.add(new FilterInfo(FilterType.COLOR).setLabel("STR").setCommand(new NormalCommand()));
        filters.add(new FilterInfo(FilterType.COLOR).setLabel("QCK").setCommand(new NormalCommand()));
        filters.add(new FilterInfo(FilterType.COLOR).setLabel("DEX").setCommand(new NormalCommand()));
        filters.add(new FilterInfo(FilterType.COLOR).setLabel("PSY").setCommand(new NormalCommand()));
        filters.add(new FilterInfo(FilterType.COLOR).setLabel("INT").setCommand(new NormalCommand()));

        filters.add(new FilterInfo(FilterType.HEADER | FilterType.CLASS));
        filters.add(new FilterInfo(FilterType.HEADER | FilterType.RARITY));
        filters.add(new FilterInfo(FilterType.HEADER | FilterType.COST));
        filters.add(new FilterInfo(FilterType.HEADER | FilterType.DROP));

    }

    private void initUiFilters(View view) {
        NavigationView nav = view.findViewById(R.id.nav_filters);
        ScrollView parentFilter = nav.findViewById(R.id.parent_filters);
        parentFilter.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        LinearLayout root = new LinearLayout(getContext());
        root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        root.setOrientation(LinearLayout.VERTICAL);
        parentFilter.addView(root);

        for (final FilterInfo info : filters) {
            if (info.isHeader()) {
                root.addView(getUiHeader(getContext(), info));
            } else {
                root.addView(getUiFilter(getContext(), info));
            }

        }

    }

    private AppCompatTextView getUiHeader(Context context, FilterInfo info) {
        AppCompatTextView textView = new AppCompatTextView(context);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setTextAppearance(getContext(), android.R.attr.textAppearanceLarge);
        textView.setText(info.getLabel());
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        return textView;
    }

    private AppCompatCheckBox getUiFilter(final Context context, final FilterInfo info) {
        final AppCompatCheckBox checkbox = new AppCompatCheckBox(getContext());
        checkbox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checkbox.setText(info.getLabel());
        checkbox.setChecked(false);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(CharacterTableFragment.class.getSimpleName(), String.format("Clicked on " + info.getLabel()));

                buttonView.jumpDrawablesToCurrentState();

                if (isChecked) {
                    info.getCommand().execute(info.getFilter(), CharacterTableFragment.this);
                } else {
                    info.getCommand().rollback(info.getFilter(), CharacterTableFragment.this);
                }
                Log.i(CharacterTableFragment.class.getSimpleName(), String.format("%s filter: %s", checkbox.isChecked() ? "Added" : "Removed", info.getLabel()));
                Log.i(CharacterTableFragment.class.getSimpleName(), "Number of filter activated: " + activatedFilter.size());
            }
        });

        return checkbox;
    }

    @Override
    public List<Filter> getUiFilters() {
        return activatedFilter;
    }
}
