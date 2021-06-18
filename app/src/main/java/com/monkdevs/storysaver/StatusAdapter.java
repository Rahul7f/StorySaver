package com.monkdevs.storysaver;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {

    ArrayList<StatusObj> statusObj;
    Context context;
    StatusAdapter(Context context,ArrayList<StatusObj> statusObj){
        this.statusObj=statusObj;
        this.context=context;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.status_adapter_layout,parent,false);

        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {

        Glide.with(context)
                .load(statusObj.get(position).status)
                .into(holder.imageView);

        holder.download.setOnClickListener(view->{
            makeCopy(statusObj.get(position).status);
        });

    }

    @Override
    public int getItemCount() {
        return statusObj.size();
    }

    public static class StatusViewHolder extends RecyclerView.ViewHolder{
        public Button download;
        public ImageView imageView;
        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
        download=itemView.findViewById(R.id.downloadButton);
        imageView=itemView.findViewById(R.id.statusImage);
        }

    }

    void makeCopy(File source) {
        checkDir();
        String suffix=".mp4";
        File dest = null;
        if (source.getParent() != null)

            if (source.getName().contains(".jpg"))
                suffix=".jpg";
            dest = new File(Environment.getExternalStorageDirectory() + "/Videos/Status " + getTime() + suffix);

        try {
            assert dest != null;
            dest.createNewFile();
            assert dest != null;
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(dest);
            byte[] buf = new byte[(int) source.length()];
            int len = 0;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            Toast.makeText(context, "File Copied in " + dest.getAbsolutePath(), Toast.LENGTH_SHORT).show();
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

    void checkDir(){
        File file=new File(Environment.getExternalStorageDirectory()+"/Videos");
        if (!file.isDirectory())
            file.mkdir();
    }
}
