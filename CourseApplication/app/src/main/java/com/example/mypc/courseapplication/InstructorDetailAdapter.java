package com.example.mypc.courseapplication;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class InstructorDetailAdapter extends RecyclerView.Adapter<InstructorDetailAdapter.ViewHolder> {


    ArrayList<Instructor> mdata;
    Context context;

    public InstructorDetailAdapter(ArrayList<Instructor> mdata, Context cntext) {
        this.mdata = mdata;
        this.context = cntext;
        Log.d("mdata size", "" + mdata.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,instructor_name,schedule;
        ImageView image;

        View view;

        Instructor instructor;

        public ViewHolder(View itemView) {
            super(itemView);

            instructor_name = (TextView) itemView.findViewById(R.id.textView_courseinfo_instructor);
            title = (TextView) itemView.findViewById(R.id.textView_courseinfo_title);
            schedule = (TextView) itemView.findViewById(R.id.textView_courseinfo_time);

            Linkify.addLinks(schedule,Linkify.WEB_URLS);

            image = (ImageView) itemView.findViewById(R.id.imageView_courseinfo_profilepic);

            view = itemView;

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

        Instructor instructor = mdata.get(position);

        holder.title.setText(instructor.getFirstname()+" "+instructor.getLastname());
        holder.instructor_name.setText(instructor.getEmail());
        holder.schedule.setText(instructor.getWebsite());

        if(!instructor.getProfilepic().equals("asd")) {
            File imgFile = new File(instructor.getProfilepic());

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                holder.image.setImageBitmap(myBitmap);

            }
        }

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure you want to delete ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


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

        holder.instructor = instructor;

        Log.d("mdata size", "" + mdata.get(position));

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

}
