package com.bouras.malik.gestion_de_profil.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bouras.malik.gestion_de_profil.model.User;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * DAO User
 */
@Dao
public interface UserDao {

    @Query("select * from User where id = :id")
    User getUserbyId(String id);

    @Query("select * from User")
    LiveData<User> getUser();

    @Insert(onConflict = REPLACE)
    void addUser(User user);

    @Delete
    void deleteUser(User user);

}
