package com.octopus.ugmscholar2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List <ItemData> itemDatas;
    private RecyclerView rv;
    int pageCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pageCounter=1;
        itemDatas = new ArrayList<ItemData>();

        rv = (RecyclerView)findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        final RvAdapter rvAdapter = new RvAdapter(itemDatas, new RvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ItemData item) {
                Log.d("ASDSAD", item.getTitle());
            }
        });
        rv.setAdapter(rvAdapter);
        rv.addOnScrollListener(new JazzyRecyclerViewScrollListener());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        new ParsePage(itemDatas, rvAdapter).execute();
        rv.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                new ParsePage(itemDatas, rvAdapter).execute();
            }
        });








    }

    private class ParsePage extends AsyncTask <String, Void, Void>{

        List <ItemData> datas;
        ItemData tmpData;
        RvAdapter rvAdapter;
        ParsePage(List<ItemData> datas, RvAdapter rvAdapter){
            this.datas = datas;
            this.rvAdapter = rvAdapter;
        }


        @Override
        protected Void doInBackground(String... params) {
            Document doc;
            Elements articles;

            try {
                int limit = pageCounter +2;

                for(int i = pageCounter; i<=limit; i++) {
                    doc = Jsoup.connect("http://ditmawa.ugm.ac.id/category/info-beasiswa/page/"+i).get();
                    articles = doc.select(".uk-article");


                    for (Element article : articles) {
                        tmpData = new ItemData();
                        tmpData.setTitle(article.select(".uk-article-title").text());
                        tmpData.setAuthor(article.select(".uk-article-meta a").first().text());
                        tmpData.setTgl(article.select(".uk-article-meta time").first().text());
                        if (article.select("img").size() > 0) {
                            tmpData.setImgUrl("http://ditmawa.ugm.ac.id" + article.select("img").first().attr("src"));
                        } else {
                            tmpData.setImgUrl("");
                        }
                        datas.add(tmpData);
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pageCounter+=3;
            rvAdapter.notifyDataSetChanged();
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
