package com.sam_chordas.android.stockhawk.ui;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLineSeries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by DIMPESH : ${month}
 */
public class GraphActivity extends AppCompatActivity
{
    TextView company_name;
    ValueLineChart chart;
    String companyStr;
    ValueLineSeries series;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);


        company_name= (TextView) findViewById(R.id.item_stock_symbol);
        chart= (ValueLineChart) findViewById(R.id.cubiclinechart);
        series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);
        companyStr=getIntent().getStringExtra("company");
        Log.v("Company Name ",companyStr);

    }


//    class FetchDataTask extends AsyncTask<String,Void,String>
//    {
//        ProgressDialog dialog=new ProgressDialog(getApplicationContext());
//        ContentLoadingProgressBar progressBar=new ContentLoadingProgressBar(getApplicationContext());
////        MovieObject [] movieObjects=null;
//        //     String []str=null;
//
//        @Override
//        protected void onPostExecute(MovieObject[] str) {
//            if(str!=null)
//                movieAdapter.clear();
//
//            for (MovieObject m : str) {
//                movieAdapter.add(m);
//            }
//
//            if(dialog.isShowing()==true)
//            {
//                dialog.dismiss();
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog.setMessage("Loading...");
//            dialog.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            final String StockBaseUrl="http://chartapi.finance.yahoo.com/instrument/1.0/";
//            final String StockEndUrl="/chartdata;type=quote;range=5y/json";
//
////            String api_key=BuildConfig.MyTmdbApiKey;
////            String YEAR_PARAM="primary_release_year";
////            String year="2015";
////            String type=strings[0];
////            String TYPE_VOTE_COUNT="vote_count.gte";
////            String voteCount="50";
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            String movieJSONStr = null;
//            try {
//
////                Uri buildUri=Uri.parse(StockBaseUrl+companyStr+StockEndUrl).buildUpon();
//                URL url=new URL(StockBaseUrl+companyStr+StockEndUrl);
//                Log.v("URL",url.toString());
//
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    movieJSONStr = null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line + "\n");
//                }
//                if (buffer.length() == 0) {
//                    movieJSONStr = null;
//                }
//                movieJSONStr = buffer.toString();
//                String title="title";
//                String vote_average="vote_avergae";
//                String overview="overview";
//                JSONObject movieJSONObject=new JSONObject(movieJSONStr);
//                JSONArray movieJSONArray=movieJSONObject.optJSONArray("results");
//                movieObjects=new MovieObject[movieJSONArray.length()];
//                for(int i=0;i<movieJSONArray.length();i++)
//                {
//                    movieObjects[i]=new MovieObject();
//                    JSONObject jsonObject=movieJSONArray.getJSONObject(i);
//                    movieObjects[i].title=jsonObject.optString("title").toString();
//                    movieObjects[i].overview=jsonObject.optString("overview").toString();
//                    movieObjects[i].poster_path=jsonObject.optString("poster_path").toString();
//                    movieObjects[i].release_date = jsonObject.getString("release_date").toString();
//                    movieObjects[i].vote_average=jsonObject.getString("vote_average").toString();
//                    movieObjects[i].backdrop_path=jsonObject.getString("backdrop_path").toString();
//                    movieObjects[i].id=jsonObject.getString("id").toString();
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return movieObjects;
//
//
//        }
//    }




}
