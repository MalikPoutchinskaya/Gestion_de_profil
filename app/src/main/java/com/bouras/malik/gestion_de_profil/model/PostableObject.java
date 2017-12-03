package com.bouras.malik.gestion_de_profil.model;


import android.databinding.BaseObservable;

/**
 * Gère le status de reception du service remote
 */

public abstract class PostableObject extends BaseObservable {
    /**
     * true si le service remote l'a bien recupéré
     */
    private boolean isPosted = false;

    public boolean isPosted() {
        return isPosted;
    }

    public void setPosted(boolean posted) {
        isPosted = posted;
    }
}
