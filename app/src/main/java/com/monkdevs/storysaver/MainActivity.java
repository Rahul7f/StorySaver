package com.monkdevs.storysaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.text.style.TtsSpan;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.material.timepicker.TimeFormat;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;

public class MainActivity extends AppCompatActivity {
    VideoView videoView;
    ArrayList<StatusObj> statusObjs=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        videoView = findViewById(R.id.videoView);
        recyclerView=findViewById(R.id.statusRecyView);
        StatusAdapter adapter=new StatusAdapter(this,statusObjs);
        recyclerView.setAdapter(adapter);
//        File directory = new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/.Statuses");
        File directory = new File(Environment.getExternalStorageDirectory(), "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");

        File[] files = directory.listFiles();


        if (files != null)
        {
            Log.d("rsin","founded");
            for (File each : files) {
                if (each.isFile() && (each.getName().endsWith(".jpg") || each.getName().endsWith(".mp4"))) {
                    //add in adapter
                    Log.e("Status",""+each.getName());
                    statusObjs.add(new StatusObj(each));
                    adapter.notifyDataSetChanged();
                }

            }
        }
        else {
            Log.d("rsin","not found");
        }



    }

    void makeCopy(File source) {

        File dest = null;
        if (source.getParent() != null)
            dest = new File(source.getParent() + "/Status " + getTime() + ".mp4");

        try {
            assert dest != null;
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(dest);
            byte[] buf = new byte[(int) source.length()];
            int len = 0;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            Toast.makeText(this, "File Copied in " + dest.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String getTime() {
        long millis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mm.ss dd-MM-yyyy", Locale.getDefault());
        return dateFormat.format(new Date(millis));
    }

}