package com.optc.optcdbmobile.data.ui.fragments.CharacterTable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.optc.optcdbmobile.R;
import com.optc.optcdbmobile.data.database.filters.FilterCollector;
import com.optc.optcdbmobile.data.database.filters.FilterInfo;
import com.optc.optcdbmobile.data.database.filters.FilterMediator;
import com.optc.optcdbmobile.data.database.filters.FilterUI;

import java.util.List;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.BaseViewHolder> {

    private FilterCollector collector;
    private Context context;

    public FilterAdapter(Context context) {
        this.context = context;
    }

    public void setCollector(final FilterCollector collector) {
        this.collector = collector;
        collector.setCallback(new FilterMediator.Callback() {
            @Override
            public void OnChangedAfterInform(int index, Object payload) {
                notifyItemChanged(index, payload);
            }
        });
    }

    public void initFilters() {
        collector.init();
        notifyItemRangeInserted(0, collector.size());
    }

    public void clearFilters() {
        collector.clear();
        notifyItemRangeChanged(0, collector.size(), FilterUI.PAYLOAD_SELECTED);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        BaseViewHolder holder = null;
        if (viewType == 0) {
            holder = new HeaderFilterViewHolder(
                    LayoutInflater.from(context)
                            .inflate(R.layout.item_header_filter, viewGroup, false));
        } else if (viewType == 1) {
            holder = new FilterViewHolder(
                    LayoutInflater.from(context)
                            .inflate(R.layout.item_filter, viewGroup, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        FilterUI filterUI = collector.get(position);
        holder.bindTo(filterUI);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.size() > 0) {
            if (holder instanceof FilterViewHolder) {
                if (FilterUI.PAYLOAD_SELECTED == (int) payloads.get(0))
                    ((FilterViewHolder) holder).filterCheckbox.setChecked(false);
            }
        } else onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return collector.size();
    }

    @Override
    public int getItemViewType(int position) {
        FilterInfo info = collector.get(position).getInfo();
        Log.d(FilterAdapter.class.getSimpleName(), String.format("Item in %d is %s", position, info.getType()));
        if (info.isHeader()) return 0;
        else return 1;
    }

    public static abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bindTo(FilterUI filterUI);
    }

    public static final class FilterViewHolder extends BaseViewHolder {

        private AppCompatCheckBox filterCheckbox;
        private FilterUI filterUI;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            filterCheckbox = itemView.findViewById(R.id.filter_checkbox);
            filterCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    buttonView.jumpDrawablesToCurrentState();
                    FilterViewHolder.this.filterUI.setSelected(isChecked, false);
                }
            });
        }

        @Override
        public void bindTo(FilterUI filterUI) {
            this.filterUI = filterUI;
            filterCheckbox.setText(filterUI.getLabel());
            filterCheckbox.setChecked(filterUI.isSelected());
        }

    }

    public static final class HeaderFilterViewHolder extends BaseViewHolder {

        private AppCompatTextView textView;

        public HeaderFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.filter_header);
        }

        @Override
        public void bindTo(FilterUI filterUI) {
            textView.setText(filterUI.getLabel());
        }
    }
}
