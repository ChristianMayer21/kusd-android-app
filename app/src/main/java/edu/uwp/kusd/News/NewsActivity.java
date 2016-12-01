package edu.uwp.kusd.News;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import edu.uwp.kusd.R;
import edu.uwp.kusd.network.CacheStringRequest;
import edu.uwp.kusd.network.VolleySingleton;

public class NewsActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    private static final String NEWS_URL = "http://www.kusd.edu/xml-news";
    private RecyclerView recyclerview;
    private String[] nItems;
    private String[] nDate;
    private String[] nContent;
    private ArrayList<NewsItems> newsItemsG;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("News");

        actionBar.setDisplayHomeAsUpEnabled(true);




        requestQueue = VolleySingleton.getsInstance().getRequestQueue();


        CacheStringRequest stringRequest = new CacheStringRequest(Request.Method.GET, NEWS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<NewsItems> newsItems = null;

                try {
                    NewsRead parser = new NewsRead();

                    //parse news items into news items list
                    InputStream xmlInputStream = new ByteArrayInputStream(response.getBytes("UTF-8"));
                    newsItems = parser.parse(xmlInputStream);


                    recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(NewsActivity.this);
                    recyclerview.setLayoutManager(layoutManager);
                    NewsAdapter adapter = new NewsAdapter(newsItems,NewsActivity.this);
                    recyclerview.setAdapter(adapter);





                } catch (IOException e) {e.printStackTrace();}


            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(stringRequest);




    }

}
