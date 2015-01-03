package io.github.krris.dicom.viewer.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.imebra.dicom.*;

import java.util.ArrayList;
import java.util.Random;

public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    public static final String ARG_PATIENT_NAME= "patient_name";
    public static final String ARG_MEDICAL_TEST_NAME= "medical_test_name";

    private MedicalTest medicalTest;

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    private String mPatientName;
    private String mMedicalTestName;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber, String patientName, String medicalTestName) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        args.putString(ARG_PATIENT_NAME, patientName);
        args.putString(ARG_MEDICAL_TEST_NAME, medicalTestName);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mMedicalTestName = getArguments().getString(ARG_MEDICAL_TEST_NAME);
        mPatientName = getArguments().getString(ARG_PATIENT_NAME);
        Log.i("mPageNumber: ", "" + mPageNumber);
        Log.i("mPatientName: ", "" + mPatientName);
        Log.i("mMedicalTestName: ", "" + mMedicalTestName);
        this.medicalTest = Patients.getInstance().getPatient(mPatientName).getMedicalTest(mMedicalTestName);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        // Set the title view to show the page number.
        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                getString(R.string.title_template_step, mPageNumber + 1));

        final Button button = (Button) rootView.findViewById(R.id.play_animation);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getActivity(), Animation.class);
                intent.putExtra(ARG_MEDICAL_TEST_NAME, mMedicalTestName);
                intent.putExtra(ARG_PATIENT_NAME, mPatientName);
                startActivity(intent);
            }
        });

        addDicomImage(rootView);

        return rootView;
    }

    private void addDicomImage(ViewGroup viewGroup) {
        Stream stream = new Stream();
//        stream.openFileRead("/storage/emulated/0/Download/dicom1.dcm");

        stream.openFileRead(medicalTest.getImages().getImage(mPageNumber));
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

        DicomView imageView = (DicomView) viewGroup.findViewById(R.id.imageView);
        imageView.setImage(image, transformsChain);
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
