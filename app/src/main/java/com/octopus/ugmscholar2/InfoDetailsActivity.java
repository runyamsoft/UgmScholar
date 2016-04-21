package com.octopus.ugmscholar2;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class InfoDetailsActivity extends AppCompatActivity {

    TextView title, desc;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_details);
        Intent intent = new Intent();
        intent = getIntent();
        title= (TextView) findViewById(R.id.title);
        desc = (TextView) findViewById(R.id.desc);
        img = (ImageView) findViewById(R.id.img);
        Ion.with(img).deepZoom().load(intent.getStringExtra("img"));
        title.setText(intent.getStringExtra("title"));
        new loadData("http://ditmawa.ugm.ac.id/"+intent.getStringExtra("url")).execute();

    }

    class loadData extends AsyncTask<String,Void,Void> {

        String url, descData;
        loadData(String url){
            this.url = url;
        }
        @Override
        protected Void doInBackground(String... params) {
            Document doc;
            Elements article;
            try {
                doc = Jsoup.connect(url).get();
                article = doc.select(".uk-article");
                if(article.select("p").size()<=2) {
                    descData ="";
                }
                else {
                    descData = article.select("p").get(2).text();
                }
                Log.d("s",""+article.select("p").size());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("failed","fail");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            desc = (TextView) findViewById(R.id.desc);
            desc.setText(descData);
        }
    }
}
