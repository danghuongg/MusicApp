package com.danghuong.musicapp.ui.frament.accountmifrag;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.AccountMiFragmentBinding;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AccountMiFragment extends BaseBindingFragment<AccountMiFragmentBinding,AccountMiVM> {
    protected ActivityResultLauncher<String> requestPermissionLauncherWriteExternal =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    try {
                        Intent marketIntent = new Intent();
                        startActivity(marketIntent);
                    } catch (Exception e) {
                    }
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            });
    @Override
    public int getLayoutId() {
        return R.layout.account_mi_fragment;
    }

    @Override
    protected Class<AccountMiVM> getViewModel() {
        return AccountMiVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.ivScan.setOnClickListener(view1 -> {
            checkReadPermission();
        });
        binding.ivBack.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).navControllerMain.popBackStack(R.id.account_mi_fragment,true);
        });
    }
    public void checkReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncherWriteExternal.launch(Manifest.permission.CAMERA);
            } else {
                try {

                    Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                    startActivity(marketIntent);
                } catch (Exception e) {
                }
            }
        } else {
             try {

                 Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                 startActivity(marketIntent);
            } catch (Exception e) {
            }
        }
    }

}
