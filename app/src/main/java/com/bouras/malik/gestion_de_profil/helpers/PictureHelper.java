package com.bouras.malik.gestion_de_profil.helpers;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import com.bouras.malik.gestion_de_profil.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe helper pour le traitement photo.
 */

public class PictureHelper {
    public static int REQUEST_CAMERA = 0;
    public static int SELECT_FILE = 1;

    private Fragment fragment;
    private Context context;
    private SelectedPictureEvent selectedPictureEvent;

    private String mCurrentPhotoPath;


    public PictureHelper(Fragment fragment, SelectedPictureEvent selectedPictureEvent) {
        this.fragment = fragment;
        this.context = fragment.getContext();
        this.selectedPictureEvent = selectedPictureEvent;
    }


    /**
     * lancer un dialog pour choisir la galerie ou prendre une photo
     */
    public void selectImage() {
        final CharSequence[] items = {context.getString(R.string.picture_take_photo),
                context.getString(R.string.picture_library),
                context.getString(R.string.picture_cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle( context.getString(R.string.picture_title));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(context.getString(R.string.picture_take_photo))) {
                        cameraIntent();
                } else if (items[item].equals(context.getString(R.string.picture_library))) {
                        galleryIntent();
                } else if (items[item].equals(context.getString(R.string.picture_cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /**
     * lance la caméra
     */
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        "com.bouras.malik.gestion_de_profil.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                fragment.startActivityForResult(intent, REQUEST_CAMERA);

            }
        }

    }

    /**
     * lance la galerie
     */
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        fragment.startActivityForResult(Intent.createChooser(intent, context.getString(R.string.picture_file)), SELECT_FILE);
    }

    /**
     * après selection dans la galerie
     * @param data
     */
    public void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();

        //MEDIA GALLERY
        String selectedImagePath = getPath(selectedImageUri);
        selectedPictureEvent.onPictureSelected(selectedImagePath);

    }

    /**
     * Action après la capture de l'image
     */
    public void onCaptureImageResult() {
        selectedPictureEvent.onPictureSelected(mCurrentPhotoPath);
    }

    /**
     * Création d'un file pr stockage interne
     * @return le fichier image
     * @throws IOException lors de la creation du fichier
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = fragment.getContext()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    /**
     * helper to retrieve the path of an image URI
     */
    private String getPath(Uri uri) {
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor =  context.getContentResolver()
                .query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
}
