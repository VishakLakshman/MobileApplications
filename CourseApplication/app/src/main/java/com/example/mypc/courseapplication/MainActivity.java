package com.example.mypc.courseapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SignInFragment.SignInInteractionListener,SignUpFragment.SignUpInteractionListener,CourseManagerFragment.CourseManagerInteractionListener,CreateCourseFragment.CreateCourseInteractionListener,AddInstructorFragment.AddInstructorInteractionListener,InstructorsFragment.InstructorsInteractionListener{


    static ArrayList<Users> userlist = new ArrayList<>();
    static ArrayList<Instructor> instructorslist = new ArrayList<>();
    static ArrayList<Course> courseslist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new SignInFragment(),"sigin").commit();


    }


    @Override
    public void sampleMethod() {

    }

    @Override
    public void signupSampleMethod() {

    }

    @Override
    public void CourseManagerMethod() {

    }

    @Override
    public void addInsMethod() {

    }

    @Override
    public void createcoursemethod() {

    }

    @Override
    public void intructorsMethod() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_action, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new CourseManagerFragment(),"coursemanager").addToBackStack(null).commit();
                return true;

            case R.id.action_instructors:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new InstructorsFragment(),"instructors").addToBackStack(null).commit();
                return true;

            case R.id.action_add_instructor:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new AddInstructorFragment(),"addinstructor").addToBackStack(null).commit();
                return true;

            case R.id.action_log_out:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new SignInFragment(),"sigin_1").commit();
                return true;

            case R.id.action_quit:
                System.exit(0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
