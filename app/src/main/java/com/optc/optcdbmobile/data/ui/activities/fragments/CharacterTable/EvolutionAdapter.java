package com.optc.optcdbmobile.data.ui.activities.fragments.CharacterTable;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.entities.Evolution;

import java.util.List;

public class EvolutionAdapter extends RecyclerView.Adapter<EvolutionAdapter.EvolutionViewHolder> {
    public static final int TO = 0;
    public static final int FROM = 1;

    private List<Evolution> evolutions;

    private Context context;
    private int type;
    private OnEvolutionItemAdapterEvent onEvolutionItemAdapterEvent;

    public EvolutionAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
    }

    public void setOnEvolutionItemAdapterEvent(OnEvolutionItemAdapterEvent onEvolutionItemAdapterEvent) {
        this.onEvolutionItemAdapterEvent = onEvolutionItemAdapterEvent;
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
        return i == TO ? new ToVH(root, onEvolutionItemAdapterEvent) : new FromVH(root, onEvolutionItemAdapterEvent);
    }

    @Override
    public void onBindViewHolder(@NonNull EvolutionViewHolder evolutionViewHolder, int i) {
        final Evolution evolution = evolutions.get(evolutionViewHolder.getAdapterPosition());
        evolutionViewHolder.setEvolution(evolution);
    }


    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public int getItemCount() {
        return evolutions.size();
    }

    public interface OnEvolutionItemAdapterEvent {
        void onClick(int id);

        void onLoadThumb(ImageView v, int id);
    }

    @SuppressWarnings("WeakerAccess")
    public abstract static class EvolutionViewHolder extends RecyclerView.ViewHolder {

        protected ImageView baseImageView;
        protected ImageView evolutionImageView;
        protected ImageView addImageView;
        protected ImageView material1;
        protected ImageView material2;
        protected ImageView material3;
        protected ImageView material4;
        protected ImageView material5;


        protected OnEvolutionItemAdapterEvent mEvents;
        private Evolution mEvolution;

        protected EvolutionViewHolder(@NonNull View itemView, final OnEvolutionItemAdapterEvent events) {
            super(itemView);

            mEvents = events;

            baseImageView = itemView.findViewById(R.id.base);


            addImageView = itemView.findViewById(R.id.add);
            evolutionImageView = itemView.findViewById(R.id.evolution);
            material1 = itemView.findViewById(R.id.mat1);
            material2 = itemView.findViewById(R.id.mat2);
            material3 = itemView.findViewById(R.id.mat3);
            material4 = itemView.findViewById(R.id.mat4);
            material5 = itemView.findViewById(R.id.mat5);

            setupOnClick();
        }


        private void setupOnClick() {
            baseImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.onClick(mEvolution.getUnitId());
                }
            });
            evolutionImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.onClick(mEvolution.getEvolutionId());
                }
            });
            material1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.onClick(mEvolution.getMaterial1());
                }
            });
            material2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.onClick(mEvolution.getMaterial2());
                }
            });
            material3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.onClick(mEvolution.getMaterial3());
                }
            });
            material4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.onClick(mEvolution.getMaterial4());
                }
            });
            material5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mEvents.onClick(mEvolution.getMaterial5());
                }
            });
        }

        protected void setupMaterials(Evolution evolution) {
            if (evolution.getMaterial1() == null) {
                material1.setVisibility(View.GONE);
            } else {
                setupGlide(material1, evolution.getMaterial1());
            }
            if (evolution.getMaterial2() == null) {
                material2.setVisibility(View.GONE);
            } else {
                setupGlide(material2, evolution.getMaterial2());
            }
            if (evolution.getMaterial3() == null) {
                material3.setVisibility(View.GONE);
            } else {
                setupGlide(material3, evolution.getMaterial3());
            }
            if (evolution.getMaterial4() == null) {
                material4.setVisibility(View.GONE);
            } else {
                setupGlide(material4, evolution.getMaterial4());
            }
            if (evolution.getMaterial5() == null) {
                material5.setVisibility(View.GONE);
            } else {

                setupGlide(material5, evolution.getMaterial5());
            }

        }

        protected void setupGlide(ImageView view, int id) {
            mEvents.onLoadThumb(view, id);
        }

        @CallSuper
        public void setEvolution(Evolution evolution) {
            mEvolution = evolution;
        }
    }

    public final static class ToVH extends EvolutionViewHolder {

        ToVH(@NonNull View itemView, OnEvolutionItemAdapterEvent events) {
            super(itemView, events);

            baseImageView.setVisibility(View.GONE);
            addImageView.setVisibility(View.GONE);
        }

        @Override
        public void setEvolution(Evolution evolution) {
            super.setEvolution(evolution);
            setupMaterials(evolution);
            setupGlide(evolutionImageView, evolution.getEvolutionId());
        }
    }

    public final static class FromVH extends EvolutionViewHolder {

        FromVH(@NonNull View itemView, OnEvolutionItemAdapterEvent events) {
            super(itemView, events);
            baseImageView.setVisibility(View.VISIBLE);
            addImageView.setVisibility(View.VISIBLE);
        }

        @Override
        public void setEvolution(Evolution evolution) {
            super.setEvolution(evolution);

            setupGlide(baseImageView, evolution.getUnitId());
            setupMaterials(evolution);
            setupGlide(evolutionImageView, evolution.getEvolutionId());
        }
    }
}