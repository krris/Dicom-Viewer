package io.github.krris.dicom.viewer.app;

import java.util.Arrays;
import java.util.List;

/**
 * Created by krris on 28.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class MedicalTest {
    private String name = "DEFAULT NAME";
    private Images images = new Images();

    public MedicalTest(String testName) {
        this.name = testName;
    }

    public void setPathsToImages(List<String> paths) {
        this.images.setPathsToImages(paths);
    }

    public void setPathsToImages(String[] paths) {
        this.images.setPathsToImages(Arrays.asList(paths));
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
}
