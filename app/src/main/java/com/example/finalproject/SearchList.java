package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

//Search List
public class SearchList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String myPreferences = "File";
    private static final String Search = "Search";
    private EditText searchEdt;
    private Button searchBtn;
    private String textSearch = " " ;
    private String searchStr;
    private  SharedPreferences pref;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navView;
    private Intent intent;
    private AlertDialog.Builder alertdialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);


        searchBtn = (Button) findViewById(R.id.searchMain);
        pref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        loadSearch();

        searchEdt = (EditText) findViewById(R.id.searchEdt);
        searchEdt.setText(textSearch);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                searchStr = searchEdt.getText().toString();

                if (!searchStr.isEmpty()) {

                    Intent goToProfile = new Intent(SearchList.this, SearchArticle.class);
                    goToProfile.putExtra("SEARCH", textSearch);
                    startActivity(goToProfile);



                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.searchEmpty), Toast.LENGTH_SHORT).show();
                }

            }
        });

        //updates
        toolbar = (Toolbar)findViewById(R.id.toolbarT);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    //return
    }

    protected void onPause() {
        super.onPause();

        searchEdt = (EditText) findViewById(R.id.searchEdt);
        pref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        searchStr = searchEdt.getText().toString();

        //editor
        SharedPreferences.Editor edit = pref.edit();

        edit.putString(Search, searchStr);
        edit.commit();

    }

    public void loadSearch() {

        pref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        textSearch = pref.getString(Search, "");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item1:

                break;

            case R.id.item2:
                alertdialogBuilder = new AlertDialog.Builder(SearchList.this);
                alertdialogBuilder.setTitle(getString(R.string.alertDialog));


                alertdialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = alertdialogBuilder.create();dialog.show();

                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.favouriteItem:

                intent = new Intent(SearchList.this, Favourite_list.class);
                startActivity(intent);

                break;

            case R.id.goBack:

                intent = new Intent(SearchList.this, MainActivity.class);
                startActivity(intent);
                break;

            case R.id.author:

                Toast.makeText(SearchList.this, getString(R.string.author), Toast.LENGTH_SHORT) .show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}