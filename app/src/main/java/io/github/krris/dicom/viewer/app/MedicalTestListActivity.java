package io.github.krris.dicom.viewer.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by krris on 28.12.14.
 * Copyright (c) 2014 krris. All rights reserved.
 */
public class MedicalTestListActivity extends ListActivity {

    private Patient patient;

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
            patient = Patients.getInstance().getPatient(patientName);
        }

        setContentView(R.layout.medical_test_list);

        // Instantiate the list of samples.

        ArrayList<Sample> samples = new ArrayList<>();
        for (MedicalTest test : patient.getMedicalTests().values()) {
            samples.add(new Sample(test.getName(), SeriesListActivity.class));
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
        Intent intent = new Intent(MedicalTestListActivity.this, mSamples[position].getActivityClass());
        intent.putExtra("medical_test", mSamples[position].getTitle());
        intent.putExtra("patient_name", patient.getName());
        startActivity(intent);
    }
}
