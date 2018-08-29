package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Unit;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;

import java.lang.reflect.Field;
import java.util.List;

public class DiffCharacterTableAdapter extends ListAdapter<Unit, DiffCharacterTableAdapter.UnitHolder> {

    private final static UnitDiffCallback UNIT_DIFF_CALLBACK = new UnitDiffCallback();

    private final Context mContext;
    private OnUnitItemAdapterEvents onUnitItemAdapterEvents;

    DiffCharacterTableAdapter(Context context) {
        super(UNIT_DIFF_CALLBACK);
        mContext = context;
    }

    public void setOnUnitItemAdapterEvents(OnUnitItemAdapterEvents onUnitItemAdapterEvents) {
        this.onUnitItemAdapterEvents = onUnitItemAdapterEvents;
    }


    public void setList() {
        try {
            Field mListField = ListAdapter.class.getDeclaredField("mHelper").getClass().getDeclaredField("mList");
            List<Unit> mList = (List<Unit>) mListField.get(this);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @NonNull
    @Override
    public UnitHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int holderType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new UnitHolder(inflater.inflate(R.layout.item_character_table, viewGroup, false),
                onUnitItemAdapterEvents);
    }


    @Override
    public void onBindViewHolder(@NonNull UnitHolder unitHolder, int i) {
        final Unit unit = getItem(unitHolder.getAdapterPosition());
        unitHolder.setUnit(unit);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitHolder holder, int position, @NonNull List<Object> payloads) {
        final Unit unit = getItem(holder.getAdapterPosition());
        if (payloads.size() > 0) {
            for (Object payload : payloads) {
                Integer id = (Integer) payload;
                holder.update(id, unit);
            }
        } else {
            onBindViewHolder(holder, position);
        }
    }


    public interface OnUnitItemAdapterEvents {
        void onClick(Unit unit);

        void onLoadThumb(ImageView view, int id);
    }

    static class UnitHolder extends RecyclerView.ViewHolder {

        TextView idTextView;
        ImageView thumbImageView;
        TextView nameTextView;
        TextView colors1TextView;
        TextView colors2TextView;
        TextView starsTextView;
        TextView hpTextView;
        TextView atkTextView;
        TextView rcvTextView;

        private OnUnitItemAdapterEvents mEvents;
        private Unit mUnit;

        UnitHolder(@NonNull View itemView, OnUnitItemAdapterEvents events) {
            super(itemView);

            mEvents = events;

            idTextView = itemView.findViewById(R.id.unit_id);
            thumbImageView = itemView.findViewById(R.id.unit_thumb);
            nameTextView = itemView.findViewById(R.id.unit_name);
            colors1TextView = itemView.findViewById(R.id.unit_color1);
            colors2TextView = itemView.findViewById(R.id.unit_color2);
            starsTextView = itemView.findViewById(R.id.unit_stars);
            hpTextView = itemView.findViewById(R.id.unit_hp);
            atkTextView = itemView.findViewById(R.id.unit_atk);
            rcvTextView = itemView.findViewById(R.id.unit_rcv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.onClick(mUnit);
                }
            });

        }

        private void setUnit(Unit unit) {
            mUnit = unit;

            idTextView.setText(String.valueOf(unit.getUnitId()));
            mEvents.onLoadThumb(thumbImageView, unit.getId());

            nameTextView.setText(unit.getName());

            if (unit.getType1() != null) {
                colors1TextView.setText(unit.getType1());
                colors1TextView.setBackgroundColor(UnitHelper.getTypeColor(unit.getType1(), colors1TextView.getResources()));
                colors1TextView.setTextColor(Color.WHITE);
                colors2TextView.setVisibility(View.VISIBLE);
            } else {
                colors1TextView.setVisibility(View.INVISIBLE);
            }

            if (unit.getType2() != null) {
                colors2TextView.setText(unit.getType2());
                colors2TextView.setBackgroundColor(UnitHelper.getTypeColor(unit.getType2(), colors2TextView.getResources()));
                colors2TextView.setTextColor(Color.WHITE);
                colors2TextView.setVisibility(View.VISIBLE);
            } else {
                colors2TextView.setVisibility(View.GONE);
            }


            starsTextView.setText(UnitHelper.getStarsToString(unit.getStars()));

            hpTextView.setText(String.valueOf(unit.getMaxHp()));
            atkTextView.setText(String.valueOf(unit.getMaxAtk()));
            rcvTextView.setText(String.valueOf(unit.getMaxRcv()));
        }

        public void update(int payloadId, Unit unit) {
            switch (payloadId) {
                case UnitDiffCallback.PAYLOAD_NAME:
                    nameTextView.setText(unit.getName());
                    break;
                case UnitDiffCallback.PAYLOAD_UNIT_ID:
                    idTextView.setText(unit.getUnitId());
                    mEvents.onLoadThumb(thumbImageView, unit.getId());
                    break;
                case UnitDiffCallback.PAYLOAD_STARS:
                    starsTextView.setText(UnitHelper.getStarsToString(unit.getStars()));
                    break;
                case UnitDiffCallback.PAYLOAD_TYPE1:
                    if (unit.getType1() != null) {
                        colors1TextView.setText(unit.getType1());
                        colors1TextView.setBackgroundColor(UnitHelper.getTypeColor(unit.getType1(), colors1TextView.getResources()));
                        colors1TextView.setTextColor(Color.WHITE);
                        colors2TextView.setVisibility(View.VISIBLE);
                    } else {
                        colors1TextView.setVisibility(View.INVISIBLE);
                    }
                    break;
                case UnitDiffCallback.PAYLOAD_TYPE2:
                    if (unit.getType2() != null) {
                        colors2TextView.setText(unit.getType2());
                        colors2TextView.setBackgroundColor(UnitHelper.getTypeColor(unit.getType2(), colors2TextView.getResources()));
                        colors2TextView.setTextColor(Color.WHITE);
                        colors2TextView.setVisibility(View.VISIBLE);
                    } else {
                        colors2TextView.setVisibility(View.GONE);
                    }
                    break;
                case UnitDiffCallback.PAYLOAD_ATKMAX:
                    atkTextView.setText(String.valueOf(unit.getMaxAtk()));
                    break;
                case UnitDiffCallback.PAYLOAD_HPMAX:
                    hpTextView.setText(String.valueOf(unit.getMaxHp()));
                    break;
                case UnitDiffCallback.PAYLOAD_RCVMAX:
                    rcvTextView.setText(String.valueOf(unit.getMaxRcv()));
                    break;
            }
        }

    }

    public static class UnitDiffCallback extends DiffUtil.ItemCallback<Unit> {
        final static int PAYLOAD_NAME = 0;
        final static int PAYLOAD_UNIT_ID = 1;
        final static int PAYLOAD_STARS = 3;
        final static int PAYLOAD_TYPE1 = 6;
        final static int PAYLOAD_TYPE2 = 7;
        final static int PAYLOAD_ATKMAX = 13;
        final static int PAYLOAD_HPMAX = 15;
        final static int PAYLOAD_RCVMAX = 17;

        @Override
        public boolean areItemsTheSame(@NonNull Unit u1, @NonNull Unit u2) {

            return u1.equals(u2);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Unit u1, @NonNull Unit u2) {

            int result = u1.compareTo(u2);
            return result == 1 ? Boolean.TRUE : Boolean.FALSE;
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull Unit oldItem, @NonNull Unit newItem) {


            if (oldItem.getUnitId() != newItem.getUnitId()) return PAYLOAD_UNIT_ID;

            if (oldItem.getName() != null && newItem.getName() != null && !oldItem.getName().equals(newItem.getName()))
                return PAYLOAD_NAME;
            if (oldItem.getStars() != null && newItem.getStars() != null && !oldItem.getStars().equals(newItem.getStars()))
                return PAYLOAD_STARS;
            if (oldItem.getType1() != null && newItem.getType1() != null && !oldItem.getType1().equals(newItem.getType1()))
                return PAYLOAD_TYPE1;

            if (oldItem.getType2() != null && newItem.getType2() != null && !oldItem.getType2().equals(newItem.getType2()))
                return PAYLOAD_TYPE2;
            if (oldItem.getType2() != null && newItem.getType2() == null) return PAYLOAD_TYPE2;
            if (oldItem.getType2() == null && newItem.getType2() != null) return PAYLOAD_TYPE2;

            if (oldItem.getMaxAtk() != null && newItem.getMaxAtk() != null && !oldItem.getMaxAtk().equals(newItem.getMaxAtk()))
                return PAYLOAD_ATKMAX;
            if (oldItem.getMaxHp() != null && newItem.getMaxHp() != null && !oldItem.getMaxHp().equals(newItem.getMaxHp()))
                return PAYLOAD_HPMAX;
            if (oldItem.getMaxRcv() != null && newItem.getMaxRcv() != null && !oldItem.getMaxRcv().equals(newItem.getMaxRcv()))
                return PAYLOAD_RCVMAX;


            return super.getChangePayload(oldItem, newItem);
        }
    }

}
