package com.example.mypc.socialnetworkingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

public class FriendsActivity extends AppCompatActivity implements AllFriendsFragment.AllFriendsInteractionListener,PendingRequestsFragment.PendingRequestsInteractionListener,AddFriendFragment.AddFriendInteractionListener{

    private final static String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPager;

    private ViewPager mViewPager;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        mSectionsPager = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container_view_pager);
        setUpViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        iv = (ImageView) findViewById(R.id.imageView_activity_friends);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FriendsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setUpViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        sectionsPageAdapter.addFragment(new AllFriendsFragment(),"Friends");
        sectionsPageAdapter.addFragment(new AddFriendFragment(),"Add New Friend");
        sectionsPageAdapter.addFragment(new PendingRequestsFragment(),"Requests Pending");


        viewPager.setAdapter(sectionsPageAdapter);
    }

    @Override
    public void allfriendsMethod() {

    }

    @Override
    public void addFriend(Uri uri) {

    }

    @Override
    public void pendingMethod() {

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
                if(MainActivity.currentUser !=null){
                    MainActivity.currentUser = null;
                    MainActivity.activeUser = null;
                    Intent intent = new Intent(FriendsActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }
}
