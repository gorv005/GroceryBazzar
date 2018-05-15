package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AbhinavT on 01-02-2018.
 */

public class ChangePassword implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("old_password")
    @Expose
    private String old_password;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("confirm_password")
    @Expose
    private String confirm_password;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
}
