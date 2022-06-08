package com.danghuong.musicapp.ui.frament.selectfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.data.model.SelectCheckItem;
import com.danghuong.musicapp.databinding.SelectionFragmentRcBinding;
import com.danghuong.musicapp.ui.adapter.SelectCheckItemAdapter;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.danghuong.musicapp.ui.dialog.dialogaddplayedlist.DialogAddPlayedListFrag;
import com.danghuong.musicapp.ui.dialog.dialogdelete.DialogDeleteFrag;
import com.danghuong.musicapp.ui.dialog.dialogmore.DialogMoreFragment;
import com.danghuong.musicapp.ui.dialog.dialogplayafter.DialogPlayAfterFrag;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SelectFragment extends BaseBindingFragment<SelectionFragmentRcBinding, SelectViewModel> {
    private SelectCheckItemAdapter selectAdapter;
    List<SelectCheckItem> selectCheckItemList=new LinkedList<>();
    private boolean isChecked = false;
    private int count = 0;
    private List<Integer> positionList=new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.selection_fragment_rc;
    }

    @Override
    protected Class<SelectViewModel> getViewModel() {
        return SelectViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        initData();
        initView();
    }

    private void initData() {
        mainViewModel.selectedItemMLD.observe(getViewLifecycleOwner(), new Observer<List<SelectCheckItem>>() {
            @Override
            public void onChanged(List<SelectCheckItem> selectItems) {
                selectCheckItemList.clear();
                selectCheckItemList.addAll(selectItems);
                Timber.e("huongdt select 1");
                selectAdapter.setSelectItemList(selectItems);
            }
        });
    }

    private void initView() {
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        setClickableIconSelected(false);
        setColorIconSelected(R.color.grey);
//        setClickable();
        initAdapter();
        initListener();
    }

    private void setClickable() {
        if (count > 0) {
            setClickableIconSelected(true);
            isChecked = true;
        } else {
            setClickableIconSelected(false);
            isChecked = false;
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initListener() {
//        binding.ivPlayAfter.setOnClickListener(view -> {
//            DialogPlayAfterFrag dialogPlayAfterFrag = new DialogPlayAfterFrag();
//            dialogPlayAfterFrag.show(getChildFragmentManager(), null);
//        });
        binding.ivDelete.setOnClickListener(view -> {
            for (int i = 0; i < positionList.size(); i++) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("audio/*");
                share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri photoURI = FileProvider.getUriForFile(requireActivity(), requireActivity().getApplicationContext().getPackageName() + ".provider", new File(selectCheckItemList.get(positionList.get(i)).getSelectItem().getLink_music()));
                share.putExtra(Intent.EXTRA_STREAM, photoURI);
                startActivity(Intent.createChooser(share, "Share Sound File"));
            }

        });
//        binding.ivMore.setOnClickListener(view -> {
//            DialogMoreFragment dialogMoreFragment = new DialogMoreFragment();
//            dialogMoreFragment.show(getChildFragmentManager(), null);
//        });
        binding.ivBack.setOnClickListener(view -> {
            ((MainActivity) requireActivity()).navControllerMain.popBackStack(R.id.select_fragment, true);
        });
        binding.ivSelectAll.setOnClickListener(view -> {
            List<SelectCheckItem> selectCheckItemList = new LinkedList<>();
            selectCheckItemList.addAll(mainViewModel.selectedItemMLD.getValue());
            if (!isChecked) {
                isChecked = true;
                Glide.with(this).load(R.drawable.ic_baseline_check_circle_24).into(binding.ivSelectAll);
                if (mainViewModel.selectedItemMLD.getValue() != null) {
                    //setText
                    count = selectCheckItemList.size();
                    binding.tvSelectedFolder.setText(count + " mục được chọn");
                    //setCheck
                    for (int i = 0; i < selectCheckItemList.size(); i++) {
                        selectCheckItemList.get(i).setCheck(true);
                        positionList.add(i);
                        Log.e("huongdt select ", String.valueOf(positionList.size()));
                    }
                }
                setColorIconSelected(android.R.color.black);
                setClickableIconSelected(true);
            } else {
                Glide.with(this).load(R.drawable.ic_baseline_panorama_fish_eye_24)
                        .into(binding.ivSelectAll);
                isChecked = false;
                //setText
                count = 0;
                binding.tvSelectedFolder.setText(count + " mục được chọn");

                //setCheck
                for (int i = 0; i < selectCheckItemList.size(); i++) {
                    selectCheckItemList.get(i).setCheck(false);
                }
                positionList.clear();
                //setClickable and set Color for
                setColorIconSelected(R.color.grey);
                setClickableIconSelected(false);
            }
            selectAdapter.notifyDataSetChanged();
        });
    }

    @SuppressLint("SetTextI18n")
    private void initAdapter() {
        selectAdapter = new SelectCheckItemAdapter();
        binding.rcSelectedList.setAdapter(selectAdapter);
        selectAdapter.setiMusicListItem((position, view) -> {
            if (selectAdapter.getSelectItemList().get(position).isCheck()) {
                setView(position, false);
                // xét màu cho imageview
                setColorIconSelected(R.color.grey);
                // xét lại text cho textview
                if (count >= 0) {
                    count--;

                    //xoa phan tu co the chia se
                    for (int i = 0; i < positionList.size(); i++) {
                       if(positionList.get(i)==position){
                           positionList.remove(positionList.get(i));
                           break;
                       }
                    }

                    binding.tvSelectedFolder.setText(count + " mục được chọn");
                }
            } else {
                setView(position, true);
                // xét lại text cho textview
                count++;
                positionList.add(position);
                binding.tvSelectedFolder.setText(count + " mục được chọn");
                // xét màu cho imageview
                setColorIconSelected(android.R.color.black);
            }
            setClickable();
            selectAdapter.notifyItemChanged(position, selectAdapter.getSelectItemList().get(position));
        });
    }
    private void setView(int position, boolean a) {
//        binding.ivPlayAfter.setClickable(a);
//        binding.ivAddPlayedList.setClickable(a);
        binding.ivDelete.setClickable(a);
        selectAdapter.getSelectItemList().get(position).setCheck(a);
    }
    public void setColorIconSelected(int a) {
        binding.ivDelete.setColorFilter(getContext().getResources().getColor(a));
//        binding.ivPlayAfter.setColorFilter(getContext().getResources().getColor(a));
//        binding.ivAddPlayedList.setColorFilter(getContext().getResources().getColor(a));
    }
    public void setClickableIconSelected(boolean a) {
//        binding.ivPlayAfter.setClickable(a);
//        binding.ivAddPlayedList.setClickable(a);
        binding.ivDelete.setClickable(a);
    }


}


