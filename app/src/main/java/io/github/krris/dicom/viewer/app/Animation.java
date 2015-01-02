package io.github.krris.dicom.viewer.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by krris on 02.01.15.
 * Copyright (c) 2015 krris. All rights reserved.
 */
public class Animation extends Activity {
    private ImageView imageView;
    private Timer timer;
    private int index;
    private MyHandler handler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_activity);

        handler = new MyHandler();
//        imageView = (ImageView) findViewById(R.id.imageView1);

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
        }
    }
}
