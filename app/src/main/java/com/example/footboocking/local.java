package com.example.footboocking;

import org.json.JSONException;
import org.json.JSONObject;

public class local {

    String id_admin;
    public local (JSONObject objetoJSON) throws JSONException {
        this.id_admin = objetoJSON.getString("id");
    }

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }

}
