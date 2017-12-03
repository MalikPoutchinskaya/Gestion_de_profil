package com.bouras.malik.gestion_de_profil.helpers;

/**
 * Classe de vérification de champs.
 */

public class FieldVerificationsHelper {
    private static FieldVerificationsHelper mInstance = null;

    //TODO: passer par Dagger 2
    public static FieldVerificationsHelper getInstance(){
        if(mInstance == null)
        {
            mInstance = new FieldVerificationsHelper();
        }
        return mInstance;
    }

    public boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
