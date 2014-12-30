package io.github.krris.dicom.viewer.app;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by krris on 28.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class MedicalTestListActivity extends ListActivity {

    private Patient patient;

    /**
     * This class describes an individual sample (the sample title, and the activity class that
     * demonstrates this sample).
     */
    private class Sample {
        private CharSequence title;
        private Class<? extends Activity> activityClass;

        public Sample(String title, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.title = title;
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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String patientName = extras.getString("patient_name");
            Toast.makeText(this, patientName, Toast.LENGTH_SHORT).show();
            patient = Patients.getInstance().getPatient(patientName);
        }

        setContentView(R.layout.medical_test_list);

        // Instantiate the list of samples.

        ArrayList<Sample> samples = new ArrayList<>();
        for (MedicalTest test : patient.getAllMedicalTests()) {
            samples.add(new Sample(test.getName(), ScreenSlideActivity.class));
        }

        mSamples = samples.toArray(new Sample[samples.size()]);

//        mSamples = new Sample[]{
//                new Sample(R.string.title_crossfade, CrossfadeActivity.class),
//                new Sample(R.string.title_card_flip, CardFlipActivity.class),
//                new Sample(R.string.title_screen_slide, ScreenSlideActivity.class)
//                new Sample(R.string.title_zoom, ZoomActivity.class),
//                new Sample(R.string.title_layout_changes, LayoutChangesActivity.class),
//        };

        setListAdapter(new ArrayAdapter<Sample>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mSamples));
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        // Launch the sample associated with this list position.
        Intent intent = new Intent(MedicalTestListActivity.this, mSamples[position].activityClass);
        ArrayList<MedicalTest> medicalTests = (ArrayList<MedicalTest>) Patients.getInstance().getPatient(patient.getName()).getAllMedicalTests();
        intent.putExtra("medical_test", medicalTests.get(position).getName());
        intent.putExtra("patient_name", patient.getName());
        startActivity(intent);
    }
}
