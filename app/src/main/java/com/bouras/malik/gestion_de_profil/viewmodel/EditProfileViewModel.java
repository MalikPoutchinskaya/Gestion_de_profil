package com.bouras.malik.gestion_de_profil.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RadioGroup;

import com.bouras.malik.gestion_de_profil.R;
import com.bouras.malik.gestion_de_profil.model.User;
import com.bouras.malik.gestion_de_profil.model.dao.AppDataBase;
import com.bouras.malik.gestion_de_profil.repository.UserRepository;


/**
 * VM des ecrans Profile
 */

public class EditProfileViewModel extends AndroidViewModel {
    /**
     * l'entité utilisateur que l'on observe
     */
    private final LiveData<User> userObservable;
    /**
     * l'entité utilisateur que l'on manipule
     */
    public ObservableField<User> user = new ObservableField<>();
    /**
     * le repo Utilisateur
     */
    private UserRepository userRepository;


    public EditProfileViewModel(@NonNull Application application) {
        super(application);
        userRepository = UserRepository.getInstance(
                AppDataBase.getDatabase(this.getApplication()));
        userObservable = userRepository.getUser();
    }

    public LiveData<User> getUserObservable() {
        return userObservable;
    }

    public ObservableField<User> getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    /**
     * Ajoute update un utilisateur
     *
     * @param view la vue : car utilisé directement dans le xml
     */
    public void updateUser(View view) {
        userRepository.updateUser(user.get());
    }

    public void setPictureUrl(String path) {
        user.get().setPictureUrl(path);
        user.notifyChange();
    }

    public void onGenderChanged(RadioGroup radioGroup, int id) {
        user.get().setMasculine(radioGroup.getCheckedRadioButtonId() == R.id.fragment_sign_up_radiobutton_male);
    }

}
