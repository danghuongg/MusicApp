package com.danghuong.musicapp.ui.dialog.displaybottomsheetfrag;

import android.os.Bundle;
import android.view.View;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.databinding.BsDisplayBinding;
import com.danghuong.musicapp.ui.base.BaseBottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

import static com.danghuong.musicapp.common.Common.SORT_SONG;

@AndroidEntryPoint
public class DisplayBottomSheetFragment extends BaseBottomSheetDialogFragment<BsDisplayBinding, DisplayBottomSheetViewModel> {
    private List<MusicListItem> musicListItemList = new LinkedList<>();

    @Override
    protected Class<DisplayBottomSheetViewModel> getViewModel() {
        return DisplayBottomSheetViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bs_display;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        initData();
        initAdapter();

    }

    private void initAdapter() {
        binding.tvAccordingToSong.setOnClickListener(view -> {
//            sortSong=1;
            EventBus.getDefault().post(new Event(SORT_SONG, 1));
            dismiss();
//            Bundle bundle=new Bundle();
//            bundle.putInt(Common.SORTLIST,sortSong);
//            ((MainActivity)requireActivity()).navControllerMain.navigate(R.id.music_list,bundle);
        });
        binding.tvAccordingToArtist.setOnClickListener(view -> {
//            sortSong=2;
            EventBus.getDefault().post(new Event(SORT_SONG, 2));
            dismiss();
//            Bundle bundle=new Bundle();
//            bundle.putInt(Common.SORTLIST,sortSong);
//            ((MainActivity)requireActivity()).navControllerMain.navigate(R.id.music_list,bundle);
        });
        binding.tvAccordingToAlbum.setOnClickListener(view -> {
            EventBus.getDefault().post(new Event(SORT_SONG, 4));
            dismiss();
        });
        binding.tvAccordingToFolder.setOnClickListener(view -> {
            EventBus.getDefault().post(new Event(SORT_SONG, 3));
            dismiss();
        });
    }

    private void initData() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
