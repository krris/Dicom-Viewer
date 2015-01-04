package io.github.krris.dicom.viewer.app;

/**
 * Created by krris on 04.01.15.
 * Copyright (c) 2015 krris. All rights reserved.
 */
public class Series {
    private String name;
    private Images images = new Images();

    public Series(String testName) {
        this.name = testName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Images getImages() {
        return images;
    }

    public void addImage(String imagePath) {
        this.images.addImage(imagePath);
    }
}
