package com.danghuong.musicapp.ui.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.danghuong.musicapp.data.model.CurrentSongItem;
import com.danghuong.musicapp.databinding.RecentFragmentItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.RecentViewHolder> {
    private List<CurrentSongItem> currentSongItemList = new LinkedList<>();
    private ICurrentSong iCurrentSong;

    public List<CurrentSongItem> getCurrentSongItemList() {
        return currentSongItemList;
    }

    public void setCurrentSongItemList(List<CurrentSongItem> currentSongItemList) {
        this.currentSongItemList = currentSongItemList;
        notifyDataSetChanged();
    }

    public ICurrentSong getiCurrentSong() {
        return iCurrentSong;
    }

    public void setiCurrentSong(ICurrentSong iCurrentSong) {
        this.iCurrentSong = iCurrentSong;
    }

    @NonNull
    @NotNull
    @Override
    public RecentAdapter.RecentViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecentFragmentItemBinding binding = RecentFragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecentAdapter.RecentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecentAdapter.RecentViewHolder holder, int position) {
        final CurrentSongItem current = currentSongItemList.get(position);
        Glide.with(holder.binding.ivSongImage).load(currentSongItemList.get(holder.getBindingAdapterPosition()).getImage()).into(holder.binding.ivSongImage);
        Glide.with(holder.binding.ivIconSong).load(currentSongItemList.get(holder.getBindingAdapterPosition()).getIconSong()).into(holder.binding.ivIconSong);
        Glide.with(holder.binding.ivMore).load(currentSongItemList.get(holder.getBindingAdapterPosition()).getIcon()).into(holder.binding.ivMore);
        holder.binding.tvSongName.setText(currentSongItemList.get(holder.getBindingAdapterPosition()).getMusic_name());
        holder.binding.tvSingerName.setText(currentSongItemList.get(holder.getBindingAdapterPosition()).getSinger());

        holder.binding.tvSongName.setOnClickListener(v -> iCurrentSong.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.tvSongName));
        holder.binding.tvSingerName.setOnClickListener(v -> iCurrentSong.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.tvSingerName));
        holder.binding.ivIconSong.setOnClickListener(v -> iCurrentSong.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.ivIconSong));
        holder.binding.ivSongImage.setOnClickListener(v -> iCurrentSong.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.ivSongImage));
        holder.binding.ivMore.setOnClickListener(v -> iCurrentSong.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.ivMore));
    }

    @Override
    public int getItemCount() {
        return currentSongItemList.size();
    }

    public interface ICurrentSong {
        void onClickFavorite(int position, View view);

    }

    public static class RecentViewHolder extends RecyclerView.ViewHolder {
        RecentFragmentItemBinding binding;

        public RecentViewHolder(RecentFragmentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
