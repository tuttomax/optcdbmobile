package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.database.filters.FilterCollector;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.ui.activities.MainViewModel;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;
import com.optc.optcdbmobile.data.ui.activities.general.UnitParcelable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterTableFragment extends Fragment {


    private final static Pattern onlyNumber = Pattern.compile("^\\d+?$");
    private final static Pattern onlyString = Pattern.compile("^\\w+?$");

    private boolean fromFilter = false;

    DiffCharacterTableAdapter characterTableAdapter;
    FilterAdapter filterAdapter;

    private RecyclerView characterTableRecyclerView;
    private RecyclerView filterRecyclerView;
    private FilterCollector filterCollector;

    private MainViewModel mainViewModel;
    private int scrollPosition = 0;
    private boolean hasChanged = false;
    private boolean firstLaunch;

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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        firstLaunch = true;

        characterTableAdapter = new DiffCharacterTableAdapter(getContext());
        characterTableAdapter.setOnUnitItemAdapterEvents(ON_UNIT_ITEM_ADAPTER_EVENTS);

        filterCollector = new FilterCollector();
        filterAdapter = new FilterAdapter(getContext());

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.units.observe(this, new Observer<List<Unit>>() {
            @Override
            public void onChanged(@Nullable List<Unit> units) {
                if (!fromFilter) characterTableAdapter.submitList(units);
                else {
                    characterTableAdapter.submitList(null);
                    characterTableAdapter.submitList(units);
                    fromFilter = false;
                }
            }
        });

        mainViewModel.getUnits();


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
                    characterTableRecyclerView.getLayoutManager().scrollToPosition(scrollPosition);
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
                    fromFilter = true;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_character_table, container, false);
        characterTableRecyclerView = root.findViewById(R.id.character_table_recycler_view);
        /*
         * Always true cause a BUG
         * BUG: Prevent NotifyItemInserted to update UI.
         * https://stackoverflow.com/questions/39683237/android-recyclerview-adapter-notifyiteminserted-and-notifyitemmoved-at-index-0/40373122#40373122
         */
        characterTableRecyclerView.setHasFixedSize(false);

        filterRecyclerView = root.findViewById(R.id.filter_recyclerview);
        filterRecyclerView.setHasFixedSize(false);


        AppCompatButton submitButton = root.findViewById(R.id.submit_query);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(CharacterTableFragment.class.getSimpleName(), filterCollector.getQuery());
                fromFilter = true;
                mainViewModel.getUnitsWithFilter(filterCollector.getQuery());
            }
        });

        AppCompatButton clearFiltersButton = root.findViewById(R.id.clear_filters);
        clearFiltersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAdapter.clearFilters();
                fromFilter = true;
                mainViewModel.getUnits();
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        characterTableRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        characterTableRecyclerView.setAdapter(characterTableAdapter);
        characterTableRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        filterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        filterRecyclerView.setAdapter(filterAdapter);
        filterAdapter.setCollector(filterCollector);
        filterAdapter.initFilters();
    }


}
