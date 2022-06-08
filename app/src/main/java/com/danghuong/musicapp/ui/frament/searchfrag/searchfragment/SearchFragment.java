package com.danghuong.musicapp.ui.frament.searchfrag.searchfragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.data.model.SearchedHistoryItem;
import com.danghuong.musicapp.databinding.SearchFragmentBinding;
import com.danghuong.musicapp.ui.adapter.HistoryAdapter;
import com.danghuong.musicapp.ui.adapter.SearchAdapter;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SearchFragment extends BaseBindingFragment<SearchFragmentBinding, SearchViewModel> {
    private HistoryAdapter historyAdapter;
    private int selected=0;

    @Override
    public int getLayoutId() {
        return R.layout.search_fragment;
    }

    @Override
    protected Class<SearchViewModel> getViewModel() {
        return SearchViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        initView();
        observeData();
    }

    private void observeData() {
        viewModel.getsearchedHistoryList();
        viewModel.searchedHistoryListMLD.observe(getViewLifecycleOwner(), new Observer<List<SearchedHistoryItem>>() {
            @Override
            public void onChanged(List<SearchedHistoryItem> searchedHistoryItems) {
                if (searchedHistoryItems != null) {
                    historyAdapter.setiHistory(position -> {
                        binding.etSearch.setQuery(searchedHistoryItems.get(position).getName(), true);
                    });
                    historyAdapter.setSearchedHistoryList(searchedHistoryItems);
                }
            }
        });
    }

    private void initView() {
        focusEdt();
        initAdapterRcHistory();
        initSearchPagerAdapter();
        initListener();
    }

    private void initSearchPagerAdapter() {
        SearchAdapter searchAdapter = new SearchAdapter(getChildFragmentManager(), getViewLifecycleOwner().getLifecycle());
        binding.viewPagerSearch.setAdapter(searchAdapter);
        binding.viewPagerSearch.setSaveEnabled(false);
        binding.tablayoutSearch.setSelectedTabIndicatorColor(Color.parseColor("#C79CD3"));
        binding.tablayoutSearch.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#C79CD3"));

        new TabLayoutMediator(binding.tablayoutSearch, binding.viewPagerSearch,
                (tab, position) -> {
                    int[] imageResId = {
                            R.drawable.ic_baseline_library_music_24,
                            R.drawable.ic_baseline_audiotrack_24,
                    };
                    binding.tablayoutSearch.setTabGravity(TabLayout.GRAVITY_FILL);
                    String[] search = {"Song Name", "Artist"};
                    tab.setIcon(imageResId[position]);
                    int tabIconColor = ContextCompat.getColor(requireContext(), R.color.violence);
                    tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    tab.setText(search[position]);
                }).attach();
    }

    private void focusEdt() {
//        binding.etSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                }
//            }
//        });
//        focusonEdittext
        binding.etSearch.setQueryHint("Search The Song");
        binding.etSearch.requestFocus();
        binding.etSearch.setIconified(false);
//        binding.etSearch.setFocusable(true);
//        binding.etSearch.requestFocusFromTouch();
//        binding.etSearch.setIconifiedByDefault(true);

//        showKeyboard
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.etSearch, InputMethodManager.SHOW_IMPLICIT);

    }

    private void initAdapterRcHistory() {
        historyAdapter = new HistoryAdapter();
        binding.rcHistory.setAdapter(historyAdapter);
    }

    private void initListener() {
        binding.ivDeleteRecent.setOnClickListener(view -> {
            viewModel.removeAllHistoryList();

            //update on view
            List<SearchedHistoryItem> searchedHistoryItems = new LinkedList<>();
            //livedata
            viewModel.searchedHistoryListMLD.postValue(searchedHistoryItems);

        });
        binding.ivBack.setOnClickListener(view -> {
            ((MainActivity) requireActivity()).navControllerMain.popBackStack(R.id.search_fragment, true);
            Timber.e("tunglt back");
        });
        binding.etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // Được kích hoạt khi nhấn tìm kiếm
            @Override
            public boolean onQueryTextSubmit(String query) {
                //evenbus
                EventBus.getDefault().post(new Event(Common.SEARCHWITHNAMESONG, query));
                SearchedHistoryItem searchedHistoryItem = new SearchedHistoryItem(query);
                viewModel.insertHistoryList(searchedHistoryItem);

//                  update data for view
                List<SearchedHistoryItem> listSearchHistory = viewModel.searchedHistoryListMLD.getValue();
                if (listSearchHistory == null) listSearchHistory = new LinkedList<>();
                listSearchHistory.add(0, searchedHistoryItem);
                viewModel.searchedHistoryListMLD.postValue(listSearchHistory);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // được gọi khi dùng nhập từng ký tự vào trường
                if (newText.isEmpty()) {
                    Timber.e("tunglt textchange null : " + newText);
                    showSearch(false);
                } else {
                    Timber.e("tunglt textchange: " + newText);
                    showSearch(true);
                    mainViewModel.liveEvent.postValue(new Event(Common.SEARCHWITHNAMESONG, newText));
                }
                return false;
            }
        });
    }
    public void showSearch(boolean search) {
        binding.tablayoutSearch.setVisibility(!search ? View.GONE : View.VISIBLE);
        binding.viewPagerSearch.setVisibility(!search ? View.GONE : View.VISIBLE);
        binding.tvSearchedHistory.setVisibility(!search ? View.VISIBLE : View.GONE);
        binding.ivDeleteRecent.setVisibility(!search ? View.VISIBLE : View.GONE);
        binding.rcHistory.setVisibility(!search ? View.VISIBLE : View.GONE);
    }














//      conduct the searching activity
//        binding.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    Timber.e("tunglt onEditorAction : " + binding.etSearch.getText().toString());
//                  EventBus.getDefault().post(new Event(Common.SEARCHWITHNAMESONG, binding.etSearch.getText().toString()));
//                    mainViewModel.liveEvent.postValue(new Event(Common.SEARCHWITHNAMESONG, binding.etSearch.getText().toString()));
//
//                    SearchedHistoryItem searchedHistoryItem = new SearchedHistoryItem(binding.etSearch.getText().toString());
//                    viewModel.insertHistoryList(searchedHistoryItem);
//
//                    //  update data for view
//                    List<SearchedHistoryItem> listSearchHistory = viewModel.searchedHistoryListMLD.getValue();
//                    if (listSearchHistory == null) listSearchHistory = new LinkedList<>();
//                    listSearchHistory.add(0, searchedHistoryItem);
//                    viewModel.searchedHistoryListMLD.postValue(listSearchHistory);
//                    return true;
//                }
//                return false;
//            }
//        });
//        binding.etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                Timber.e("tunglt beforeTextChanged");
//                showSearch(true);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.toString().isEmpty()) {
//                    Timber.e("tunglt textchange null : " + charSequence);
////                    showSearch(false);
//                } else {
//                    Timber.e("tunglt textchange: " + charSequence);
////                    showSearch(true);
////                    EventBus.getDefault().post(new Event(Common.SEARCHWITHNAMESONG,charSequence.toString()));
//                    mainViewModel.liveEvent.postValue(new Event(Common.SEARCHWITHNAMESONG, charSequence.toString()));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                Timber.e("tunglt afterTextChanged : " + editable);
////                if (binding.etSearch.getText().toString().isEmpty()) {
////                    showSearch();
////                } else {
////                    searchWithKey();
////                }
//            }
//        });



}


