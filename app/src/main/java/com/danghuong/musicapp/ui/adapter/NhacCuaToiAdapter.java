package com.danghuong.musicapp.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.databinding.MusicListItemBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

public class NhacCuaToiAdapter extends RecyclerView.Adapter<NhacCuaToiAdapter.MusicListViewHoder> {
    private List<MusicListItem> musicListItems = new LinkedList<>();
    private iMusicListItem iMusicListItem;

    public List<MusicListItem> getMusicListItems() {
        return musicListItems;
    }

    public void setMusicListItems(List<MusicListItem> musicListItems) {
        this.musicListItems = musicListItems;
        notifyDataSetChanged();
    }

    public NhacCuaToiAdapter.iMusicListItem getiMusicListItem() {
        return iMusicListItem;
    }

    public void setiMusicListItem(iMusicListItem iMusicListItem) {
        this.iMusicListItem = iMusicListItem;
    }

    @NonNull
    @NotNull
    @Override
    public MusicListViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        MusicListItemBinding binding = MusicListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NhacCuaToiAdapter.MusicListViewHoder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NhacCuaToiAdapter.MusicListViewHoder holder, int position) {
        Glide.with(holder.binding.ivSongImage).load(musicListItems.get(holder.getBindingAdapterPosition()).getSongImage()).into(holder.binding.ivSongImage);
        holder.binding.tvSongName.setText(musicListItems.get(holder.getBindingAdapterPosition()).getMusic_name());
        holder.binding.tvSingerName.setText(musicListItems.get(holder.getBindingAdapterPosition()).getSingerName());
        Glide.with(holder.binding.ivIconSong).asBitmap().load(musicListItems.get(holder.getBindingAdapterPosition()).getIconSong()).into(holder.binding.ivIconSong);
        Glide.with(holder.binding.ivMore).load(musicListItems.get(holder.getBindingAdapterPosition()).getIconMore()).into(holder.binding.ivMore);
        holder.binding.tvSongName.setOnClickListener(v -> iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(),holder.binding.tvSongName));
        holder.binding.tvSingerName.setOnClickListener(v -> iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(),holder.binding.tvSingerName));
        holder.binding.ivIconSong.setOnClickListener(v -> iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(),holder.binding.ivIconSong));
        holder.binding.ivSongImage.setOnClickListener(v -> iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(),holder.binding.ivSongImage));
        holder.binding.ivMore.setOnClickListener(v -> iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(),holder.binding.ivMore));
    }

    @Override
    public int getItemCount() {
        return musicListItems.size();
    }

    public interface iMusicListItem {
        void onClickView(int position, View view);
    }

    public static class MusicListViewHoder extends RecyclerView.ViewHolder {
        MusicListItemBinding binding;

        public MusicListViewHoder(MusicListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
