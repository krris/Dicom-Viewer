package io.github.krris.dicom.viewer.app;

import java.util.ArrayList;

/**
 * Created by krris on 20.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class Images {
    private int currentImage = 0;
    private static ArrayList<String> paths = new ArrayList<>();

    public Images() {
        paths.add("/storage/emulated/0/Download/pacjent1/tf2d15_retro_2ch_cine - 6/IM-0006-0001-0001.dcm");
        paths.add("/storage/emulated/0/Download/pacjent1/tf2d15_retro_2ch_cine - 6/IM-0006-0002-0001.dcm");
        paths.add("/storage/emulated/0/Download/pacjent1/tf2d15_retro_2ch_cine - 6/IM-0006-0003-0001.dcm");
    }

    public String getNext() {
        if (currentImage >= this.paths.size() - 1) {
            return this.paths.get(currentImage);
        }
        currentImage++;
        return getCurrentImage();
    }

    public String getPrevious() {
        if (currentImage <= 0) {
            return this.paths.get(currentImage);
        }
        currentImage--;
        return getCurrentImage();
    }

    public String getCurrentImage() {
        return this.paths.get(this.currentImage);
    }

    public static void main(String[] args) {
        Images images = new Images();
        System.out.println(images.getCurrentImage());
        System.out.println(images.getNext());
        System.out.println(images.getNext());
        System.out.println(images.getNext());

        System.out.println(images.getPrevious());
        System.out.println(images.getPrevious());
        System.out.println(images.getPrevious());
        System.out.println(images.getPrevious());
    }
}
