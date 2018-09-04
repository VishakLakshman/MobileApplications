package com.example.mypc.courseapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class InstructorSmallAdapter extends Adapter<InstructorSmallAdapter.ViewHolder> {


    ArrayList<Instructor> mdata ;


    public InstructorSmallAdapter(ArrayList<Instructor> mdata) {
        this.mdata = mdata;
        Log.d("mdata size",""+mdata.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView instructor_name;
        ImageView image;

        Instructor instructor;

        public ViewHolder(View itemView) {
            super(itemView);

            instructor_name= (TextView) itemView.findViewById(R.id.textView_instructor_info_name);
            image = (ImageView) itemView.findViewById(R.id.imageView_instructor_info_profilepic);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructor_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Instructor instructor = mdata.get(position);
        holder.instructor_name.setText(instructor.getFirstname()+" "+instructor.getLastname());

        if(!instructor.getProfilepic().equals("asd")) {
            File imgFile = new File(instructor.getProfilepic());

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                holder.image.setImageBitmap(myBitmap);

            }
        }

        holder.instructor = instructor;

        Log.d("mdata size",""+mdata.get(position));


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }


}
