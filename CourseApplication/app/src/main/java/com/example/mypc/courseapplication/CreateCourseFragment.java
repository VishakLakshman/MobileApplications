package com.example.mypc.courseapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CreateCourseFragment extends Fragment {

    private CreateCourseInteractionListener mListener;

    String[] DAY,TIME,SEMESTER;

    public CreateCourseFragment() {

        DAY = new String[6];
        TIME = new String[2];
        SEMESTER = new String[4];

        DAY[0] = "Select";DAY[1] = "Monday";DAY[2] = "Tuesday";DAY[3] = "Wednesday";DAY[4] = "Thursday";DAY[5] = "Friday";
        TIME[0] = "AM";TIME[1] = "PM";
        SEMESTER[0] = "Select Semester";SEMESTER[1] = "Spring";SEMESTER[2] = "Fall";SEMESTER[3] = "Summer";
    }

    Spinner spinner_day,spinner_time,spinner_semester;

    EditText editText_title, editText_hour,editText_minutes;

    RadioGroup credit;

    Button reset,create;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Course course;

    StringBuffer schedule;
    String credit_selected;

    TextView warning;

    AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View create_course_view = inflater.inflate(R.layout.fragment_create_course, container, false);

        spinner_day = (Spinner) create_course_view.findViewById(R.id.spinner_create_course_day);
        spinner_time = (Spinner) create_course_view.findViewById(R.id.spinner_create_course_time);
        spinner_semester = (Spinner) create_course_view.findViewById(R.id.spinner_create_course_semester);

        editText_title = (EditText) create_course_view.findViewById(R.id.editText_createcourse_title);
        editText_hour = (EditText) create_course_view.findViewById(R.id.editText_createcourse_hour);
        editText_minutes = (EditText) create_course_view.findViewById(R.id.editText_createcourse_minutes);

        warning = (TextView) create_course_view.findViewById(R.id.textview_createcourse_warning);

        reset =(Button) create_course_view.findViewById(R.id.button_createcourse_reset);
        create =(Button) create_course_view.findViewById(R.id.button_createcourse_create);
        create.setEnabled(false);

        credit =(RadioGroup) create_course_view.findViewById(R.id.radioGroup_createcourse_credithours);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, DAY);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(spinnerArrayAdapter);
        spinner_day.setSelection(0);

        spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, TIME);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_time.setAdapter(spinnerArrayAdapter);
        spinner_time.setSelection(0);


        spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, SEMESTER);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_semester.setAdapter(spinnerArrayAdapter);
        spinner_semester.setSelection(0);



        mRecyclerView = (RecyclerView) create_course_view.findViewById(R.id.recyclerView_createcourse_instructors);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);


        if(MainActivity.instructorslist.size() ==0)
        {
            mRecyclerView.setVisibility(View.INVISIBLE);
            warning.setVisibility(View.VISIBLE);
            create.setEnabled(false);
        }
        else {
            mRecyclerView.setVisibility(View.VISIBLE);
            warning.setVisibility(View.INVISIBLE);
            create.setEnabled(true);
            mAdapter = new InstructorSmallAdapter(MainActivity.instructorslist);
            mRecyclerView.setAdapter(mAdapter);
        }
        credit_selected = "nothing";

        alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Are you sure you want to clear everything ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                editText_title.setText("");
                                editText_hour.setText("");
                                editText_minutes.setText("");

                                spinner_day.setSelection(0);
                                spinner_time.setSelection(0);
                                spinner_semester.setSelection(0);

                                credit.clearCheck();

                                course = new Course();

                                mAdapter = new InstructorSmallAdapter(MainActivity.instructorslist);
                                mRecyclerView.setAdapter(mAdapter);

                            }
                        });
                alertDialog.show();
            }
        });

        credit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                if(credit.getCheckedRadioButtonId() == R.id.radioButtonOne)
                {
                    credit_selected = "1";
                }
                else
                    if(credit.getCheckedRadioButtonId() == R.id.radioButtonTwo)
                    {
                        credit_selected = "2";
                    }
                    else
                        if(credit.getCheckedRadioButtonId() == R.id.radioButtonThree)
                        {
                            credit_selected = "3";
                        }


            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(editText_title.getText().toString().equals("") || editText_hour.getText().toString().equals("")
                || editText_minutes.getText().toString().equals("") || spinner_day.getSelectedItemPosition() == 0
                       || spinner_semester.getSelectedItemPosition() == 0 || credit_selected == "nothing")
               {
                   Toast.makeText(getActivity(),"All fields are mandatory",Toast.LENGTH_LONG).show();
               }
               else
               {
                   String h = editText_hour.getText().toString();
                   String m = editText_minutes.getText().toString();
                   if(Integer.parseInt(h) > 12 || Integer.parseInt(h) < 1
                           || Integer.parseInt(h) > 59 || Integer.parseInt(h) < 0)
                   {
                       Toast.makeText(getActivity(),"Invalid Time",Toast.LENGTH_LONG).show();
                   }
                   else {
                       course = new Course();
                       schedule = new StringBuffer();

                       course.setTitle(editText_title.getText().toString());
                       course.setInstructor(MainActivity.instructorslist.get(0));
                       course.setSemester(spinner_semester.getSelectedItem().toString());

                       schedule.append(editText_hour.getText().toString()).append(":").append(editText_minutes.getText().toString());
                       schedule.append(" ").append(spinner_day.getItemAtPosition(spinner_day.getSelectedItemPosition()).toString());
                       course.setSchedule(schedule.toString());


                       course.setCredits(credit_selected);

                       MainActivity.courseslist.add(course);
                   }
               }

            }
        });

        return create_course_view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateCourseInteractionListener) {
            mListener = (CreateCourseInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CreateCourseInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface CreateCourseInteractionListener {
        // TODO: Update argument type and name
        void createcoursemethod();
    }
}
