package com.bouras.malik.gestion_de_profil.helpers;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bouras.malik.gestion_de_profil.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Classe de vérification de champs.
 */
public class BindingHelper {

    @BindingAdapter({"bind:displaySrcByGender"})
    public static void displaySrcByGender(ImageView view, boolean isMasculine) {
        Drawable image = isMasculine ?
                view.getResources().getDrawable(R.drawable.ic_masculine_selected) :
                view.getResources().getDrawable(R.drawable.ic_femenine_selected);

        view.setImageDrawable(image);
    }


    @BindingAdapter({"bind:imageProfileUrl"})
    public static void loadImageFromUri(ImageView view, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_add_a_photo_black_24px)
                .error(R.drawable.ic_add_a_photo_black_24px);

        Glide.with(view.getContext())
                .setDefaultRequestOptions(requestOptions)
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(url)
                .into(view);
    }

    @BindingAdapter({"bind:imageSquareProfileUrl"})
    public static void loadImageSquareFromUri(ImageView view, String url) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_add_a_photo_black_24px)
                .centerCrop()
                .error(R.drawable.ic_add_a_photo_black_24px);

        Glide.with(view.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(url)
                .into(view);
    }
}
