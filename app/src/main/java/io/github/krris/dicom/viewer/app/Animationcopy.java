package io.github.krris.dicom.viewer.app;

import android.app.Activity;
import android.os.Bundle;
import com.imebra.dicom.*;

/**
 * Created by krris on 02.01.15.
 * Copyright (c) 2015 krris. All rights reserved.
 */
public class Animationcopy extends Activity {
    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_activity);
        // LOAD THE LIBRARY
        System.loadLibrary("imebra_lib");
    }
    // Called when the activity starts
    public void onStart()
    {
        super.onStart();
        // Open the dicom file from sdcard
        Stream stream = new Stream();
//        stream.openFileRead("/sdcard/Download/87FDH4G2.dcm");
        stream.openFileRead("/storage/emulated/0/Download/pacjent1/tf2d15_retro_2ch_cine - 6/IM-0006-0001-0001.dcm");
        // Build an internal representation of the Dicom file. Tags larger than 256 bytes
        //  will be loaded on demand from the file
        DataSet dataSet = CodecFactory.load(new StreamReader(stream), 256);
        // Get the first image
        Image image = dataSet.getImage(0);
        // Monochrome images may have a modality transform
        if(ColorTransformsFactory.isMonochrome(image.getColorSpace()))
        {
            ModalityVOILUT modalityVOILUT = new ModalityVOILUT(dataSet);
            if(!modalityVOILUT.isEmpty())
            {
                Image modalityImage = modalityVOILUT.allocateOutputImage(image, image.getSizeX(), image.getSizeY());
                modalityVOILUT.runTransform(image, 0, 0, image.getSizeX(), image.getSizeY(), modalityImage, 0, 0);
                image = modalityImage;
            }
        }
        // Just for fun: get the color space and the patient name
        String colorSpace = image.getColorSpace();
        String patientName = dataSet.getString(0x0010, 0, 0x0010, 0);
        String dataType = dataSet.getDataType(0x0010, 0, 0x0010);
        // Allocate a transforms chain: contains all the transforms to execute before displaying
        //  an image
        TransformsChain transformsChain = new TransformsChain();
        // Monochromatic image may require a presentation transform to display interesting data
        if(ColorTransformsFactory.isMonochrome(image.getColorSpace()))
        {
            VOILUT voilut = new VOILUT(dataSet);
            int voilutId = voilut.getVOILUTId(0);
            if(voilutId != 0)
            {
                voilut.setVOILUT(voilutId);
            }
            else
            {
                // No presentation transform is present: here we calculate the optimal window/width (brightness,
                //  contrast) and we will use that
                voilut.applyOptimalVOI(image, 0, 0, image.getSizeX(), image.getSizeY());
            }
            transformsChain.addTransform(voilut);
        }
        // Let's find the DicomView and se the image
        DicomView imageView = (DicomView)findViewById(R.id.dicomView);
        imageView.setImage(image, transformsChain);
    }
}
