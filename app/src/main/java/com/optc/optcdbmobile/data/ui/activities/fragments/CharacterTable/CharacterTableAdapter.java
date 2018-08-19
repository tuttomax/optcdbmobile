package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.content.Context;
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
import com.optc.optcdbmobile.data.ui.activities.UnitColor;

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

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (getItemViewType(i) == 0) {
            return new HeaderHolder(inflater.inflate(R.layout.character_table_header_layout, viewGroup, false));
        }
        if (card_view_on)
            return new UnitHolder(inflater.inflate(R.layout.character_table_unit_layout_card, viewGroup, false));

        return new UnitHolder(inflater.inflate(R.layout.character_table_unit_layout, viewGroup, false));
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
            Unit unit = list.get(unitHolder.getAdapterPosition());

            unitHolder.idTextView.setText(String.valueOf(unit.getId()));

            String url = API.getThumb(unit.getId());

            Glide.with(context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())

                    .apply(new RequestOptions()
                            .override(THUMB_WIDTH, THUMB_HEIGHT)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .fitCenter()
                    ).into(((UnitHolder) holder).thumbImageView);

            unitHolder.nameTextView.setText(unit.getName());

            unitHolder.colorsTextView.setText(UnitColor.getFormattedString(unit.getType1(), unit.getType2()));
            unitHolder.colorsTextView.setBackgroundColor(UnitColor.getColorId(unit.getType1()));

            String stars = null;
            if (unit.getStars() == 5.5f) stars = "5+";
            else if (unit.getStars() == 6.5f) stars = "6+";
            else stars = String.valueOf(unit.getStars().intValue());
            unitHolder.starsTextView.setText(stars);

            unitHolder.hpTextView.setText(String.valueOf(unit.getMaxHp()));
            unitHolder.atkTextView.setText(String.valueOf(unit.getMaxAtk()));
            unitHolder.rcvTextView.setText(String.valueOf(unit.getMaxAtk()));
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

    public static class BaseHolder extends RecyclerView.ViewHolder {

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
        }
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

    public static class UnitHolder extends BaseHolder {

        public TextView idTextView;
        public ImageView thumbImageView;
        public TextView nameTextView;
        public TextView colorsTextView;
        public TextView starsTextView;
        public TextView hpTextView;
        public TextView atkTextView;
        public TextView rcvTextView;


        public UnitHolder(@NonNull View itemView) {
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
}
