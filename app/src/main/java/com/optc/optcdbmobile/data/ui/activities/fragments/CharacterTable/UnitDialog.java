package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.DoubleColorDrawable;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.controls.RepeatableDrawableWidget;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.tabs.EvolutionsFragment;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.tabs.FamilyFragment;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.tabs.GeneralFragment;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.tabs.LimitBreakFragment;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.tabs.ManualsFragment;
import com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable.tabs.SpecialFragment;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;
import com.optc.optcdbmobile.data.ui.activities.general.UnitParcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class UnitDialog extends DialogFragment {

    private DynamicViewPagerAdapter adapter;
    private UnitParcelable unit;

    public static UnitDialog newInstance(UnitParcelable unit) {

        Bundle b = new Bundle();
        b.putParcelable(UnitHelper.UNIT_PARCELLABLE, unit);
        UnitDialog fragment = new UnitDialog();
        fragment.setArguments(b);
        return fragment;
    }

    public static UnitDialog newInstance(int id) {
        Bundle b = new Bundle();
        b.putInt(UnitHelper.UNIT_ID, id);
        UnitDialog fragment = new UnitDialog();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UnitDialogViewModel viewModel = ViewModelProviders.of(this).get(UnitDialogViewModel.class);

        Bundle args = getArguments();

        unit = args.getParcelable(UnitHelper.UNIT_PARCELLABLE);
        if (unit == null) {
            int id = args.getInt(UnitHelper.UNIT_ID);
            try {
                unit = new UnitParcelable(viewModel.getUnit(id));
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        adapter = new DynamicViewPagerAdapter(getChildFragmentManager(), unit);


        try {
            adapter.setHasCaptain(viewModel.unitHasCaptain(unit.getDatabaseId()));
            adapter.setHasSpecial(viewModel.unitHasSpecial(unit.getDatabaseId()));
            adapter.setHasSailor(viewModel.unitHasSailor(unit.getDatabaseId()));
            adapter.setHasPotential(viewModel.unitHasPotential(unit.getDatabaseId()));
            adapter.setHasLimit(viewModel.unitHasLimit(unit.getDatabaseId()));
            adapter.setHasEvolutions(viewModel.unitHasEvolutions(unit.getDatabaseId()));
            adapter.setHasEvolvesFrom(viewModel.unitHasEvolvesFrom(unit.getDatabaseId()));
            adapter.setHasManuals(viewModel.unitHasManuals(unit.getDatabaseId()));
            adapter.setHasFamily(viewModel.unitHasFamily(unit.getDatabaseId()));


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        adapter.buildTabs();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_unit, container, false);

        TextView unitName, unitCost;
        RepeatableDrawableWidget starWidget, socketWidget;
        ImageView class1ImageView, class2ImageView;

        unitName = root.findViewById(R.id.unit_name);
        starWidget = root.findViewById(R.id.unit_stars);
        socketWidget = root.findViewById(R.id.unit_sockets);
        unitCost = root.findViewById(R.id.unit_cost);
        class1ImageView = root.findViewById(R.id.unit_class1);
        class2ImageView = root.findViewById(R.id.unit_class2);

        unitName.setText(unit.getName());

        Float stars = unit.getStars();
        if (stars == 6.5f || stars == 5.5f) {
            starWidget.setGeneralDrawable(R.drawable.ic_star_red);
            starWidget.setCount(stars.intValue());
        } else {
            starWidget.setGeneralDrawable(R.drawable.ic_star_yellow);
            starWidget.setCount(stars.intValue());
        }

        socketWidget.setCount(unit.getSocket());

        unitCost.setText(String.format("Cost:\n%s", unit.getCost()));


        ImageView unitImage = root.findViewById(R.id.unit_image);
        Glide
                .with(this)
                .load(API.getBigImage(unit.getDatabaseId()))
                .apply(new RequestOptions()
                        .override(580)
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_no_bigimage)
                        .error(R.drawable.ic_no_bigimage))
                .into(unitImage);


        if (unit.getClass1() == null) class1ImageView.setVisibility(View.GONE);
        else
            class1ImageView.
                    setImageDrawable(UnitHelper.getClassDrawable(unit.getClass1(), getResources()));

        if (unit.getClass2() == null) class2ImageView.setVisibility(View.GONE);
        else class2ImageView.
                setImageDrawable(UnitHelper.getClassDrawable(unit.getClass2(), getResources()));


        ViewPager pager = root.findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
        TabLayout tabLayout = root.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);

        if (unit.getType2() == null)
            root.setBackgroundColor(UnitHelper.getTypeColor(unit.getType1(), getResources()));
        else {

            DoubleColorDrawable doubleColorDrawable = new DoubleColorDrawable(
                    UnitHelper.getTypeColor(unit.getType1(), getResources()),
                    UnitHelper.getTypeColor(unit.getType2(), getResources())
            );

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                root.setBackground(doubleColorDrawable);
            } else {
                root.setBackgroundDrawable(doubleColorDrawable);
            }

        }

        return root;
    }


    private class DynamicViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments;
        private final List<String> titles;

        private boolean hasSpecial;
        private boolean hasCaptain;
        private boolean hasSailor;
        private boolean hasLimit;
        private boolean hasPotential;
        private boolean hasEvolvesFrom;
        private boolean hasEvolutions;
        private boolean hasFamily;
        private boolean hasManuals;

        private UnitParcelable unit;

        DynamicViewPagerAdapter(FragmentManager fm, UnitParcelable unit) {
            super(fm);
            this.unit = unit;

            fragments = new ArrayList<>();
            titles = new ArrayList<>();


        }

        public void buildTabs() {

            add(GeneralFragment.newInstance(unit), "General");

            if (hasCaptain || hasSpecial || hasSailor) {

                add(SpecialFragment.newInstance(unit.getDatabaseId()), "Abilities");
            }

            if (hasLimit || hasPotential) {
                add(LimitBreakFragment.newInstance(unit.getDatabaseId()), "Limit Break");
            }

            if (hasEvolutions || hasEvolvesFrom) {
                add(EvolutionsFragment.newInstance(unit.getDatabaseId()), "Evolutions");
            }

            if (hasManuals) {
                add(ManualsFragment.newInstance(unit.getDatabaseId()), "Manuals");
            }

            if (hasFamily) {
                add(FamilyFragment.newInstance(unit.getDatabaseId()), "Family");
            }

        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return titles.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        private void add(Fragment fragment, String title) {
            fragments.add(fragment);
            titles.add(title);
        }

        void setHasSpecial(boolean hasSpecial) {
            this.hasSpecial = hasSpecial;
        }

        void setHasCaptain(boolean hasCaptain) {
            this.hasCaptain = hasCaptain;
        }

        void setHasSailor(boolean hasSailor) {
            this.hasSailor = hasSailor;
        }

        void setHasLimit(boolean hasLimit) {
            this.hasLimit = hasLimit;
        }

        void setHasPotential(boolean hasPotential) {
            this.hasPotential = hasPotential;
        }

        void setHasEvolvesFrom(boolean hasEvolvesFrom) {
            this.hasEvolvesFrom = hasEvolvesFrom;
        }

        void setHasEvolutions(boolean hasEvolutions) {
            this.hasEvolutions = hasEvolutions;
        }

        public void setHasFamily(boolean hasFamily) {
            this.hasFamily = hasFamily;
        }

        public void setHasManuals(boolean hasManuals) {
            this.hasManuals = hasManuals;
        }
    }

}
