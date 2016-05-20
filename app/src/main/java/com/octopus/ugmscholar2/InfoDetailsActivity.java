package com.octopus.ugmscholar2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class InfoDetailsActivity extends AppCompatActivity {

    TextView title, desc;
    ImageView img;
    WebView wv;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_details);
        wv = (WebView)findViewById(R.id.webview);

        Intent intent = new Intent();
        intent = getIntent();
        title= (TextView) findViewById(R.id.title);
        //desc = (TextView) findViewById(R.id.desc);
        img = (ImageView) findViewById(R.id.img);
        pb = (ProgressBar)findViewById(R.id.progressBar);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                pb.setVisibility(View.VISIBLE);
                pb.setProgress(0);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pb.setProgress(100);
                pb.setVisibility(View.GONE);
                wv.loadUrl("javascript:(function() { " +
                        "document.getElementsByClassName('wrap-topnav1')[0].style.display='none'; " +
                        "document.getElementsByClassName('wrap-topnav')[0].style.display='none';" +
                        "})()");
            }
        });
        wv.loadUrl("http://ditmawa.ugm.ac.id/" + intent.getStringExtra("url"));
        /*Ion.with(img).deepZoom().load(intent.getStringExtra("img"));
        title.setText(intent.getStringExtra("title"));
        new loadData("http://ditmawa.ugm.ac.id/"+intent.getStringExtra("url")).execute();*/

    }
    public void back(View view){
        super.onBackPressed();
    }

    /*class loadData extends AsyncTask<String,Void,Void> {

        String url, descData;
        loadData(String url){
            this.url = url;
        }
        @Override
        protected Void doInBackground(String... params) {
            Document doc;
            Elements article;
            descData = "";
            try {
                doc = Jsoup.connect(url).get();
                article = doc.select(".uk-article");
                if(article.select("p").size()<=2) {
                    descData ="";
                }
                else {
                    for(int i = 2 ; i < article.select("p").size(); i++) {
                        descData += article.select("p").get(i).text();
                        Log.d("s",""+article.select("p").get(i).parent().attr("class"));
                    }
                }

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
    }*/
}
