package com.bouras.malik.gestion_de_profil.helpers;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

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
    private SelectedPictureEvent selectedPictureEvent;

    private String mCurrentPhotoPath;


    public PictureHelper(Fragment fragment, SelectedPictureEvent selectedPictureEvent) {
        this.fragment = fragment;
        this.selectedPictureEvent = selectedPictureEvent;
    }


    /**
     * lancer un dialog pour choisir la galerie ou prendre une photo
     */
    public void selectImage() {
        final CharSequence[] items = {"Prendre une photo", "Depuis la librarie",
                "Annuler"};
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setTitle("Changer la photo de profile:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = PermissionHelper.checkPermission(fragment.getContext());
                if (items[item].equals("Prendre une photo")) {
                    if (result)
                        cameraIntent();
                } else if (items[item].equals("Depuis la librarie")) {
                    if (result)
                        galleryIntent();
                } else if (items[item].equals("Annuler")) {
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
        if (intent.resolveActivity(fragment.getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(fragment.getContext(),
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
        fragment.startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    /**
     * après selection dans la galerie
     * @param data
     */
    public void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();

        //OI FILE Manager
        String filemanagerstring = selectedImageUri.getPath();

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
        Cursor cursor =  fragment.getContext().getContentResolver()
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
