package com.bouras.malik.gestion_de_profil.view.ui.activities;

import android.animation.Animator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.bouras.malik.gestion_de_profil.R;
import com.bouras.malik.gestion_de_profil.databinding.ActivityProfileBinding;
import com.bouras.malik.gestion_de_profil.model.User;
import com.bouras.malik.gestion_de_profil.viewmodel.ProfileViewModel;

import io.codetail.animation.ViewAnimationUtils;

public class ProfileActivity extends AppCompatActivity {

    int cx;
    FloatingActionButton fab;
    int cy;
    ActivityProfileBinding binding;
    //    private ExitActivityTransition exitTransition;
    private View fragmentEditView;
    private ProfileViewModel viewModel;
    private boolean zoomOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Action bar pour retour
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //fleche de retuour
        }

        fragmentEditView = findViewById(R.id.fragment_profile_edit);


        fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.INVISIBLE);
                fragmentEditView.setVisibility(View.VISIBLE);
                cx = (view.getLeft() + view.getRight()) / 2;
                cy = (view.getTop() + view.getBottom()) / 2;
                // get the final radius for the clipping circle
                int dx = Math.max(cx, fragmentEditView.getWidth() - cx);
                int dy = Math.max(cy, fragmentEditView.getHeight() - cy);
                float finalRadius = (float) Math.hypot(dx, dy);

                // Android native animator
                Animator animator =
                        ViewAnimationUtils.createCircularReveal(fragmentEditView, cx, cy, 0, finalRadius);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(500);
                animator.start();

            }
        });


        viewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);


        observeViewModel(viewModel);
        binding.setProfileVM(viewModel);
    }

    private void observeViewModel(ProfileViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getUserObservable().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    // binding.setIsLoading(false);
                    //projectAdapter.setProjectList(projects);
                    viewModel.setUser(user);

                    if (fragmentEditView != null) {
                        close(fragmentEditView);
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
//        exitTransition.exit(this);
        this.finish();
    }

    public void close(View view) {
        // get the final radius for the clipping circle
        int finalRadius = Math.max(fragmentEditView.getWidth(), fragmentEditView.getHeight());
        // i just swapped from radius, to radius arguments
        fab.setVisibility(View.VISIBLE);
        //FIXME: verifier que la vue est bien attaché
        Animator animator =
                ViewAnimationUtils.createCircularReveal(fragmentEditView, cx, cy, finalRadius, 0);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
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

}
