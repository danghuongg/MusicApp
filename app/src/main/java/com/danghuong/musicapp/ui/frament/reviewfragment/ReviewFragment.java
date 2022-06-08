package com.danghuong.musicapp.ui.frament.reviewfragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.databinding.ReviewFragmentBinding;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReviewFragment extends BaseBindingFragment<ReviewFragmentBinding, ReviewVM> {
    protected ActivityResultLauncher<String> requestPermissionLauncherWriteExternal =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            });
    @Override
    public int getLayoutId() {
        return R.layout.review_fragment;
    }

    @Override
    protected Class<ReviewVM> getViewModel() {
        return ReviewVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.tvTime.setVisibility(View.VISIBLE);
        binding.tvRequest.setVisibility(View.VISIBLE);
        binding.ivForward.setVisibility(View.VISIBLE);
        binding.rbProblem.setChecked(true);
        initView();
    }

    private void initView() {
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        initListener();
        initAdapter();
    }

    private void initAdapter() {
    }

    private void initListener() {
        binding.rbOpinion.setOnCheckedChangeListener((compoundButton, b) -> {
            binding.tvTime.setVisibility(View.GONE);
            binding.tvRequest.setVisibility(View.GONE);
            binding.ivForward.setVisibility(View.GONE);
            binding.etFeedback.setHint(Common.HINTFOROPINION);
        });
        binding.rbProblem.setOnCheckedChangeListener((compoundButton, b) -> {
            binding.tvTime.setVisibility(View.VISIBLE);
            binding.tvRequest.setVisibility(View.VISIBLE);
            binding.ivForward.setVisibility(View.VISIBLE);
            binding.etFeedback.setHint(Common.HINTFORPROBLEM);
        });
        binding.tvTakeAPhoto.setOnClickListener(view -> {
            checkReadPermission();
        });
    }
    
    public void checkReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncherWriteExternal.launch(Manifest.permission.CAMERA);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        }
    }

}
