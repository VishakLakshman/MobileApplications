package com.example.mypc.courseapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class AddInstructorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private AddInstructorInteractionListener mListener;

    public AddInstructorFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddInstructorFragment newInstance(String param1, String param2) {
        AddInstructorFragment fragment = new AddInstructorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    EditText editText_firstname,editText_lastname,editText_email,editText_website;

    ImageView profilepic;

    Button new_instructor_register;

    Instructor instructor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View add_instructor_view = inflater.inflate(R.layout.fragment_add_instructor, container, false);

        editText_firstname = (EditText) add_instructor_view.findViewById(R.id.editText_addinstructor_firstname);
        editText_lastname = (EditText) add_instructor_view.findViewById(R.id.editText_addinstructor_lastname);
        editText_email = (EditText) add_instructor_view.findViewById(R.id.editText_addinstructor_email);
        editText_website = (EditText) add_instructor_view.findViewById(R.id.editText_addinstructor_website);

        new_instructor_register = (Button) add_instructor_view.findViewById(R.id.button_addinstructor_register);

        profilepic = (ImageView) add_instructor_view.findViewById(R.id.imageView_addinstructor_profilepic);

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        new_instructor_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText_firstname.getText().toString().equals("") || editText_lastname.getText().toString().equals("")
                        || editText_email.getText().toString().equals("") || editText_website.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(),"All fields are mandatory",Toast.LENGTH_LONG).show();
                }
                else
                {
                    instructor = new Instructor();
                    instructor.setFirstname(editText_firstname.getText().toString());
                    instructor.setLastname(editText_lastname.getText().toString());
                    instructor.setEmail(editText_email.getText().toString());
                    instructor.setWebsite(editText_website.getText().toString());

                    instructor.setProfilepic(photoPath);

                    MainActivity.instructorslist.add(instructor);
                }

            }
        });


        return add_instructor_view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddInstructorInteractionListener) {
            mListener = (AddInstructorInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddInstructorInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface AddInstructorInteractionListener {
        // TODO: Update argument type and name
        void addInsMethod();
    }

    final static int REQUEST_IMAGE_CAPTURE = 1;

    public String photoPath="asd";

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (Exception ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            File imgFile = new  File(photoPath);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                profilepic.setImageBitmap(myBitmap);

            }

        }

    }

    private File createImageFile() {

        File imagev = null;
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp;
            File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            imagev = File.createTempFile(imageFileName, ".jpg", storageDir);


            photoPath = imagev.getAbsolutePath();
            return imagev;
        } catch (Exception e) {

        }
        return imagev;
    }


}
