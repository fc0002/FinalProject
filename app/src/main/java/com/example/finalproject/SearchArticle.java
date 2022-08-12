package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.widget.Button;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;


public class SearchArticle extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressBar progressBar;
    ArrayList<Article> articleArrayList = new ArrayList<>();
    private ListView listView;
    private Adapter adapter;
    private TextView textViewUrl;
    private String update;
    private  HttpURLConnection urlConnection;
    private URL url;
    private String urlStr;
    private String urlSearch;
    private EditText searchEdt;
    private String articleName;
    private String articleTile;
    private String articleUrl;
    private String urlBrowser;
    private String urlLoad;
    private  Intent intent;
    private Article article;
    private AlertDialog.Builder alertdialogBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_search);

        textViewUrl =(TextView) findViewById(R.id.article_url);
        listView = (ListView) findViewById(R.id.listView1);

        intent = getIntent();
        update = intent.getStringExtra("SEARCH");
        urlBrowser = intent.getStringExtra("URL");

        searchEdt = (EditText)findViewById(R.id.searchEdt);
        searchEdt.setText(update);
        urlStr = "https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=";

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        urlSearch = urlStr + update;
        ArticleQuery req = new ArticleQuery();
        req.execute();



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
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                alertdialogBuilder = new AlertDialog.Builder(SearchArticle.this);
                alertdialogBuilder.setTitle(getString(R.string.addFavorite));

                article = articleArrayList.get(position);
                articleTile = article.getTitle();
                articleUrl = article.getUrl();
                String articleSection = article.getSection();

                alertdialogBuilder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {



                        intent = new Intent(SearchArticle.this, Favourite_list.class);
                        intent.putExtra("ARTICLE TITLE", articleTile);
                        intent.putExtra("ARTICLE URL", articleUrl);
                        intent.putExtra("ARTICLE SECTION", articleSection);
                        startActivity(intent);


                        Toast.makeText(getApplicationContext(), getString(R.string.confirm), Toast.LENGTH_LONG).show();
                    }

                });


                alertdialogBuilder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        Toast.makeText(getApplicationContext(), getString(R.string.confirm2), Toast.LENGTH_LONG).show();
                    }

                });


                AlertDialog dialog = alertdialogBuilder.create();

                dialog.show();
                return true;

            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public class ArticleQuery extends AsyncTask<String, Integer, String> {

        String articleTitle;
        String articleUrl;
        String articleSection;


        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder sb = new StringBuilder();
            articleArrayList = new ArrayList<>();

            try {

                url = new URL( urlSearch );
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }

                return sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{

                if( urlConnection != null){

                    urlConnection.disconnect();
                }
            }

            return sb.toString();

        }

        //JSON
        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(String result) {

            try {
                if(!result.isEmpty()) {

                    articleArrayList = new ArrayList<>();

                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject responseJSObj = jsonObject.getJSONObject("response");
                    JSONArray jsonArray = responseJSObj.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length() - 1; i++) {


                        article = new Article();
                        JSONObject anObject = jsonArray.getJSONObject(i);

                        //Array
                        articleName = anObject.getString("sectionSection");
                        publishProgress(30);
                        articleTitle = anObject.getString("webTitle");
                        publishProgress(60);
                        articleUrl = anObject.getString("webUrl");
                        publishProgress(100);


                        article.setSection(articleSection);
                        article.setTitle(articleTitle);
                        article.setUrl(articleUrl);


                        intent.putExtra("URL", articleUrl);
                        articleArrayList.add(article);
                    }

                    adapter = new Adapter(articleArrayList, SearchArticle.this);
                    listView.setAdapter(adapter);

                }else{

                    Toast.makeText(SearchArticle.this, "not found", Toast.LENGTH_SHORT) .show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.INVISIBLE);

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);

        }

    }
}