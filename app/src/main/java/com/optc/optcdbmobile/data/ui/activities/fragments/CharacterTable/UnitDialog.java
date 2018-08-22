package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.graphics.drawable.ColorDrawable;
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
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;

import java.util.ArrayList;
import java.util.List;


public class UnitDialog extends DialogFragment {

    private FragmentPagerAdapter adapter;
    private UnitProxy unit;

    public static UnitDialog newInstance(UnitProxy unit) {

        Bundle b = new Bundle();
        b.putParcelable(UnitHelper.UNIT_PARCELLABLE, unit);
        UnitDialog fragment = new UnitDialog();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        unit = args.getParcelable(UnitHelper.UNIT_PARCELLABLE);

        adapter = new DynamicViewPagerAdapter(getChildFragmentManager(), unit);
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
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(new ColorDrawable(UnitHelper.getTypeColor(unit.getType1(), getResources()))))
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
        private final String[] TITLES = new String[]{
                "General",
                "Abilities",
                "Limit break",
                "Evolutions",
                "Family"
        };
        private UnitProxy unit;

        DynamicViewPagerAdapter(FragmentManager fm, UnitProxy unit) {
            super(fm);
            this.unit = unit;

            fragments = new ArrayList<>();
            titles = new ArrayList<>();

            //if (unit.hasPotential)

            add(GeneralFragment.newInstance(unit), "General");

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
    }

}
