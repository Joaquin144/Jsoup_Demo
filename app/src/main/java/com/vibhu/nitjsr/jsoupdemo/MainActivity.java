package com.vibhu.nitjsr.jsoupdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.helpers.ParserAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ParseAdapter adapter;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ProgressBar progressBar;
    private String TAG = "#### MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressBar);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ParseAdapter(parseItems,this);
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute();
    }

    private class Content extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String url = "https://www.cinemaqatar.com/";
                Document doc = Jsoup.connect(url).get();
                Elements data = doc.select("span.thumbnail");
                int size = data.size();
                Log.d(TAG,"Bhaiya ji humko issize ki list mili  : "+size);
                for(int i=0;i<size;i++){
                    String imgUrl = data.select("span.thumbnail")
                            .select("img")
                            .eq(i)
                            .attr("src");
                    String title = data.select("h4.gridminfotitle")
                            .select("span")
                            .eq(i)
                            .text();
                    parseItems.add(new ParseItem(imgUrl,title));
                    Log.d(TAG,"Waah bhaimya appka app chal pada ????????\n imgURL  : "+imgUrl+"\ntitle  : "+title);
                }

            }
            catch (IOException e) {
                Log.d(TAG,"Le bhai kaam toh nahi hua");
                //Toast.makeText(this, "Sorry we encountered an error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }
    }
}