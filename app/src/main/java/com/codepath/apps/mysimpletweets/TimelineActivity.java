package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.adapters.TweetCursorAdapter;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.UserMentionFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;

public class TimelineActivity extends ActionBarActivity implements RetweetDialog.RetweetListener{

    private User myHandle;
    public static final int  REQUEST_RESULT_POST=50;
    public static final int  REQUEST_RESULT_PROFILE=100;


    private TweetCursorAdapter tweetCursorAdapter;
    Cursor tweetCursor;
    private TweetsPageAdapter tweetsPageAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(R.layout.custom_action_bar);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF21D3FF));


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tweetsPageAdapter=new TweetsPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tweetsPageAdapter);


        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setViewPager(viewPager);

        final Button btnPost = (Button) findViewById(R.id.btnCompose);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimelineActivity.this, TweetPostActivity.class);
                //i.putExtra("personalDetails", myHandle);
                //startActivityForResult(i, REQUEST_RESULT_POST);
                startActivity(i);

            }
        });


        final Button btnProfile = (Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
                startActivity(i);

            }
        });


        /*Button btnRetweet = (Button)findViewById(R.id.btnRetweet);

        btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retweetDialog();
            }
        });*/





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return super.onOptionsItemSelected(item);
    }


    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REQUEST_RESULT_POST){
            if(resultCode==RESULT_OK){
                Tweet newTweet = data.getParcelableExtra("newTweet");
                HomeTimelineFragment fragmentDemo = (HomeTimelineFragment)tweetsPageAdapter.
                fragmentDemo.injectTweet(newTweet);


            }
        }
    }*/




    private void retweetDialog() {

        FragmentManager fm = getSupportFragmentManager();
        RetweetDialog retweetDialog = RetweetDialog.newInstance();
        retweetDialog.show(fm, "fragment_retweet");
    }


    private boolean isInternetAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onFinishRetweetDialog(Boolean returnBool) {

        if(returnBool==true){
            Toast.makeText(this, "retweet happened", Toast.LENGTH_LONG).show();
        }

    }

    public class TweetsPageAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPageAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(position==0) return new HomeTimelineFragment();
            else if (position==1) return new UserMentionFragment();
            else return null;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

}
