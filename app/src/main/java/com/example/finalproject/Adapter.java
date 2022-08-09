package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private ArrayList<Article> articleArrayList;
    private Context context;
    private LayoutInflater inflater;
    private TextView artcTitle;
    private TextView artcUrl;
    private TextView artcSection;

    public Adapter(ArrayList<Article> articleArrayList, Context context) {

        this.articleArrayList = articleArrayList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addList(Article article) {
        articleArrayList.add(article);
    }

    @Override
    public int getCount() {
        return articleArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return articleArrayList.get(position).getId();

    }

    @Override
    public View getView(int position, View newView, ViewGroup parent) {

        View v;
        v = inflater.inflate(R.layout.search_detail, parent, false);


        artcTitle = v.findViewById(R.id.article_title);
        artcUrl = v.findViewById(R.id.article_url);
        Object artSection = v.findViewById(R.id.article_section);

        artcTitle.setText(articleArrayList.get(position).getTitle());
        artcUrl.setText(articleArrayList.get(position).getUrl());
        artcSection.setText(articleArrayList.get(position).getSection());

        return v;

    }
}