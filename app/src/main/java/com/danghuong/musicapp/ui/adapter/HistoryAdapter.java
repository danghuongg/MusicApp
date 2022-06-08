package com.danghuong.musicapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danghuong.musicapp.data.model.SearchedHistoryItem;
import com.danghuong.musicapp.databinding.SearchHistoryItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<SearchedHistoryItem> searchedHistoryList = new LinkedList<>();
    private iHistory iHistory;

    public List<SearchedHistoryItem> getSearchedHistoryList() {
        return searchedHistoryList;
    }

    public void setSearchedHistoryList(List<SearchedHistoryItem> searchedHistoryList) {
        this.searchedHistoryList = searchedHistoryList;
        notifyDataSetChanged();
    }

    public HistoryAdapter.iHistory getiHistory() {
        return iHistory;
    }

    public void setiHistory(HistoryAdapter.iHistory iHistory) {
        this.iHistory = iHistory;
    }

    @NonNull
    @NotNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SearchHistoryItemBinding binding = SearchHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HistoryAdapter.HistoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HistoryAdapter.HistoryViewHolder holder, int position) {
        holder.binding.tvHistoryName.setText(searchedHistoryList.get(holder.getBindingAdapterPosition()).getName());
        holder.itemView.setOnClickListener(view -> iHistory.setClickOn(holder.getBindingAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return searchedHistoryList.size();
    }

    public interface iHistory {
        void setClickOn(int position);
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        SearchHistoryItemBinding binding;

        public HistoryViewHolder(SearchHistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

