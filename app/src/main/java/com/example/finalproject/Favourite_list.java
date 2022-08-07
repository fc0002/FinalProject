package com.example.finalproject;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class Favourite_list<MyOpenerG> extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;
    private Adapter adapter;
    ArrayList<Article> articleArrayList = new ArrayList<>();
    private EditText articleName;
    private EditText articleTitle;
    private EditText articleUrl;
    private EditText articleSection;
    private String nameStr;
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
    private Article article;
    private String artName;
    private String artTitle;
    private String artUrl;
    private long newId;
    private MyOpenerG dbOpener;
    private Cursor results;
    private AlertDialog dialog;
    private ContentValues contentValues;

    public static final String ITEM_ID = "ID";
    public static final String CHECKBOX = "CHECKBOX";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_to_add);
        favouriteBtn = (Button) findViewById(R.id.addFvrBtn);

        listView = (ListView) findViewById(R.id.listView);

        articleName = (EditText) findViewById(R.id.article_name);
        articleTitle = (EditText) findViewById(R.id.article_title);
        articleUrl = (EditText) findViewById(R.id.article_url);
        articleSection = (EditText) findViewById(R.id.article_section);

        intent = getIntent();
        nameStr = intent.getStringExtra("ARTICLE NAME");
        titleStr = intent.getStringExtra("ARTICLE TITLE");
        urlStr = intent.getStringExtra("ARTICLE URL");


        articleName.setText(nameStr);
        articleTitle.setText(titleStr);
        articleUrl.setText(urlStr);


        toolbar = (Toolbar) findViewById(R.id.toolbarT);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        adapter = new Adapter(articleArrayList, getApplicationContext());
        listView.setAdapter(adapter);
        loadDataFromDatabase();


        favouriteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                newRowValues = new ContentValues();

                nameStr = articleName.getText().toString();
                titleStr = articleTitle.getText().toString();
                urlStr = articleUrl.getText().toString();
                if (!nameStr.isEmpty() && !titleStr.isEmpty() && !urlStr.isEmpty()) {
                    newRowValues.put(MyOpenerG.COL_NAME, nameStr);
                    newRowValues.put(MyOpenerG.COL_TITLE, titleStr);
                    newRowValues.put(MyOpenerG.COL_URL, urlStr);
                    newRowValues.put(MyOpenerG.COL_SECTION, sectionStr);
                    newId = db.insert(MyOpenerG.TABLE_NAME, null, newRowValues);
                    article = new Article(nameStr, titleStr, urlStr, sectionStr, true, newId);

                    articleArrayList.add(article);
                    articleName.setText("");
                    articleTitle.setText("");
                    articleUrl.setText("");

                    adapter.notifyDataSetChanged();
                    Snackbar.make(favouriteBtn, getResources().getString(R.string.confirm3), Snackbar.LENGTH_LONG)

                            .setAction(getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })

                            .show();
                } else {

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

        dbOpener = new MyOpenerG(this);
        db = dbOpener.getWritableDatabase();

        String[] columns = {MyOpenerG.COL_ID, MyOpenerG.COL_NAME, MyOpenerG.COL_TITLE, MyOpenerG.COL_URL, MyOpener.COL_SECTION};
        //cursor
        results = db.query(false, MyOpenerG.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results, db.getVersion());

        //Now the results object has rows of results that match the query.

        // find the column indices:
        int idColumnIndex = results.getColumnIndex(MyOpenerG.COL_ID);
        int nameColumnIndex = results.getColumnIndex(MyOpenerG.COL_NAME);
        int titleColumnIndex = results.getColumnIndex(MyOpenerG.COL_TITLE);
        int urlColumnIndex = results.getColumnIndex(MyOpenerG.COL_URL);
        int sectionColumnIndex = results.getColumnIndex(MyOpenerG.COL_SECTION);
        results.moveToPosition(-1);

        while (results.moveToNext()) {

            nameStr = results.getString(nameColumnIndex);
            titleStr = results.getString(titleColumnIndex);
            urlStr = results.getString(urlColumnIndex);
            sectionStr = results.getString(sectionStr);
            newId = results.getLong(idColumnIndex);


            articleArrayList.add(new Article(nameStr, titleStr, urlStr, sectionStr, true, newId));

        }


    }

    /**
     * Update article.
     *
     * @param article the article
     */
    protected void updateArticle(Article article) {
        //Create a ContentValues object to represent a database row:
        contentValues = new ContentValues();
        contentValues.put(MyOpenerG.COL_NAME, article.getArticleName());
        contentValues.put(MyOpenerG.COL_TITLE, article.getTitle());
        contentValues.put(MyOpenerG.COL_URL, article.getUrl());
        contentValues.put(MyOpenerG.COL_SECTION, article.getSection());
        //now call the update function:
        db.update(MyOpenerG.TABLE_NAME, contentValues, MyOpenerG.COL_ID + "= ?", new String[]{Long.toString(article.getId())});

    }


    /**
     * Delete article from db.
     *
     * @param article the article
     */
    protected void deleteArticleFromDB(Article article) {
        db.delete(MyOpenerG.TABLE_NAME, MyOpenerG.COL_ID + " = ?", new String[]{Long.toString(article.getId())});
    }


    /**
     * Print cursor.
     *
     * @param cursor  the cursor
     * @param version the version
     */
    public void printCursor(Cursor cursor, int version) {

        int colIndex = cursor.getColumnIndex(MyOpenerG.COL_NAME);

        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            String fn = cursor.getString(colIndex);
            Log.i("ArticleName: ", (cursor.getString(cursor.getColumnIndex(MyOpenerG.COL_NAME)) + " Title: " + (cursor.getString(cursor.getColumnIndex(MyOpenerG.COL_TITLE)))
                    + "Url:  " + (cursor.getString(cursor.getColumnIndex(MyOpenerG.COL_URL)))));
            cursor.moveToNext();
        }

    }

}


