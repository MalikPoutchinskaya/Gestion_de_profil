package com.bouras.malik.gestion_de_profil.model.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bouras.malik.gestion_de_profil.model.User;


/**
 * Base de donnee de challenge
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase INSTANCE;

    public static AppDataBase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "challenge_db")
                            .build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();

}