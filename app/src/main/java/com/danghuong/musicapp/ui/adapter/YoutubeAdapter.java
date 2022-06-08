package com.danghuong.musicapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.danghuong.musicapp.data.model.YoutubeItem;
import com.danghuong.musicapp.databinding.YoutubeItemBinding;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.YoutubeViewHolder> {
    private List<YoutubeItem> YoutubeItems = new LinkedList<>();
    private iYoutubeItem iYoutubeItem;

    public List<YoutubeItem> getYoutubeItems() {
        return YoutubeItems;
    }

    public void setYoutubeItems(List<YoutubeItem> youtubeItems) {
        YoutubeItems = youtubeItems;
        notifyDataSetChanged();
    }

    public YoutubeAdapter.iYoutubeItem getiYoutubeItem() {
        return iYoutubeItem;
    }

    public void setiYoutubeItem(YoutubeAdapter.iYoutubeItem iYoutubeItem) {
        this.iYoutubeItem = iYoutubeItem;
    }

    @NonNull
    @Override
    public YoutubeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        YoutubeItemBinding binding = YoutubeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new YoutubeAdapter.YoutubeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeAdapter.YoutubeViewHolder holder, int position) {
//        YoutubeItem youtubeItem = YoutubeItems.get(position);
        Picasso.get().load(YoutubeItems.get(holder.getBindingAdapterPosition()).getThumbnails()).into(holder.binding.ivBackgroundVideo);
        holder.binding.tvTitleVideo.setText(YoutubeItems.get(holder.getBindingAdapterPosition()).getTitle());
        Glide.with(holder.itemView.getContext()).asBitmap().
                load(YoutubeItems.get(holder.getBindingAdapterPosition()).getChannelImage()).into(holder.binding.ivChannelBackground);
        holder.itemView.setOnClickListener(view -> iYoutubeItem.clickVideo(holder.getAbsoluteAdapterPosition()));
    }
    @Override
    public int getItemCount() {
        return YoutubeItems.size();
    }
    public interface iYoutubeItem {
        void clickVideo(int position);
    }
    public static class YoutubeViewHolder extends RecyclerView.ViewHolder {
        private YoutubeItemBinding binding;

        public YoutubeViewHolder(YoutubeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
