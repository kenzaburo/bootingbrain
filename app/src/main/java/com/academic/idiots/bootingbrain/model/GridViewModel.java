package com.academic.idiots.bootingbrain.model;

/**
 * Created by Thien on 3/20/2015.
 */
public class GridViewModel {
    private String gameName;
    private int resourceID;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public GridViewModel(String gameName, int resourceID) {
        this.gameName = gameName;
        this.resourceID = resourceID;
    }
}
