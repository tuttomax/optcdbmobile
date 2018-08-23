package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Evolution;
import com.optc.optcdbmobile.data.optcdb.API;
import com.optc.optcdbmobile.data.ui.activities.general.UnitHelper;

import java.util.List;

public class EvolutionRecyclerViewAdapter extends RecyclerView.Adapter<EvolutionRecyclerViewAdapter.EvolutionViewHolder> {
    public static final int TO = 0;
    public static final int FROM = 1;

    private List<Evolution> evolutions;

    private Context context;
    private int type;

    public EvolutionRecyclerViewAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    public void setEvolutions(List<Evolution> evolutions) {
        this.evolutions = evolutions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EvolutionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View root = inflater.inflate(R.layout.item_evolution, viewGroup, false);
        return i == TO ? new ToVH(root) : new FromVH(root);
    }

    @Override
    public void onBindViewHolder(@NonNull EvolutionViewHolder evolutionViewHolder, int i) {
        Evolution evolution = evolutions.get(evolutionViewHolder.getAdapterPosition());

        if (evolutionViewHolder instanceof ToVH) {
            //evolves to
            setMaterials(evolution, evolutionViewHolder);
            setupGlide(API.getThumb(evolution.getEvolutionId()), evolutionViewHolder.evolutionImageView);

        } else if (evolutionViewHolder instanceof FromVH) {
            //evolves from
            setupGlide(API.getThumb(evolution.getUnitId()), evolutionViewHolder.baseImageView);
            setMaterials(evolution, evolutionViewHolder);
            setupGlide(API.getThumb(evolution.getEvolutionId()), evolutionViewHolder.evolutionImageView);

        }
    }

    private void setMaterials(Evolution evolution, EvolutionViewHolder holder) {
        if (evolution.getMaterial1() == null) {
            holder.material1.setVisibility(View.GONE);
        } else {
            setupGlide(API.getThumb(evolution.getMaterial1()), holder.material1);
        }
        if (evolution.getMaterial2() == null) {
            holder.material2.setVisibility(View.GONE);
        } else {
            setupGlide(API.getThumb(evolution.getMaterial2()), holder.material2);
        }
        if (evolution.getMaterial3() == null) {
            holder.material3.setVisibility(View.GONE);
        } else {
            setupGlide(API.getThumb(evolution.getMaterial3()), holder.material3);
        }
        if (evolution.getMaterial4() == null) {
            holder.material4.setVisibility(View.GONE);
        } else {
            setupGlide(API.getThumb(evolution.getMaterial4()), holder.material4);
        }
        if (evolution.getMaterial5() == null) {
            holder.material5.setVisibility(View.GONE);
        } else {
            setupGlide(API.getThumb(evolution.getMaterial5()), holder.material5);
        }

    }

    private void setupGlide(String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions()
                        .override(UnitHelper.THUMB_WIDTH, UnitHelper.THUMB_HEIGHT)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_nothumb)
                        .fitCenter()
                ).into(view);
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public int getItemCount() {
        return evolutions.size();
    }

    public static class EvolutionViewHolder extends RecyclerView.ViewHolder {

        public ImageView baseImageView;
        public ImageView evolutionImageView;
        public ImageView addImageView;
        public ImageView material1;
        public ImageView material2;
        public ImageView material3;
        public ImageView material4;
        public ImageView material5;

        public EvolutionViewHolder(@NonNull View itemView) {
            super(itemView);

            baseImageView = itemView.findViewById(R.id.base);
            addImageView = itemView.findViewById(R.id.add);
            evolutionImageView = itemView.findViewById(R.id.evolution);
            material1 = itemView.findViewById(R.id.mat1);
            material2 = itemView.findViewById(R.id.mat2);
            material3 = itemView.findViewById(R.id.mat3);
            material4 = itemView.findViewById(R.id.mat4);
            material5 = itemView.findViewById(R.id.mat5);

        }
    }

    public static class ToVH extends EvolutionViewHolder {

        public ToVH(@NonNull View itemView) {
            super(itemView);

            baseImageView.setVisibility(View.GONE);
            addImageView.setVisibility(View.GONE);
        }
    }

    public static class FromVH extends EvolutionViewHolder {

        public FromVH(@NonNull View itemView) {
            super(itemView);
            baseImageView.setVisibility(View.VISIBLE);
            addImageView.setVisibility(View.VISIBLE);
        }
    }
}