package com.ilyjava.android.yourphotos.model;

import java.io.Serializable;

/**
 * Created by Никита on 05.05.2018.
 */

public class Image implements Serializable {
    private String small, medium, large;
    private long timestamp;

    public Image() {
    }

    public Image(String name, String small, String medium, String large) {
        this.small = small;
        this.medium = medium;
        this.large = large;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }


}
