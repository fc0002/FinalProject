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
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import java.util.ArrayList;
import android.database.Cursor;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.snackbar.Snackbar;

//Favourite list
public class Favourite_list extends AppCompatActivity  implements OnNavigationItemSelectedListener  {


    private ListView listView;
    private com.example.finalproject.Adapter adapter;

    //Article array
    ArrayList<com.example.finalproject.Article> articleArrayList = new ArrayList<>();
    private EditText articleTitle;
    private EditText articleUrl;
    private EditText articleSection;
    private String titleStr;
    private String urlStr;
    private String sectionStr;
    private Intent intent;
    private Toolbar toolbar;
    private AlertDialog.Builder alertdialogBuilder;
    private Button favouriteBtn;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navView;
    private ContentValues newRowValues;
    private SQLiteDatabase db;
    private com.example.finalproject.Article article;
    private String artTitle;
    private String artUrl;
    private String artSection;
    private long newId;
    private MyOpener dbOpener;
    private Cursor results;
    private AlertDialog dialog;
    private ContentValues contentValues;

    public static final String ITEM_ID = "ID";
    public static final String CHECKBOX = "CHECKBOX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_to_add);
        favouriteBtn = (Button) findViewById(R.id.addFvrBtn);

        listView = (ListView) findViewById(R.id.listView);

        articleTitle = (EditText)findViewById(R.id.article_title);
        articleUrl = (EditText)findViewById(R.id.article_url);
        articleSection = (EditText)findViewById(R.id.article_section);

        intent = getIntent();
        titleStr = intent.getStringExtra("ARTICLE TITLE");
        urlStr = intent.getStringExtra("ARTICLE URL");
        final String[] sectionStr = {intent.getStringExtra("ARTICLE SECTION")};

        articleTitle.setText(titleStr);
        articleUrl.setText(urlStr);
        articleSection.setText(sectionStr[0]);


        toolbar = (Toolbar)findViewById(R.id.toolbarT);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener((OnNavigationItemSelectedListener) this);

        adapter = new com.example.finalproject.Adapter(articleArrayList, getApplicationContext());
        listView.setAdapter(adapter);
        loadDataFromDatabase();


        favouriteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                newRowValues = new ContentValues();

                titleStr = articleTitle.getText().toString();
                urlStr = articleUrl.getText().toString();
                sectionStr[0] = articleSection.getText().toString();
                if (!titleStr.isEmpty() && !urlStr.isEmpty() && !sectionStr.isEmpty()) {
                    newRowValues.put(MyOpener.COL_TITLE, titleStr);
                    newRowValues.put(MyOpener.COL_URL, urlStr);
                    newRowValues.put(MyOpener.COL_SECTION, sectionStr[0]);
                    newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);

                    article = new com.example.finalproject.Article(titleStr, urlStr, sectionStr,   true, newId);
                    articleArrayList.add(article);
                    articleTitle.setText("");
                    articleUrl.setText("");
                    articleSection.setText("");
                    adapter.notifyDataSetChanged();
                    Snackbar.make(favouriteBtn, getResources().getString(R.string.confirm3), Snackbar.LENGTH_LONG)

                            .setAction(getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })

                            .show();
                }else{

                    Toast.makeText(getApplicationContext(), getString(R.string.favEmpty), Toast.LENGTH_SHORT).show();

                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                article = articleArrayList.get(position);

                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getUrl()));
                startActivity(intent);
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                alertdialogBuilder = new AlertDialog.Builder(Favourite_list.this);
                alertdialogBuilder.setTitle(getString(R.string.delete));
                alertdialogBuilder.setMessage("Select row is " + position + ("\nDatabase selected id is ") + id);

                alertdialogBuilder.setPositiveButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getApplicationContext(), getString(R.string.cancelStr), Toast.LENGTH_LONG).show();
                    }
                });
                alertdialogBuilder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteArticleFromDB(articleArrayList.get(position));
                        articleArrayList.remove(position);
                        adapter.notifyDataSetChanged();

                        Toast.makeText(getApplicationContext(), getString(R.string.artDelete), Toast.LENGTH_LONG).show();
                    }

                    private void deleteArticleFromDB(Article article) {
                    }
                });

                dialog = alertdialogBuilder.create();

                dialog.show();
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar2, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item1:

                break;

            case R.id.item2:

                alertdialogBuilder = new AlertDialog.Builder(Favourite_list.this);
                alertdialogBuilder.setTitle(getString(R.string.helpMenu));

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.backToSearch:


                intent = new Intent(Favourite_list.this, SearchList.class);
                startActivity(intent);

                break;

            case R.id.goBack:

                intent = new Intent(Favourite_list.this, MainActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    private void loadDataFromDatabase() {

        dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        String[] columns = {MyOpener.COL_ID, MyOpener.COL_TITLE, MyOpener.COL_URL, MyOpener.COL_SECTION,};

        //cursor
        results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results, db.getVersion());

        //Now results match the query.
        // find the column indices:
        int idColumnIndex = results.getColumnIndex(MyOpener.COL_ID);
        int titleColumnIndex = results.getColumnIndex(MyOpener.COL_TITLE);
        int urlColumnIndex = results.getColumnIndex(MyOpener.COL_URL);
        int sectionColumnIndex = results.getColumnIndex(MyOpener.COL_SECTION);
        results.moveToPosition(-1);

        while (results.moveToNext()) {

            titleStr = results.getString(titleColumnIndex);
            urlStr= results.getString(urlColumnIndex);
            sectionStr= results.getString(sectionColumnIndex);
            newId = results.getLong(idColumnIndex);


            articleArrayList.add(new com.example.finalproject.Article(titleStr, urlStr, sectionStr,true, newId));

        }


    }

    //Update
    protected void updateArticle(com.example.finalproject.Article article)
    {
        //Represents a database row:
        contentValues = new ContentValues();
        contentValues.put(MyOpener.COL_TITLE, article.getTitle());
        contentValues.put(MyOpener.COL_URL, article.getUrl());
        contentValues.put(MyOpener.COL_NAME, article.getSection());

        //Function
        db.update(MyOpener.TABLE_NAME, contentValues, MyOpener.COL_ID + "= ?", new String[] {Long.toString(article.getId())});

    }

    //Delete
    protected void   deleteArticleFromDB(com.example.finalproject.Article article)
    {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + " = ?", new String[]{Long.toString(article.getId())});
    }

    //Print
    public void printCursor(Cursor cursor, int version) {
        int colIndex = cursor.getColumnIndex(MyOpener.COL_TITLE);
        cursor.moveToFirst();

        //Not working error, ????
        for (int i = 0; i < cursor.getCount(); i++) {
            String fn = cursor.getString(colIndex);
            Log.i("Section: ", (cursor.getString(cursor.getColumnIndex(MyOpener.COL_SECTION)) + " Title: " + (cursor.getString(cursor.getColumnIndex(MyOpener.COL_TITLE)))
                    + "Url:  " + (cursor.getString(cursor.getColumnIndex(MyOpener.COL_URL)))));
            cursor.moveToNext();
        }
    }
}




