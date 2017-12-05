package com.bouras.malik.gestion_de_profil.helpers;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.bouras.malik.gestion_de_profil.R;

/**
 * Class utilitaire pour les permissions
 */

public class PermissionHelper {

    public static final int REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final int REQUEST_CAMER_STORAGE = 654;
    private static Fragment mFragment;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(Fragment fragment
    ) {
        mFragment = fragment;
        int currentAPIVersion = Build.VERSION.SDK_INT;
        // si supp à version android Marshmallow: on demande les permissions
        return currentAPIVersion < Build.VERSION_CODES.M || storagePermission(fragment.getContext())
                && storageCamera(fragment.getContext());
    }

    /**
     * permission pour les dossiers
     *
     * @param context le context
     * @return true si permis
     */
    private static boolean storagePermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(context.getString(R.string.permission_title));
                alertBuilder.setMessage(context.getString(R.string.permission_file));
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        mFragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            } else {
                mFragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * permission pour la camera
     *
     * @param context le context
     * @return true si permis
     */
    private static boolean storageCamera(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle(context.getString(R.string.permission_title));
                alertBuilder.setMessage(context.getString(R.string.permission_camera));
                alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        mFragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
            } else {
                mFragment.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMER_STORAGE);
            }
            return false;
        } else {
            return true;
        }
    }
}