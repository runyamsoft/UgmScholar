package com.octopus.ugmscholar2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List <ItemData> itemDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        itemDatas = new ArrayList<ItemData>();
        new ParsePage(itemDatas).execute();



    }

    private class ParsePage extends AsyncTask <String, Void, Void>{

        List <ItemData> datas;
        ItemData tmpData;
        ParsePage(List<ItemData> datas){
            this.datas = datas;
        }


        @Override
        protected Void doInBackground(String... params) {
            Document doc;
            Elements articles;

            try {
                doc = Jsoup.connect("http://ditmawa.ugm.ac.id/category/info-beasiswa/").get();
                articles = doc.select(".uk-article");
                for (Element article : articles) {
                    tmpData = new ItemData();
                    tmpData.setTitle(article.select(".uk-article-title").text());
                    tmpData.setAuthor(article.select(".uk-article-meta a").first().text());
                    //tmpData.setImgUrl(article.select("img").first().text());
                    tmpData.setTgl(article.select(".uk-article-meta time").first().text());
                    datas.add(tmpData);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("wa", itemDatas.get(0).getTitle());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
