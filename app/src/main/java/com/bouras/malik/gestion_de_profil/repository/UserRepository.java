package com.bouras.malik.gestion_de_profil.repository;

import android.arch.lifecycle.LiveData;

import com.bouras.malik.gestion_de_profil.model.User;
import com.bouras.malik.gestion_de_profil.model.dao.AppDataBase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Repo pour un user
 */
public class UserRepository {
    /**
     * Instance de la class
     */
    private static UserRepository sInstance;
    /**
     * execute une tache dans un thread différent
     */
    private final Executor executor = Executors.newFixedThreadPool(2);
    /**
     * la base de donnée
     */
    private AppDataBase appDataBase;

    /**
     * constructor
     *
     * @param database la base de donnée
     */
    private UserRepository(final AppDataBase database) {
        this.appDataBase = database;
    }

    /**
     * Creer une nouvelle instance
     *
     * @param database la base de donnée
     * @return une nouvelle instance
     */
    public static UserRepository getInstance(final AppDataBase database) {
        if (sInstance == null) {
            synchronized (UserRepository.class) {
                if (sInstance == null) {
                    sInstance = new UserRepository(database);
                }
            }
        }
        return sInstance;
    }


    public LiveData<User> getUser() {
        return getUserFromLocal();
    }

    public void updateUser(User user) {
        updateUserToLocal(user);
    }


    /**
     * update un utilisateur en bdd locale et le renvoi au service
     *
     * @param user l'utilisateur
     */
    private void updateUserToLocal(User user) {
        executor.execute(() -> appDataBase.userDao().addUser(user));
    }

    /**
     * recupere un utilisateur dans la base locale
     *
     * @return un utilisateur
     */
    private LiveData<User> getUserFromLocal() {
        return appDataBase.userDao().getUser();
    }

    /**
     * supprime un utilisateur
     * @param user l'utiliasateur
     */
    public void deleteFromDataBase(User user) {
        executor.execute(() -> appDataBase.userDao().deleteUser(user));
    }
}
