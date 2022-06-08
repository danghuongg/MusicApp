package com.danghuong.musicapp.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.data.model.SelectCheckItem;
import com.danghuong.musicapp.databinding.SelectFragmentItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

public class SelectCheckItemAdapter extends RecyclerView.Adapter<SelectCheckItemAdapter.SelectViewHoder> {
    private List<SelectCheckItem> selectItemList = new LinkedList<>();
    private iMusicListItem iMusicListItem;

    public List<SelectCheckItem> getSelectItemList() {
        return selectItemList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectItemList(List<SelectCheckItem> selectItemList) {
        this.selectItemList = selectItemList;
        notifyDataSetChanged();
    }

    public SelectCheckItemAdapter.iMusicListItem getiMusicListItem() {
        return iMusicListItem;
    }

    public void setiMusicListItem(SelectCheckItemAdapter.iMusicListItem iMusicListItem) {
        this.iMusicListItem = iMusicListItem;
    }

    @NonNull
    @NotNull
    @Override
    public SelectCheckItemAdapter.SelectViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SelectFragmentItemBinding binding = SelectFragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SelectCheckItemAdapter.SelectViewHoder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SelectCheckItemAdapter.SelectViewHoder holder, int position) {
        Timber.e("huongdt select 2");
        boolean check = selectItemList.get(holder.getBindingAdapterPosition()).isCheck();
        holder.binding.tvNameSong.setText(selectItemList.get(holder.getBindingAdapterPosition()).getSelectItem().getMusic_name());
        holder.binding.tvSerial.setText(selectItemList.get(holder.getBindingAdapterPosition()).getSelectItem().getSerial());
        holder.binding.tvSingername.setText(selectItemList.get(holder.getBindingAdapterPosition()).getSelectItem().getSinger());
        Glide.with(holder.itemView.getContext()).
                load(selectItemList.get(holder.getBindingAdapterPosition()).getSelectItem().getIconSong())
                .into(holder.binding.ivIcon);
        holder.binding.tvNameSong.setOnClickListener(v ->
                iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.tvNameSong));
        holder.binding.tvSingername.setOnClickListener(v ->
                iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.tvSingername));
        holder.binding.ivIcon.setOnClickListener(v ->
                iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.ivIcon));
        holder.binding.tvSerial.setOnClickListener(v ->
                iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.tvSerial));
        if (check) {
            Glide.with(holder.itemView.getContext()).
                    load(R.drawable.ic_baseline_check_circle_24)
                    .into(holder.binding.ivSelectItem);
            selectItemList.get(holder.getBindingAdapterPosition()).setCheck(true);
        } else {
            Glide.with(holder.itemView.getContext()).
                    load(R.drawable.ic_baseline_panorama_fish_eye_24)
                    .into(holder.binding.ivSelectItem);
            selectItemList.get(holder.getBindingAdapterPosition()).setCheck(false);
        }
        holder.binding.ivSelectItem.setOnClickListener(v -> {
            iMusicListItem.onClickView(holder.getAbsoluteAdapterPosition(), holder.binding.ivSelectItem);
        });
    }

    @Override
    public int getItemCount() {
        return selectItemList.size();
    }

    public interface iMusicListItem {
        void onClickView(int position, View view);
    }

    public static class SelectViewHoder extends RecyclerView.ViewHolder {
        SelectFragmentItemBinding binding;

        public SelectViewHoder(SelectFragmentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
