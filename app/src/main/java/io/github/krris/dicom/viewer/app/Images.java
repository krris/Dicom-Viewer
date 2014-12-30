package io.github.krris.dicom.viewer.app;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by krris on 20.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class Images {
    private int currentImage = 0;
    private List<String> paths;

//    public Images() {
//        paths = new ArrayList<>();
//        paths.add("/storage/emulated/0/Download/pacjent1/tf2d15_retro_2ch_cine - 6/IM-0006-0001-0001.dcm");
//        paths.add("/storage/emulated/0/Download/pacjent1/tf2d15_retro_2ch_cine - 6/IM-0006-0002-0001.dcm");
//        paths.add("/storage/emulated/0/Download/pacjent1/tf2d15_retro_2ch_cine - 6/IM-0006-0003-0001.dcm");
//        paths.add("/storage/emulated/0/Download/pacjent1/tf2d15_retro_2ch_cine - 6/IM-0006-0004-0001.dcm");
//    }

    public int getImagesSize() {
        return paths.size();
    }

    public String getImage(int index) {
        Log.i("Image", paths.get(index));
        return paths.get(index);
    }

    public String getNext() {
        if (currentImage >= paths.size() - 1) {
            return paths.get(currentImage);
        }
        currentImage++;
        return getCurrentImage();
    }

    public String getPrevious() {
        if (currentImage <= 0) {
            return paths.get(currentImage);
        }
        currentImage--;
        return getCurrentImage();
    }

    public String getCurrentImage() {
        return paths.get(currentImage);
    }

    public void setPathsToImages(List<String> paths) {
        this.paths = new ArrayList<>(paths);
    }
}
