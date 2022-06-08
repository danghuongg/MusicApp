package com.danghuong.musicapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.data.model.SearchArtistItem;
import com.danghuong.musicapp.databinding.SearchArtistItemBinding;
import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.List;

public class SearchArtistAdapter extends RecyclerView.Adapter<SearchArtistAdapter.SearchArtistViewHoder> {
    private List<MusicListItem> searchArtistItemList = new LinkedList<>();
    private iSearchArtist iSearchArtist;

    public List<MusicListItem> getSearchArtistItemList() {
        return searchArtistItemList;
    }

    public void setSearchArtistItemList(List<MusicListItem> searchArtistItemList) {
        this.searchArtistItemList = searchArtistItemList;
        notifyDataSetChanged();
    }

    public iSearchArtist getiSearchArtist() {
        return iSearchArtist;
    }

    public void setiSearchArtist(iSearchArtist iSearchArtist) {
        this.iSearchArtist = iSearchArtist;
    }

    @NonNull
    @NotNull
    @Override
    public SearchArtistAdapter.SearchArtistViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SearchArtistItemBinding binding = SearchArtistItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SearchArtistAdapter.SearchArtistViewHoder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchArtistAdapter.SearchArtistViewHoder holder, int position) {
        Glide.with(holder.itemView.getContext()).asBitmap().
                load(searchArtistItemList.get(holder.getBindingAdapterPosition()).getSongImage()).into(holder.binding.ivSongImage);
        holder.binding.tvSongName.setText(searchArtistItemList.get(holder.getBindingAdapterPosition()).getMusic_name());
        holder.binding.tvSingerName.setText(searchArtistItemList.get(holder.getBindingAdapterPosition()).getSingerName());
        Glide.with(holder.itemView.getContext()).asBitmap().
                load(searchArtistItemList.get(holder.getBindingAdapterPosition()).getIconSong())
                .into(holder.binding.ivIconSong);
        Glide.with(holder.itemView.getContext()).asBitmap().
                load(searchArtistItemList.get(holder.getBindingAdapterPosition()).getIconMore())
                .into(holder.binding.ivMore);
        holder.binding.tvSongName.setOnClickListener(v ->
                iSearchArtist.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.tvSongName));
        holder.binding.tvSingerName.setOnClickListener(v ->
                iSearchArtist.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.tvSingerName));
        holder.binding.ivIconSong.setOnClickListener(v ->
                iSearchArtist.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.ivIconSong));
        holder.binding.ivSongImage.setOnClickListener(v ->
                iSearchArtist.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.ivSongImage));
        holder.binding.ivMore.setOnClickListener(v ->
                iSearchArtist.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.ivMore));
    }

    @Override
    public int getItemCount() {
        return searchArtistItemList.size();
    }

    public interface iSearchArtist {
        void onClickView(int position, View view);
    }

    public static class SearchArtistViewHoder extends RecyclerView.ViewHolder {
        SearchArtistItemBinding binding;
        public SearchArtistViewHoder(SearchArtistItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
