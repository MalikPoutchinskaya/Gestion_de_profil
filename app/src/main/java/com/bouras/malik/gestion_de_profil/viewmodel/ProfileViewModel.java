package com.bouras.malik.gestion_de_profil.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.bouras.malik.gestion_de_profil.model.User;
import com.bouras.malik.gestion_de_profil.model.dao.AppDataBase;
import com.bouras.malik.gestion_de_profil.repository.UserRepository;


/**
 * VM des ecrans Profile
 */

public class ProfileViewModel extends AndroidViewModel {
    private final LiveData<User> userObservable;
    public ObservableField<User> user = new ObservableField<>();


    public ProfileViewModel(@NonNull Application application) {
        super(application);

        UserRepository userRepository = UserRepository.getInstance(
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
}
