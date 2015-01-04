package io.github.krris.dicom.viewer.app;

import android.app.Activity;

/**
 * Created by krris on 04.01.15.
 * Copyright (c) 2015 krris. All rights reserved.
 */
public class Sample {
    private CharSequence title;

    private Class<? extends Activity> activityClass;

    public Sample(String string, Class<? extends Activity> activityClass) {
        this.activityClass = activityClass;
        this.title = string;
    }

    @Override
    public String toString()
    {
        return title.toString();
    }

    public String getTitle() {
        return this.title.toString();
    }

    public Class<? extends Activity> getActivityClass() {
        return activityClass;
    }
}
