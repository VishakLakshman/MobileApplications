package com.example.mypc.socialnetworkingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements SignUpFragment.SignUpInteractionListener,LogInFragment.LoginInteractionListener,ShowAllPostsFragment.ShowAllPostsInteractionListener,ShowParticularPostFragment.ShowParticularInteractionListener{

    public static FirebaseUser currentUser;
    public static GoogleSignInAccount ctUser;

    public static User activeUser;

    public static DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(currentUser != null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.container,new ShowAllPostsFragment(),"as").commit();
        }
        else
            getSupportFragmentManager().beginTransaction().add(R.id.container,new LogInFragment(),"login").commit();

    }

    @Override
    public void loginmethod() {

    }

    @Override
    public void signupmethod() {

    }

    @Override
    public void allPostsmethod() {

    }

    @Override
    public void anotherMethod() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_friends, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                if(currentUser !=null){
                currentUser = null;
                activeUser = null;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container, new LogInFragment());
                    fragmentTransaction.commit();
                }
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
}
