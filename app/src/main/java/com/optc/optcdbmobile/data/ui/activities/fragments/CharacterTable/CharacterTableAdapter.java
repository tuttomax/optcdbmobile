package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.Constants;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;

import java.util.List;

public class CharacterTableAdapter extends RecyclerView.Adapter<CharacterTableAdapter.BaseHolder> {

    private static final int THUMB_WIDTH = 96;
    private static final int THUMB_HEIGHT = 96;
    private static final int CARD_THUMB_WIDTH = 120;
    private static final int CARD_THUMB_HEIGHT = 120;
    Context context;
    private List<Unit> list;
    private LayoutInflater inflater;
    private boolean card_view_on;
    private ItemClickListner itemClickListner;

    public CharacterTableAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        card_view_on = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constants.Settings.pref_card_layout_key, false);
    }

    public void setList(List<Unit> list) {
        this.list = list;

        //HACK: Fake unit
        list.add(0, new Unit(-1, "", "", "", "", "", -1,
                (byte) -1, -1, -1, -1, -1, -1, -1,
                (byte) -1, (byte) -1, (byte) -1, 0.0f));

        notifyDataSetChanged();
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (getItemViewType(i) == 0) {
            return new HeaderHolder(inflater.inflate(R.layout.header_character_table, viewGroup, false));
        }
        if (card_view_on)
            return new UnitHolder(inflater.inflate(R.layout.item_card_character_table, viewGroup, false));

        return new UnitHolder(inflater.inflate(R.layout.item_character_table, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int i) {

        if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.idTextView.setText("Id");
            headerHolder.idTextView.setGravity(Gravity.CENTER);

            headerHolder.thumbImageView.setText("   ");

            headerHolder.nameTextView.setText("Name");
            headerHolder.colorsTextView.setText("Type(s)");
            headerHolder.starsTextView.setText("Stars");
            headerHolder.atkTextView.setText("ATK");
            headerHolder.hpTextView.setText("HP");
            headerHolder.rcvTextView.setText("RCV");

        } else if (holder instanceof UnitHolder) {
            UnitHolder unitHolder = (UnitHolder) holder;
            final Unit unit = list.get(unitHolder.getAdapterPosition());

            unitHolder.idTextView.setText(String.valueOf(unit.getId()));

            String url = API.getThumb(unit.getId());

            Glide.with(context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())

                    .apply(new RequestOptions()
                            .override(THUMB_WIDTH, THUMB_HEIGHT)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.ic_nothumb)
                            .fitCenter()
                    ).into(((UnitHolder) holder).thumbImageView);

            unitHolder.nameTextView.setText(unit.getName());

            if (unit.getType1() != null) {
                unitHolder.colors1TextView.setText(unit.getType1());
                unitHolder.colors1TextView.setBackgroundColor(UnitHelper.getTypeColor(unit.getType1(), context.getResources()));
                unitHolder.colors1TextView.setTextColor(Color.WHITE);
                unitHolder.colors2TextView.setVisibility(View.VISIBLE);
            } else {
                unitHolder.colors1TextView.setVisibility(View.INVISIBLE);
            }

            if (unit.getType2() != null) {
                unitHolder.colors2TextView.setText(unit.getType2());
                unitHolder.colors2TextView.setBackgroundColor(UnitHelper.getTypeColor(unit.getType2(), context.getResources()));
                unitHolder.colors2TextView.setTextColor(Color.WHITE);
                unitHolder.colors2TextView.setVisibility(View.VISIBLE);
            } else {
                unitHolder.colors2TextView.setVisibility(View.GONE);
            }


            unitHolder.starsTextView.setText(UnitHelper.getStarsToString(unit.getStars()));

            unitHolder.hpTextView.setText(String.valueOf(unit.getMaxHp()));
            unitHolder.atkTextView.setText(String.valueOf(unit.getMaxAtk()));
            unitHolder.rcvTextView.setText(String.valueOf(unit.getMaxAtk()));

            if (itemClickListner != null) {
                unitHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        itemClickListner.onClick(unit, view);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return 0;

        return 1;
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public interface ItemClickListner {
        void onClick(Unit unit, View v);
    }

    public static class HeaderHolder extends BaseHolder {

        public TextView idTextView;
        public TextView thumbImageView;
        public TextView nameTextView;
        public TextView colorsTextView;
        public TextView starsTextView;
        public TextView hpTextView;
        public TextView atkTextView;
        public TextView rcvTextView;


        public HeaderHolder(@NonNull View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.unit_id);
            thumbImageView = itemView.findViewById(R.id.unit_thumb);
            nameTextView = itemView.findViewById(R.id.unit_name);
            colorsTextView = itemView.findViewById(R.id.unit_colors);
            starsTextView = itemView.findViewById(R.id.unit_stars);
            hpTextView = itemView.findViewById(R.id.unit_hp);
            atkTextView = itemView.findViewById(R.id.unit_atk);
            rcvTextView = itemView.findViewById(R.id.unit_rcv);

        }
    }

    public static class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
        }


    }

    public static class UnitHolder extends BaseHolder {

        public TextView idTextView;
        public ImageView thumbImageView;
        public TextView nameTextView;
        public TextView colors1TextView;
        public TextView colors2TextView;
        public TextView starsTextView;
        public TextView hpTextView;
        public TextView atkTextView;
        public TextView rcvTextView;


        public UnitHolder(@NonNull View itemView) {
            super(itemView);

            idTextView = itemView.findViewById(R.id.unit_id);
            thumbImageView = itemView.findViewById(R.id.unit_thumb);
            nameTextView = itemView.findViewById(R.id.unit_name);
            colors1TextView = itemView.findViewById(R.id.unit_color1);
            colors2TextView = itemView.findViewById(R.id.unit_color2);
            starsTextView = itemView.findViewById(R.id.unit_stars);
            hpTextView = itemView.findViewById(R.id.unit_hp);
            atkTextView = itemView.findViewById(R.id.unit_atk);
            rcvTextView = itemView.findViewById(R.id.unit_rcv);

        }

    }

}
