package com.danghuong.musicapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.danghuong.musicapp.data.model.FavoriteItem;
import com.danghuong.musicapp.databinding.FavoriteFragmentItemBinding;
import org.jetbrains.annotations.NotNull;
import java.util.LinkedList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<FavoriteItem> favoriteList = new LinkedList<>();
    private iFavorite iFavorite;
    public List<FavoriteItem> getFavoriteList() {
        return favoriteList;
    }


    public void setFavoriteList(List<FavoriteItem> favoriteList) {
        this.favoriteList = favoriteList;
        notifyDataSetChanged();
    }
    public void setiFavorite(FavoriteAdapter.iFavorite iFavorite) {
        this.iFavorite = iFavorite;
    }
    @NonNull
    @NotNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        FavoriteFragmentItemBinding binding = FavoriteFragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FavoriteAdapter.FavoriteViewHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteAdapter.FavoriteViewHolder holder, int position) {
        final FavoriteItem favorite = getFavoriteList().get(position);
        Glide.with(holder.binding.ivSongImage).load(favoriteList.get(holder.getBindingAdapterPosition()).getImage()).into(holder.binding.ivSongImage);
        Glide.with(holder.binding.ivIconSong).load(favoriteList.get(holder.getBindingAdapterPosition()).getIconSong()).into(holder.binding.ivIconSong);
        Glide.with(holder.binding.ivMore).load(favoriteList.get(holder.getBindingAdapterPosition()).getIcon()).into(holder.binding.ivMore);
        holder.binding.tvSongName.setText(favoriteList.get(holder.getBindingAdapterPosition()).getMusic_name());
        holder.binding.tvSingerName.setText(favoriteList.get(holder.getBindingAdapterPosition()).getSinger());
        holder.binding.tvSongName.setOnClickListener(v -> iFavorite.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.tvSongName));
        holder.binding.tvSingerName.setOnClickListener(v -> iFavorite.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.tvSingerName));
        holder.binding.ivIconSong.setOnClickListener(v -> iFavorite.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.ivIconSong));
        holder.binding.ivSongImage.setOnClickListener(v -> iFavorite.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.ivSongImage));
        holder.binding.ivMore.setOnClickListener(v -> iFavorite.onClickFavorite(holder.getAbsoluteAdapterPosition(),holder.binding.ivMore));
    }
    @Override
    public int getItemCount() {
        return favoriteList.size();
    }
    public interface iFavorite {
        void onClickFavorite(int position, View view);

    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        FavoriteFragmentItemBinding binding;

        public FavoriteViewHolder(FavoriteFragmentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}


