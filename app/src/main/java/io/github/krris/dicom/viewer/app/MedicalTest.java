package io.github.krris.dicom.viewer.app;

import java.io.File;
import java.util.ArrayList;
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

    public void setPathsToImages(File[] paths) {
        ArrayList<String> pathsToImages = new ArrayList<>();
        for (File file : paths)
            pathsToImages.add(file.getAbsolutePath());

        this.images.setPathsToImages(pathsToImages);
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
