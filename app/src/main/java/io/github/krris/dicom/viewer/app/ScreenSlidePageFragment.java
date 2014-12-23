package io.github.krris.dicom.viewer.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.imebra.dicom.*;

public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    private Images images = Images.getInstance();

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
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

        addDicomImage(rootView);

        return rootView;
    }

    private void addDicomImage(ViewGroup viewGroup) {
        Stream stream = new Stream();
//        stream.openFileRead("/storage/emulated/0/Download/dicom1.dcm");
        stream.openFileRead(images.getCurrentImage());
        // Build an internal representation of the Dicom file. Tags larger than 256 bytes
        //  will be loaded on demand from the file
        DataSet dataSet = CodecFactory.load(new StreamReader(stream), 256);
        // Get the first image
        Image image = dataSet.getImage(0);

        TransformsChain transformsChain = new TransformsChain();
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
