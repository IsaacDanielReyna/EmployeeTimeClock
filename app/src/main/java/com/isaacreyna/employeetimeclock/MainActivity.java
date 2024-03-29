package com.isaacreyna.employeetimeclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.isaacreyna.employeetimeclock.models.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ExampleFragment.OnFragmentInteractionListener {
    private SharedPreferences Settings;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO: Work on this part //////////////////////////////////////////////////////////////////////////////////
        Settings = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = Settings.getString("user", "");
        user = gson.fromJson(json, User.class);
        Log.i("APISYSTEM: ", user.username + ", token: " + user.token );
        setNavHeader(user);
        loadTimeClock();
    }

    void setNavHeader(User user){
        final View navView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null); //navigation header menu layout

        TextView name = (TextView) navView.findViewById(R.id.fullname);
        name.setText(user.DisplayName());

        TextView email = (TextView) navView.findViewById(R.id.email);
        email.setText(user.email);

        //Loading an image
        String url = "http://www.isaacreyna.com/system/profile/uploads/"+user.photo;
        ImageView iv = (ImageView) navView.findViewById(R.id.imageView);
        Picasso.with(getApplicationContext()).load(url).fit().into(iv);
        /**/
        Picasso.with(this).load(url).into(new Target() {

            @Override
            public void onBitmapLoaded (final Bitmap bitmap, Picasso.LoadedFrom from){
                Drawable drawable = new BitmapDrawable(getApplicationContext().getResources(), bitmap);
                navView.findViewById(R.id.BackgroundImageView).setBackground(drawable);
            }

            @Override
            public void onBitmapFailed(final Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(final Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });
        /**/

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.addHeaderView(navView);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_timeclock) {
            loadTimeClock();
        } else if (id == R.id.nav_timesheet) {
            loadTimeSheets();
        } else if (id == R.id.nav_companies) {
            loadCompanies();
        } else if (id == R.id.ic_logout){
            logout();
            Log.i("APISYSTEM", "loggingout");
            /*
            // You can change the number of passed variables and their types.
            ExampleFragment exampleFragment =  ExampleFragment.newInstance("data1", "data2", "Inside MainActivity|");
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.fragment_layout, exampleFragment, exampleFragment.getTag()).commit();
            */
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //TODO: This was added after I added ExampleFragment.OnFragmentInteractionListener
    @Override
    public void onFragmentInteraction(String data) {
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }

    void loadTimeClock()
    {
        TimeClockFragment fragment = new TimeClockFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();
    }

    void loadTimeSheets(){
        // Allows to pass vars to fragment
        Bundle bundle = new Bundle();
        bundle.putString("company", user.default_company+"");
        bundle.putString("token", user.token);

        // Create and Start fragment
        TimeSheetsFragment fragment = new TimeSheetsFragment();
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();
    }


    void loadCompanies(){
        CompaniesFragment fragment = new CompaniesFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment_layout, fragment, fragment.getTag()).commit();
    }

    void logout()
    {
        SharedPreferences Settings;
        Settings = getApplicationContext().getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = Settings.edit();
        editor.clear();
        editor.commit();
        finish();
        Intent intent = new Intent (this, InitialActivity.class);
        startActivity(intent);
    }


}
