package com.bouras.malik.gestion_de_profil.view.ui.activities;

import android.animation.Animator;
import android.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;

import com.bouras.malik.gestion_de_profil.R;
import com.bouras.malik.gestion_de_profil.databinding.ActivityProfileBinding;
import com.bouras.malik.gestion_de_profil.helpers.PermissionHelper;
import com.bouras.malik.gestion_de_profil.helpers.PictureHelper;
import com.bouras.malik.gestion_de_profil.model.User;
import com.bouras.malik.gestion_de_profil.view.ui.fragments.EditProfileFragment;
import com.bouras.malik.gestion_de_profil.viewmodel.ProfileViewModel;

import io.codetail.animation.ViewAnimationUtils;

public class ProfileActivity extends AppCompatActivity {
    /**
     * animation d'ouverture / fermeture de l'edition de profil
     */
    private static int ANIMATION_DURANTION = 500;
    /**
     * le binding
     */
    private ActivityProfileBinding binding;
    /**
     * le fragment d'edition
     */
    private View fragmentEditView;
    /**
     * fab x
     */
    private int cx;
    /**
     * fab y
     */
    private int cy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);

        fragmentEditView = findViewById(R.id.fragment_profile_edit);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEdit(view);

            }
        });


        ProfileViewModel viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);


        observeViewModel(viewModel);
        binding.setProfileVM(viewModel);
    }

    /**
     * listener sur l'item courant
     *
     * @param viewModel le vue model
     */
    private void observeViewModel(ProfileViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getUserObservable().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    viewModel.setUser(user);
                    //si le model change on ferme l'edition
                    closeEdit(fragmentEditView);
                }
            }
        });
    }

    /**
     * ferme la vue d'edition
     *
     * @param view le fab bouton
     */
    public void closeEdit(View view) {
        closeKeyboard();
        int finalRadius = Math.max(fragmentEditView.getWidth(), fragmentEditView.getHeight());
        binding.fab.setVisibility(View.VISIBLE);
        Animator animator =
                ViewAnimationUtils.createCircularReveal(fragmentEditView, cx, cy, finalRadius, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(ANIMATION_DURANTION);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fragmentEditView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    /**
     * ouvre la vue d'edition
     *
     * @param view le fab bouton
     */
    private void openEdit(View view) {
        view.setVisibility(View.INVISIBLE);
        fragmentEditView.setVisibility(View.VISIBLE);
        computeDimFab(view);
        int dx = Math.max(cx, fragmentEditView.getWidth() - cx);
        int dy = Math.max(cy, fragmentEditView.getHeight() - cy);
        float finalRadius = (float) Math.hypot(dx, dy);

        Animator animator =
                ViewAnimationUtils.createCircularReveal(fragmentEditView, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(ANIMATION_DURANTION);
        animator.start();
    }

    /**
     * calcul la dimension du fab pour l'animation
     *
     * @param view le bouton
     */
    private void computeDimFab(View view) {
        cx = (view.getLeft() + view.getRight()) / 2;
        cy = (view.getTop() + view.getBottom()) / 2;
    }

    /**
     * ferme le clavier
     */
    private void closeKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
