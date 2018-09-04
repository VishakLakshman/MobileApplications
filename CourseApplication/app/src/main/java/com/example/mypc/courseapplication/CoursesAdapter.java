package com.example.mypc.courseapplication;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
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


public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {


    ArrayList<Course> mdata;


    public CoursesAdapter(ArrayList<Course> mdata) {
        this.mdata = mdata;
        Log.d("mdata size", "" + mdata.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,instructor_name,schedule;
        ImageView image;

        Course course;

        public ViewHolder(View itemView) {
            super(itemView);

            instructor_name = (TextView) itemView.findViewById(R.id.textView_courseinfo_instructor);
            title = (TextView) itemView.findViewById(R.id.textView_courseinfo_title);
            schedule = (TextView) itemView.findViewById(R.id.textView_courseinfo_time);

            image = (ImageView) itemView.findViewById(R.id.imageView_courseinfo_profilepic);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    AlertDialog alertDialog;
    View viewdialog;

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Course course = mdata.get(position);
        Instructor instructor = course.getInstructor();

        holder.title.setText(course.getTitle());
        holder.instructor_name.setText(instructor.getFirstname() + " " + instructor.getLastname());
        holder.schedule.setText(course.getSchedule());

        if(!instructor.getProfilepic().equals("asd")) {
            File imgFile = new File(instructor.getProfilepic());

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                holder.image.setImageBitmap(myBitmap);

            }
        }

        holder.title.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                viewdialog = view;
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                View parentRow = (View) viewdialog.getParent();
                                RecyclerView lv = (RecyclerView) parentRow.getParent();
                                mdata.remove(lv.getChildPosition(parentRow));
                                notifyDataSetChanged();
                            }
                        });


                alertDialog.show();
                return false;
            }
        });

        holder.course = course;

        Log.d("mdata size", "" + mdata.get(position));

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

}
