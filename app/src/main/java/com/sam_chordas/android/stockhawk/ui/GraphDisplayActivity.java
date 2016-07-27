package com.sam_chordas.android.stockhawk.ui;

        import android.app.ProgressDialog;
        import android.content.Context;
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
        import org.eazegraph.lib.models.ValueLinePoint;
        import org.eazegraph.lib.models.ValueLineSeries;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

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
public class GraphDisplayActivity extends AppCompatActivity
{
    TextView company_name;
    TextView exch,cl_price;
    public ValueLineChart chart;
    TextView cname,ticker,currency;
    String companyStr;
    public ValueLineSeries series;
    String class_name;
    Context mContext;
    GraphData[] graphData=new GraphData[30];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        mContext=getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);


        exch=(TextView)findViewById(R.id.exch_name);
        cl_price=(TextView)findViewById(R.id.cl_price);
        company_name= (TextView) findViewById(R.id.item_stock_symbol);
        cname=(TextView) findViewById(R.id.name);
        currency= (TextView) findViewById(R.id.currency);
        ticker=(TextView) findViewById(R.id.ticker);

//        chart= (ValueLineChart) findViewById(R.id.cubiclinechart);
//        series = new ValueLineSeries();
//        series.setColor(0xFF56B7F1);
//
//        series.addPoint(new ValueLinePoint("Jan", 2.4f));
//        series.addPoint(new ValueLinePoint("Feb", 3.4f));
//        series.addPoint(new ValueLinePoint("Mar", .4f));
//        series.addPoint(new ValueLinePoint("Apr", 1.2f));
//        series.addPoint(new ValueLinePoint("Mai", 2.6f));
//        series.addPoint(new ValueLinePoint("Jun", 1.0f));
//        series.addPoint(new ValueLinePoint("Jul", 3.5f));
//        series.addPoint(new ValueLinePoint("Aug", 2.4f));
//        series.addPoint(new ValueLinePoint("Sep", 2.4f));
//        series.addPoint(new ValueLinePoint("Oct", 3.4f));
//        series.addPoint(new ValueLinePoint("Nov", .4f));
//        series.addPoint(new ValueLinePoint("Dec", 1.3f));
//
//        chart.addSeries(series);
//        chart.startAnimation();
        companyStr=getIntent().getStringExtra("company");
//        company_name.setText(companyStr);
        Log.v("Company Name VERBOSE",companyStr);

        new FetchDataTask().execute(companyStr);
    }


    class FetchDataTask extends AsyncTask<String,Void,GraphDetail> {
        ProgressDialog dialog = new ProgressDialog(mContext);
        ContentLoadingProgressBar progressBar = new ContentLoadingProgressBar(getApplicationContext());
//        MovieObject [] movieObjects=null;
        //     String []str=null;
        GraphDetail gd;
        @Override
        protected void onPostExecute(GraphDetail gd)
        {
            setTitle("Graphical Analysis : "+gd.getTicker().toUpperCase());
            currency.setText(gd.getCurrency());
            ticker.setText(gd.getTicker());
            cl_price.setText(gd.getPrev_price());
            exch.setText(gd.getExch_name());
            cname.setText(gd.getCompany_name());
            ValueLineChart lineChart= (ValueLineChart) findViewById(R.id.cubiclinechart);
            series = new ValueLineSeries();
            series.setColor(0xFF56B7F1);
            company_name.setText(gd.getCompany_name());
            for(int i=0;i<gd.value.size();i++)
            {
                Log.v("OnPostExecute : ","sfhl");
                series.addPoint(new ValueLinePoint(gd.date.get(i), (float) gd.value.get(i)));
            }
            lineChart.addSeries(series);
            lineChart.startAnimation();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected GraphDetail doInBackground(String... strings) {
            final String StockBaseUrl = "http://chartapi.finance.yahoo.com/instrument/1.0/";
            final String StockEndUrl = "/chartdata;type=quote;range=2m/json";
//            GraphData[] graphData = null;
            Log.v("VERBOSE BASE ", StockBaseUrl);
            Log.v("VERBOSE END URL", StockEndUrl);

            Log.v("URL : ", StockBaseUrl + strings[0] + StockEndUrl);

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String stockJSONStr = null;
            try {

//                Uri buildUri=Uri.parse(StockBaseUrl+companyStr+StockEndUrl).buildUpon();
                URL url = new URL(StockBaseUrl + strings[0] + StockEndUrl);
                Log.v("URL :  VERBOSE ", url.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    stockJSONStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    stockJSONStr = null;
                }

                Log.v("MY Data : VERBOSE", buffer.toString());
                stockJSONStr = buffer.toString();

                Log.v("MY DATA : ", "Completed ...");
                String string = stockJSONStr.substring(29);
                Log.v("SubString ", string);
                JSONObject obj1 = new JSONObject(string);
                JSONObject obj2=obj1.getJSONObject("meta");

                gd=new GraphDetail();
                gd.setExch_name(obj2.getString("Exchange-Name"));
                gd.setPrev_price(obj2.getString("previous_close_price"));
                gd.setTicker(obj2.getString("ticker"));
                Log.v("GD : ",gd.getTicker());
                gd.setCompany_name(obj2.getString("Company-Name"));
                Log.v("GD : ",gd.getCompany_name());

                gd.setCurrency(obj2.getString("currency"));
                Log.v("GD : ",gd.getCurrency());

                JSONArray myarray = obj1.getJSONArray("series");

                Log.v("MY Array :", myarray.toString());
 //               graphData = new GraphData[myarray.length()];
                for (int i = 0; i < myarray.length(); i++) {
                    JSONObject graphjson = myarray.getJSONObject(i);
                    gd.date.add(graphjson.getString("Date"));
//                    gd.graphData[i].value=(graphjson.getDouble("close"));
                    gd.value.add((float) graphjson.getDouble("close"));
//                    Log.v("My Tag" + (i + 1) + " : ", graphData[i].date + " and " + graphData[i].value);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return gd;
        }
    }

}
