package com.example.mypc.socialnetworkingapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SignUpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SignUpInteractionListener mListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
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


    TextView firstname,lastname,email,password,confirmp;
    EditText dob;

    Button cancel,signup;

    FirebaseAuth firebaseAuth;

    DatabaseReference useroneRef;

    Calendar bDatePicker = Calendar.getInstance();


    DatePickerDialog.OnDateSetListener date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View signup_view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        firstname = (TextView) signup_view.findViewById(R.id.editText_signup_fname);
        lastname = (TextView) signup_view.findViewById(R.id.editText_signup_lname);
        email = (TextView) signup_view.findViewById(R.id.editText_signup_email);
        dob = (EditText) signup_view.findViewById(R.id.editText_signup_bday);
        password = (TextView) signup_view.findViewById(R.id.editText_signup_password);
        confirmp = (TextView) signup_view.findViewById(R.id.editText_signup_confirm_password);

        cancel = (Button) signup_view.findViewById(R.id.button_signup_cancel);
        signup = (Button) signup_view.findViewById(R.id.button_signup_signup);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstname.setText("");
                lastname.setText("");
                email.setText("");
                password.setText("");
                confirmp.setText("");
                dob.setText("");

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, new LogInFragment());
                fragmentTransaction.commit();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                bDatePicker.set(Calendar.YEAR, year);
                bDatePicker.set(Calendar.MONTH, monthOfYear);
                bDatePicker.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                dob = updateLabel(dob);
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), date, bDatePicker.get(Calendar.YEAR), bDatePicker.get(Calendar.MONTH),
                        bDatePicker.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(password.getText().toString().equals(confirmp.getText().toString()))
                {
                    if(email.getText().toString().equals("") || password.getText().equals("")
                            ||confirmp.getText().toString().equals("") || firstname.getText().toString().equals("")
                            ||lastname.getText().toString().equals("") ||dob.getText().toString().equals("")) {
                        Toast.makeText(getActivity(),"All fields are mandatory",Toast.LENGTH_LONG).show();
                    }
                    else {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
                        Date d1, d2, d3;

                        long diff = 11;

                        try {
                            d1 = sdf.parse(dob.getText().toString());
                            d2 = Calendar.getInstance().getTime();
                            diff = Math.round((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24 * 365));

                        } catch (Exception e) {

                        }

                        if(diff >= 13)
                        {
                        String emailtext = email.getText().toString();
                        String passwordtext = password.getText().toString();

                        firebaseAuth.createUserWithEmailAndPassword(emailtext, passwordtext)
                                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("Hello", "createUserWithEmail:success");
                                            useroneRef = MainActivity.mRootRef.child(email.getText().toString().replace("@", "").replace(".", ""));

                                            useroneRef.child("email").setValue(email.getText().toString());
                                            useroneRef.child("fname").setValue(firstname.getText().toString());
                                            useroneRef.child("lastname").setValue(lastname.getText().toString());
                                            useroneRef.child("dob").setValue(dob.getText().toString());

                                            Toast.makeText(getActivity(), "User created successfully",Toast.LENGTH_LONG).show();

                                            updateUI();

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("Error", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(getActivity(), "Email already in use",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else {
                            Toast.makeText(getActivity(), "Age should be greater than 13",Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Passwords mismatch",Toast.LENGTH_LONG).show();
                }

            }
        });


        return signup_view;
    }


    void updateUI(){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new LogInFragment());
        fragmentTransaction.commit();
    }

    private EditText updateLabel(EditText editText) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(bDatePicker.getTime()));
        return editText;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignUpInteractionListener) {
            mListener = (SignUpInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface SignUpInteractionListener {
        // TODO: Update argument type and name
        void signupmethod();
    }
}
