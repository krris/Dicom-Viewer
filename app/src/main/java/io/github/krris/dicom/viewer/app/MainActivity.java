package io.github.krris.dicom.viewer.app;


import android.app.Activity;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.ipaulpro.afilechooser.utils.FileUtils;
import net.rdrei.android.dirchooser.DirectoryChooserActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * The launchpad activity for this sample project. This activity launches other activities that
 * demonstrate implementations of common animations.
 */
public class MainActivity extends ListActivity {
    private static final int REQUEST_CODE = 6384; // onActivityResult request
    private static final String TAG = "FileChooserExampleActivity";

    /**
     * This class describes an individual sample (the sample title, and the activity class that
     * demonstrates this sample).
     */
    private class Sample {
        private CharSequence title;
        private Class<? extends Activity> activityClass;

        public Sample(String string, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.title = string;
        }

        @Override
        public String toString() {
            return title.toString();
        }
    }

    /**
     * The collection of all samples in the app. This gets instantiated in {@link
     * #onCreate(android.os.Bundle)} because the {@link Sample} constructor needs access to {@link
     * android.content.res.Resources}.
     */
    private static Sample[] mSamples;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("imebra_lib");

        setContentView(R.layout.activity_main);

        // Instantiate the list of samples.

        instantiateList();

        final Button button = (Button) findViewById(R.id.choose_file_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Toast.makeText(MainActivity.this, "choose file", Toast.LENGTH_SHORT).show();
                showChooser();
            }
        });
    }

    private void instantiateList() {
        List<Patient> patients = Patients.getInstance().getAllPatients();
        List<Sample> samples = new ArrayList<>();
        for (Patient patient : patients) {
            samples.add(new Sample(patient.getName(), MedicalTestListActivity.class));
        }
        mSamples = samples.toArray(new Sample[samples.size()]);

        setListAdapter(new ArrayAdapter<Sample>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mSamples));
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        // Launch the sample associated with this list position.
        Intent intent = new Intent(MainActivity.this, mSamples[position].activityClass);
        ArrayList<Patient> patients = (ArrayList<Patient>) Patients.getInstance().getAllPatients();
        intent.putExtra("patient_name", patients.get(position).getName());
        startActivity(intent);
    }

    private void showChooser() {
        final Intent chooserIntent = new Intent(this, DirectoryChooserActivity.class);
        // Optional: Allow users to create a new directory with a fixed name.
        chooserIntent.putExtra(DirectoryChooserActivity.EXTRA_NEW_DIR_NAME, "DirChooserSample");
        startActivityForResult(chooserIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == DirectoryChooserActivity.RESULT_CODE_DIR_SELECTED) {
                final String path = data.getStringExtra(DirectoryChooserActivity.RESULT_SELECTED_DIR);
                Toast.makeText(MainActivity.this, "File Selected: " + path, Toast.LENGTH_LONG).show();
                Patients.getInstance().addPatient(path);
                instantiateList();
            } else {
                // Nothing selected
            }
        }
    }
}
