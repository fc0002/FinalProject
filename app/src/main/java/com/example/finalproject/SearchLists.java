package com.example.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class SearchLists extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String myPreferences = "File";
    private static final String Search = "Search";
    private EditText searchEdit;
    private Button searchBtn;
    private String textSearch = " ";
    private String searchStr;
    private SharedPreferences pref;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Intent intent;
    private AlertDialog.Builder alertdialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchBtn = (Button) findViewById(R.id.searchMain);
        pref = getSharedPreferences(myPreferences, context.MODE_PRIVATE);
        loadSearch();

        searchEdit = (EditText) findViewById(R.id.searchEdit);
        searchEdit.setText(textSearch);

        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchStr = searchEdit.getText().toString();

                if (!searchStr.isEmpty()) {
                    Intent goToProfile = new Intent(SearchLists.this, SearchArticle.class);
                    goToProfile.putExtra("SEARCH", textSearch);
                    startActivity(goToProfile);

                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.searchEmpty), Toast.LENGTH_SHORT).show();
                }
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.set.NavigationItemSelectedListener(this);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    protected void onPause() {
        super.onPause();

        searchEdit = (EditText) findViewById(R.id.searchEdit);
        pref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);

        searchStr = searchEdit.getText().toString();
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(Search, searchStr);
        edit.commit();
    }

    public void loadSearch() {

        pref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        textSearch = pref.getString(Search, " ");
    }

    @Override
    public boolean onOptionsItemsSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                break;
            case R.id.item2:
                ;
                alertdialogBuilder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = alertdialogBuilder.create();
                dialog.show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.favouriteItem:

                itent = new Intent(SearchList.this, Favourite_list.class);
                startActivity(intent);

                break;

            case R.id.goBack:
                itent = new Intent(SearchList.this, MainActivity.class);
                startActivity(intent);


            case R.id.author:

                Toast.makeText(SearchLists.this, getString(R.string.author), Toast.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

}




