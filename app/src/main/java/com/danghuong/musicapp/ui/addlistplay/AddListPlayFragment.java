package com.danghuong.musicapp.ui.addlistplay;

import android.os.Bundle;
import android.view.View;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.FragmentListPlayBinding;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.danghuong.musicapp.ui.dialog.dialodaddnewplaylist.DialogAddNewPlaylistFrag;

public class AddListPlayFragment extends BaseBindingFragment<FragmentListPlayBinding,AddPlayListVM> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_list_play;
    }

    @Override
    protected Class<AddPlayListVM> getViewModel() {
        return AddPlayListVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        initListener();
    }

    private void initListener() {
//        binding.cvTvAddListPlay.setOnClickListener(view -> {
//            DialogAddNewPlaylistFrag dialogAddNewPlaylistFrag =new DialogAddNewPlaylistFrag();
//            dialogAddNewPlaylistFrag.show(getChildFragmentManager(),null);
//        });
    }
}
