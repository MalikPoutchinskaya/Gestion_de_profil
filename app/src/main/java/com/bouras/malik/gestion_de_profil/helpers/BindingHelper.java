package com.bouras.malik.gestion_de_profil.helpers;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bouras.malik.gestion_de_profil.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Classe de vérification de champs.
 */

public class BindingHelper {
    private static BindingHelper mInstance = null;

    //TODO: passer par Dagger 2
    public static BindingHelper getInstance() {
        if (mInstance == null) {
            mInstance = new BindingHelper();
        }
        return mInstance;
    }

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
//                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(url)
                .into(view);
    }

    @BindingAdapter({"bind:imageProfile"})
    public static void loadImage(ImageView view, String url) {
//        Bitmap bm = null;
//        if (url != null) {
//            try {
//                bm = android.provider.MediaStore.Images.Media.getBitmap(view.getContext().getApplicationContext().
//                        getContentResolver(), Uri.fromFile(new File(url)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Glide.with(view.getContext())
////                    .load(Uri.fromFile(new File(url)))
//                    .load(new File(url))
////                .centerCrop()
////                    .placeholder(R.drawable.ic_add_a_photo_black_24px)
//                    .into(view);
//        } else {
//
//            Glide.with(view.getContext())
//                    .load(bm)
//                    .fitCenter()
////                .centerCrop()
//                    .placeholder(R.drawable.ic_add_a_photo_black_24px)
//                    .into(view);
//        }
    }
}
