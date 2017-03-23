package com.aps.user.sametime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 3/11/2017.
 */

public class StateBean {

    @SerializedName("states")
    @Expose
    private List<State> states = null;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

}