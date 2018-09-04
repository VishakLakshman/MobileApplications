package com.example.mypc.filesearch;

/**
 * Created by My Pc on 24-09-2017.
 */

/*
* Group 29
* Homework 3
* Name :
* 1. Akshay M Adagale 800987050
* 2. Vishak Lakshman Sanjeevikani Murugesh 800985356
*
* */
import android.app.Activity;
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
        import android.widget.Toast;

        import java.io.File;
        import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Activity context;
    ArrayList<String> searchList;

    private static LayoutInflater inflater = null;

    public CustomAdapter(Activity context, ArrayList<String> searchList){

        this.context = context;
        this.searchList = searchList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return searchList.size();
    }

    @Override
    public String getItem(int i) {
        return searchList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    int position;
    View userView;
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        userView = view;
        userView = (userView == null) ? inflater.inflate(R.layout.row,null) : userView ;

        TextView tv = (TextView) userView.findViewById(R.id.searchTextView);
        Button remove = (Button) userView.findViewById(R.id.removeButton);


        String selectedUser = searchList.get(i);

        tv.setText (selectedUser);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parentRow = (View) view.getParent();
                ListView lv = (ListView) parentRow.getParent();
                position = lv.getPositionForView(parentRow);
                searchList.remove(position);
                notifyDataSetChanged();
            }
        });

        return userView;
    }
}
