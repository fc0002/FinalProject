package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.collection.CircularArray;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    private ArrayList<Article> articleArrayList;
    private Context context;
    private TextView artName;
    private TextView artTitle;
    private TextView artUrl;
    private TextView artSection;
    private LayoutInflater inflater;

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

        artName = v.findViewById(R.id.articleName);
        artTitle = v.findViewById(R.id.articleTitle);
        artUrl = v.findViewById(R.id.articleUrl);
        artSection = v.findViewById(R.id.artSectionName);

        artName.setText(articleArrayList.get(position).getArticleName());
        artTitle.setText(articleArrayList.get(position).getTitle());
        artUrl.setText(articleArrayList.get(position).getUrl());
        artSection.setText(articleArrayList.get(position).getSection());

        return v;
    }
}
