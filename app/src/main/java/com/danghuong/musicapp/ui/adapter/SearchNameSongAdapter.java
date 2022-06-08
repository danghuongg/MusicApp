package com.danghuong.musicapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.data.model.SearchNameSongItem;
import com.danghuong.musicapp.databinding.SearchArtistItemBinding;
import com.danghuong.musicapp.databinding.SearchNameSongItemBinding;
import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.List;

public class SearchNameSongAdapter extends RecyclerView.Adapter<SearchNameSongAdapter.SearchNameSongViewHoder> {
    private List<MusicListItem> searchNameSongItems = new LinkedList<>();
    private iSearchNameSong iSearchNameSong;

    public List<MusicListItem> getSearchNameSongItems() {
        return searchNameSongItems;
    }

    public void setSearchNameSongItems(List<MusicListItem> searchNameSongItems) {
        this.searchNameSongItems = searchNameSongItems;
        notifyDataSetChanged();
    }

    public void setiSearchNameSong(SearchNameSongAdapter.iSearchNameSong iSearchNameSong) {
        this.iSearchNameSong = iSearchNameSong;
    }

    @NonNull
    @NotNull
    @Override
    public SearchNameSongAdapter.SearchNameSongViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SearchNameSongItemBinding binding = SearchNameSongItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchNameSongAdapter.SearchNameSongViewHoder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchNameSongAdapter.SearchNameSongViewHoder holder, int position) {
        Glide.with(holder.itemView.getContext()).asBitmap().
                load(searchNameSongItems.get(holder.getBindingAdapterPosition()).getSongImage()).into(holder.binding.ivSongImage);
        holder.binding.tvSongName.setText(searchNameSongItems.get(holder.getBindingAdapterPosition()).getMusic_name());
        holder.binding.tvSingerName.setText(searchNameSongItems.get(holder.getBindingAdapterPosition()).getSingerName());
        Glide.with(holder.itemView.getContext()).asBitmap().
                load(searchNameSongItems.get(holder.getBindingAdapterPosition()).getIconSong())
                .into(holder.binding.ivIconSong);
        Glide.with(holder.itemView.getContext()).asBitmap().
                load(searchNameSongItems.get(holder.getBindingAdapterPosition()).getIconMore())
                .into(holder.binding.ivMore);
        holder.binding.tvSongName.setOnClickListener(v ->
                iSearchNameSong.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.tvSongName));
        holder.binding.tvSingerName.setOnClickListener(v ->
                iSearchNameSong.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.tvSingerName));
        holder.binding.ivIconSong.setOnClickListener(v ->
                iSearchNameSong.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.ivIconSong));
        holder.binding.ivSongImage.setOnClickListener(v ->
                iSearchNameSong.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.ivSongImage));
        holder.binding.ivMore.setOnClickListener(v ->
                iSearchNameSong.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.ivMore));
    }

    @Override
    public int getItemCount() {
        return searchNameSongItems.size();
    }
    public interface iSearchNameSong {
        void onClickView(int position, View view);
    }
    public static class SearchNameSongViewHoder extends RecyclerView.ViewHolder {
        SearchNameSongItemBinding binding;
        public SearchNameSongViewHoder(SearchNameSongItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
