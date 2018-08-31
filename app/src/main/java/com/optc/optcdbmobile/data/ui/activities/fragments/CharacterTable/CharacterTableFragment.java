package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
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
import com.optc.optcdbmobile.data.database.filters.FilterType;
import com.optc.optcdbmobile.data.database.filters.__Filter;
import com.optc.optcdbmobile.data.database.filters.__FilterContext;
import com.optc.optcdbmobile.data.database.filters.__FilterInfo;
import com.optc.optcdbmobile.data.database.filters.__Filters;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.ui.activities.MainViewModel;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.commands.NormalCommand;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;
import com.optc.optcdbmobile.data.ui.activities.general.UnitParcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterTableFragment extends Fragment {


    private final static Pattern onlyNumber = Pattern.compile("^\\d+?$");
    private final static Pattern onlyString = Pattern.compile("^\\w+?$");
    private final List<__FilterInfo> filters = new ArrayList<>();
    private final __FilterContext filterContext = new __CharacterTableFilterContext();
    private boolean fromFilter = false;

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

                if (!fromFilter) adapter.submitList(units);
                else {
                    adapter.submitList(null);
                    adapter.submitList(units);
                    fromFilter = false;
                }
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

        //TODO Create factory class
        filters.add(new __FilterInfo(FilterType.HEADER | FilterType.COLOR));
        filters.add(new __FilterInfo(FilterType.COLOR).setLabel(UnitHelper.STR_STRING).setCommand(new NormalCommand()).setFilter(__Filters.COLOR1_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.STR_STRING))));
        filters.add(new __FilterInfo(FilterType.COLOR).setLabel(UnitHelper.QCK_STRING).setCommand(new NormalCommand()).setFilter(__Filters.COLOR1_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.QCK_STRING))));
        filters.add(new __FilterInfo(FilterType.COLOR).setLabel(UnitHelper.DEX_STRING).setCommand(new NormalCommand()).setFilter(__Filters.COLOR1_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.DEX_STRING))));
        filters.add(new __FilterInfo(FilterType.COLOR).setLabel(UnitHelper.PSY_STRING).setCommand(new NormalCommand()).setFilter(__Filters.COLOR1_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.PSY_STRING))));
        filters.add(new __FilterInfo(FilterType.COLOR).setLabel(UnitHelper.INT_STRING).setCommand(new NormalCommand()).setFilter(__Filters.COLOR1_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.INT_STRING))));

        filters.add(new __FilterInfo(FilterType.HEADER | FilterType.CLASS));
        filters.add(new __FilterInfo(FilterType.CLASS).setLabel(UnitHelper.FIGHTER_STRING).setCommand(new NormalCommand()).setFilter(__Filters.CLASS1_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.FIGHTER_STRING))));
        filters.add(new __FilterInfo(FilterType.CLASS).setLabel(UnitHelper.SLASHER_STRING).setCommand(new NormalCommand()).setFilter(__Filters.CLASS1_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.SLASHER_STRING))));
        filters.add(new __FilterInfo(FilterType.CLASS).setLabel(UnitHelper.SHOOTER_STRING).setCommand(new NormalCommand()).setFilter(__Filters.CLASS1_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.SHOOTER_STRING))));
        filters.add(new __FilterInfo(FilterType.CLASS).setLabel(UnitHelper.STRIKER_STRING).setCommand(new NormalCommand()).setFilter(__Filters.CLASS1_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.STRIKER_STRING))));
        filters.add(new __FilterInfo(FilterType.CLASS).setLabel(UnitHelper.DRIVEN_STRING).setCommand(new NormalCommand()).setFilter(__Filters.CLASS2_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.DRIVEN_STRING))));
        filters.add(new __FilterInfo(FilterType.CLASS).setLabel(UnitHelper.POWERHOUSE_STRING).setCommand(new NormalCommand()).setFilter(__Filters.CLASS2_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.POWERHOUSE_STRING))));
        filters.add(new __FilterInfo(FilterType.CLASS).setLabel(UnitHelper.FREE_SPIRIT_STRING).setCommand(new NormalCommand()).setFilter(__Filters.CLASS2_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.FREE_SPIRIT_STRING))));
        filters.add(new __FilterInfo(FilterType.CLASS).setLabel(UnitHelper.CEREBRAL_STRING).setCommand(new NormalCommand()).setFilter(__Filters.CLASS2_FILTER.clone().setArgs(String.format("'%s'", UnitHelper.CEREBRAL_STRING))));


        filters.add(new __FilterInfo(FilterType.HEADER | FilterType.RARITY));
        filters.add(new __FilterInfo(FilterType.HEADER | FilterType.COST));
        filters.add(new __FilterInfo(FilterType.HEADER | FilterType.DROP));
        filters.add(new __FilterInfo(FilterType.DROP).setLabel("Global units").setCommand(new NormalCommand()).setFilter(__Filters.GLOBAL_FILTER));
        filters.add(new __FilterInfo(FilterType.DROP).setLabel("Japan exclusive").setCommand(new NormalCommand()).setFilter(__Filters.JAPAN_FILTER));
        filters.add(new __FilterInfo(FilterType.DROP).setLabel("In RR pool").setCommand(new NormalCommand()).setFilter(__Filters.RARE_RECRUIT_FILTER));

        filters.add(new __FilterInfo(FilterType.HEADER | FilterType.EXCLUSION));
        filters.add(new __FilterInfo(FilterType.EXCLUSION).setLabel("Exclude base form").setCommand(new NormalCommand()).setFilter(__Filters.EXCLUDE_BASE_FORM));
        filters.add(new __FilterInfo(FilterType.EXCLUSION).setLabel("Exclude fodder").setCommand(new NormalCommand()).setFilter(__Filters.EXCLUDE_FODDER_FILTER));
        filters.add(new __FilterInfo(FilterType.EXCLUSION).setLabel("Exclude booster and evolver").setCommand(new NormalCommand()).setFilter(__Filters.EXCLUDE_BOOSTER_AND_EVOLVER));


    }


    private void initUiFilters(final View view) {
        final NavigationView nav = view.findViewById(R.id.nav_filters);
        final ScrollView scrollView = nav.findViewById(R.id.scrollview);
        final LinearLayout container = scrollView.findViewById(R.id.filters_container);

        AppCompatButton clearAllFiltersButton = nav.findViewById(R.id.clear_filters);
        clearAllFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int index = 0; index < container.getChildCount(); index++) {
                    View child = container.getChildAt(index);
                    if (child instanceof AppCompatCheckBox) {
                        ((AppCompatCheckBox) child).setChecked(false);
                        if (hasChanged) mainViewModel.getUnits();
                        ((DrawerLayout) nav.getParent()).closeDrawer(Gravity.END);
                    }
                }

            }
        });

        AppCompatButton submitButton = nav.findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                __Filter finalQuery = filterContext.getQuery();
                Log.i(CharacterTableFragment.class.getSimpleName(), finalQuery.build());
                Snackbar.make(v, "This will take a while...", Snackbar.LENGTH_LONG).show();
                fromFilter = true;
                mainViewModel.getUnitsWithFilter(finalQuery.build());
                hasChanged = true;
                ((DrawerLayout) nav.getParent()).closeDrawer(Gravity.END);
            }
        });


        for (final __FilterInfo info : filters) {
            if (info.isHeader()) {
                setUiHeader(container, info);
            } else {
                setUiFilter(container, info);
            }
        }

    }

    private void setUiHeader(ViewGroup root, __FilterInfo info) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL;

        AppCompatTextView textView = new AppCompatTextView(getContext());
        textView.setLayoutParams(params);
        textView.setTextAppearance(getContext(), android.R.attr.textAppearanceLarge);
        textView.setText(info.getLabel());
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));

        AppCompatImageView imageView = new AppCompatImageView(getContext());
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(R.drawable.separator);

        root.addView(textView);
        root.addView(imageView);
    }

    private void setUiFilter(ViewGroup root, final __FilterInfo info) {
        final AppCompatCheckBox checkbox = new AppCompatCheckBox(getContext());
        checkbox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        checkbox.setText(info.getLabel());
        checkbox.setChecked(false);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.jumpDrawablesToCurrentState(); //without this there is a strange BUG:flickering when changing status
                if (isChecked) {
                    info.getCommand().execute(info, filterContext);
                } else {
                    info.getCommand().rollback(info, filterContext);
                }
            }
        });
        root.addView(checkbox);
    }

}
