package io.github.krris.dicom.viewer.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.imebra.dicom.*;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by krris on 02.01.15.
 * Copyright (c) 2015 krris. All rights reserved.
 */
public class Animation extends Activity {
    private DicomView dicomView;
    private Timer timer;
    private int index;
    private MyHandler handler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_activity);

        handler = new MyHandler();
        dicomView = (DicomView) findViewById(R.id.dicomView);

        index=0;
        timer= new Timer();
        timer.schedule(new TickClass(), 500, 200);
    }

    private class TickClass extends TimerTask {
        @Override
        public void run() {
            handler.sendEmptyMessage(index);
            index++;
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//                _imagView.setImageBitmap(bmp);
            Log.i("Loaing Image: ", index + "");
            addDicomImage();
        }
    }

    private void addDicomImage() {
        Stream stream = new Stream();
        stream.openFileRead("/storage/emulated/0/Download/dicom1.dcm");
//        stream.openFileRead(medicalTest.getImages().getImage(mPageNumber));
        // Build an internal representation of the Dicom file. Tags larger than 256 bytes
        //  will be loaded on demand from the file
        DataSet dataSet = CodecFactory.load(new StreamReader(stream), 256);
        // Get the first image
        Image image = dataSet.getImage(0);
        // Monochrome images may have a modality transform
        if(ColorTransformsFactory.isMonochrome(image.getColorSpace())) {
            ModalityVOILUT modalityVOILUT = new ModalityVOILUT(dataSet);
            if(!modalityVOILUT.isEmpty()) {
                Image modalityImage = modalityVOILUT.allocateOutputImage(image, image.getSizeX(), image.getSizeY());
                modalityVOILUT.runTransform(image, 0, 0, image.getSizeX(), image.getSizeY(), modalityImage, 0, 0);
                image = modalityImage;
            }
        }

        TransformsChain transformsChain = new TransformsChain();
        // Monochromatic image may require a presentation transform to display interesting data
        if(ColorTransformsFactory.isMonochrome(image.getColorSpace())) {
            VOILUT voilut = new VOILUT(dataSet);
            int voilutId = voilut.getVOILUTId(0);
            if(voilutId != 0) {
                voilut.setVOILUT(voilutId);
            } else {
                // No presentation transform is present: here we calculate the optimal window/width (brightness,
                //  contrast) and we will use that
                voilut.applyOptimalVOI(image, 0, 0, image.getSizeX(), image.getSizeY());
            }
            transformsChain.addTransform(voilut);
        }

//        DicomView imageView = (DicomView) viewGroup.findViewById(R.id.imageView);
        dicomView.setImage(image, transformsChain);
    }
}
