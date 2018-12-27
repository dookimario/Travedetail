package panupong.th.ac.rmutl.travedetail;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //    Explicit
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create Toolbar
        createToolbar();

//        Add Fragment
        addFragment();
//        Home Controller
        homeController();
//        About Controller
        aboutController();
//        info controller
        infoController();
//        Exit controller
        exitController();
//        maps cotroller
        mapsCotroller();
    }   //Main Method

    private void mapsCotroller() {
        TextView textView = findViewById(R.id.txtMap);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MapsActivity.class));
            }
        });
    }

    private void exitController() {
        TextView textView = findViewById(R.id.txtExit);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void infoController() {
        TextView textView = findViewById(R.id.txtInfo);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragmentMain, new InfoFragment()).commit();
                drawerLayout.closeDrawers();
            }
        });
    }

    private void aboutController() {
        TextView textView = findViewById(R.id.txtAbout);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragmentMain, new AboutMeFragment()).commit();
                drawerLayout.closeDrawers();
            }
        });
    }

    private void homeController() {
        TextView textView = findViewById(R.id.txtHome);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contentFragmentMain, new MainFragment()).commit();
                drawerLayout.closeDrawers();
            }
        });
    }

    private void addFragment() {
        if (checkSQLite()) {
//            Have Database
            getSupportFragmentManager().beginTransaction().add(R.id.contentFragmentMain, new MainFragment()).commit();
        } else {
//            No Database
            getSupportFragmentManager().beginTransaction().add(R.id.contentFragmentMain,new FormFragment()).commit();
        }
    }

    private boolean checkSQLite() {

        boolean result = false;

        try {

            MyManager myManager = new MyManager(MainActivity.this);
            SQLiteDatabase sqLiteDatabase = MainActivity.this.openOrCreateDatabase(MyOpenHelper.database_name,MODE_PRIVATE,null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM userTABLE", null);
            cursor.moveToFirst();

            if (cursor.getCount()== 0) {
                result = false;
            } else {
                result = true;
            }
            cursor.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();

    }

    private void createToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.title_toolbar));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_hambager);

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,R.string.open,R.string.close );
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

}   //Main Class
