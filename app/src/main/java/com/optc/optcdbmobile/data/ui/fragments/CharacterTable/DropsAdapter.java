package com.optc.optcdbmobile.data.ui.fragments.CharacterTable;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.LocationInformation;
import com.optc.optcdbmobile.data.ui.general.SpannableBuilder;

import java.util.ArrayList;
import java.util.List;

public class DropsAdapter extends RecyclerView.Adapter<DropsAdapter.DropsViewHolder> {

    private List<LocationInformation> mList;
    private Context mContext;
    private OnDropsItemAdapterEvents mEvents;

    public DropsAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setEvents(OnDropsItemAdapterEvents events) {
        this.mEvents = events;
    }

    public void setList(List<LocationInformation> locationInformations) {
        mList.clear();
        mList.addAll(locationInformations);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DropsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.item_drop, viewGroup, false);
        return new DropsViewHolder(root, mEvents);
    }

    @Override
    public void onBindViewHolder(@NonNull DropsViewHolder dropsViewHolder, int i) {
        LocationInformation drops = mList.get(dropsViewHolder.getAdapterPosition());
        dropsViewHolder.bindTo(drops);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnDropsItemAdapterEvents {
        void OnClick(int locationId);

        void OnLoadThumb(ImageView thumbView, int thumbId);
    }

    public static class DropsViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbImageView;
        TextView nameTextView;
        TextView stageTextView;

        private OnDropsItemAdapterEvents mEvents;
        private LocationInformation mLocationInfo;

        DropsViewHolder(@NonNull View itemView, OnDropsItemAdapterEvents events) {

            super(itemView);
            mEvents = events;

            thumbImageView = itemView.findViewById(R.id.drop_thumb);
            nameTextView = itemView.findViewById(R.id.drop_location_name);
            stageTextView = itemView.findViewById(R.id.drop_location_stage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.OnClick(mLocationInfo.getLocation().getId());
                }
            });
        }

        void bindTo(LocationInformation information) {
            mLocationInfo = information;
            nameTextView.setText(
                    SpannableBuilder.
                            create()
                            .append(information.getLocation().getName())
                            .background(Color.WHITE)
                            .foreground(Color.BLACK)
                            .italic().getInternalBuilder(), TextView.BufferType.SPANNABLE);

            mEvents.OnLoadThumb(thumbImageView, information.getLocation().getThumb());
            stageTextView.setText(
                    SpannableBuilder.create()
                            .append(information.getLocationDrops().getStageName())
                            .bold().getInternalBuilder());
        }
    }
}
