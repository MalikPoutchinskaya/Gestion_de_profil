package com.bouras.malik.gestion_de_profil.view.ui.fragments;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bouras.malik.gestion_de_profil.R;
import com.bouras.malik.gestion_de_profil.databinding.FragmentProfileEditBinding;
import com.bouras.malik.gestion_de_profil.helpers.PermissionHelper;
import com.bouras.malik.gestion_de_profil.helpers.PictureHelper;
import com.bouras.malik.gestion_de_profil.helpers.PrefManager;
import com.bouras.malik.gestion_de_profil.helpers.SelectedPictureEvent;
import com.bouras.malik.gestion_de_profil.model.User;
import com.bouras.malik.gestion_de_profil.viewmodel.EditProfileViewModel;

/**
 *
 */
public class EditProfileFragment extends Fragment implements SelectedPictureEvent {

    private PictureHelper pictureHelper;

    private EditProfileViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // binding
        FragmentProfileEditBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_profile_edit, container, false);
        View view = binding.getRoot();
        view.setVisibility(View.INVISIBLE);

        view.findViewById(R.id.fragment_profile_edit_image_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictureHelper = new PictureHelper(EditProfileFragment.this, EditProfileFragment.this);
                pictureHelper.selectImage();
            }
        });

        viewModel =
                ViewModelProviders.of(this).get(EditProfileViewModel.class);


        observeViewModel(viewModel);
        binding.setEditProfileVM(viewModel);

        PrefManager prefManager = new PrefManager(getContext());
        if (prefManager.isFirstTimeLaunch()){
            viewModel.setUser(new User());
        }

        return view;
    }

    private void observeViewModel(EditProfileViewModel viewModel) {
        viewModel.getUserObservable().observe(this, user -> {
            if (user != null) {
                viewModel.setUser(user);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionHelper.REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pictureHelper.selectImage();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PictureHelper.SELECT_FILE)
                pictureHelper.onSelectFromGalleryResult(data);
            else if (requestCode == PictureHelper.REQUEST_CAMERA)
                pictureHelper.onCaptureImageResult();
        }
    }


    @Override
    public void onPictureSelected(String path) {
        viewModel.setPictureUrl(path);
    }


}
