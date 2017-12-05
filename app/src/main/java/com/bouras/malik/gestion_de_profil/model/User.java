package com.bouras.malik.gestion_de_profil.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * POJO User
 */

@Entity
public class User extends PostableObject {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    /**
     * id de l'user connu du server
     */
    private Long server_id;
    /**
     * prenom
     */
    private String firstName;
    /**
     * nom de famille
     */
    private String lastName;
    /**
     * prenom et nom de famille
     */
    private String fullName;
    /**
     * date d'anniversaire
     */
    private String birthday;
    /**
     * true si femme
     */
    private boolean masculine;
    /**
     * photo de profile
     */
    private String pictureUrl;
    /**
     * email
     */
    private String email;
    /**
     * password
     */
    private String password;
    /**
     * description
     */
    private String description;

    public User() {
    }

    public User(Long id, Long server_id, String firstName, String lastName, String birthday, boolean masculine, String pictureUrl, String email, String password, String description) {
        this.id = id;
        this.server_id = server_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.masculine = masculine;
        this.pictureUrl = pictureUrl;
        this.email = email;
        this.password = password;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServer_id() {
        return this.server_id;
    }

    public void setServer_id(Long server_id) {
        this.server_id = server_id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public String getPictureUrl() {
        return this.pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMasculine() {
        return masculine;
    }

    public void setMasculine(boolean masculine) {
        this.masculine = masculine;
    }

    public String getFullName() {
        if (firstName == null && lastName != null) {
            return lastName;
        } else if (firstName != null && lastName == null) {
            return firstName;
        } else if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else {
            return "";
        }
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

