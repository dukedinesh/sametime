package com.aps.user.sametime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 3/11/2017.
 */

public class Country {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("country_name")
    @Expose
    private String countryName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
