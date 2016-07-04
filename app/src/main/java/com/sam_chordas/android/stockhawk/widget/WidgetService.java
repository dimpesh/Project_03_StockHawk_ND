package com.sam_chordas.android.stockhawk.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

@SuppressLint("NewApi")
public class WidgetService extends RemoteViewsService {

    public final String LOG_TAG=WidgetService.class.getSimpleName();

    private static final String [] STOCK_COLUMNS={
        QuoteColumns._ID,
        QuoteColumns.SYMBOL,
        QuoteColumns.BIDPRICE,
        QuoteColumns.PERCENT_CHANGE
    };


    // Index mentioned here must match to the Projection
    static final int INDEX_STOCK_ID = 0;
    static final int INDEX_STOCK_SYMBOL = 1;
    static final int INDEX_STOCK_BIDPRICE = 2;
    static final int INDEX_STOCK_PERCENT_CHANGE = 3;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {

        return new RemoteViewsFactory() {

            private Cursor data=null;

            @Override
            public void onCreate() {
                // Nothing to do here....
            }

            @Override
            public void onDataSetChanged() {
                if(data!=null)
                {
                    data.close();
                }

                final long identityToken = Binder.clearCallingIdentity();

                try {
                    data = WidgetService.this.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                            new String[]{QuoteColumns.SYMBOL,QuoteColumns.BIDPRICE,
                                    QuoteColumns.PERCENT_CHANGE,
                                    QuoteColumns.CHANGE,}, QuoteColumns.ISCURRENT + " = ?",
                            new String[]{"1"}, null);

                    data.moveToFirst();
                    Binder.restoreCallingIdentity(identityToken);
                }
                catch(Exception e)
                {
                    Log.v("Widget Gen. Exception :",e.getMessage());
                }
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }

            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }


            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        android.R.layout.simple_list_item_1);

                String symbol = data.getString(data.getColumnIndex(QuoteColumns.SYMBOL));
                String bidPrice = data.getString(data.getColumnIndex(QuoteColumns.BIDPRICE));
                String percentChange = data.getString(data.getColumnIndex(QuoteColumns.PERCENT_CHANGE));

                views.setTextViewText(android.R.id.text1,symbol+"\t\t"+percentChange+"\t\t"+bidPrice);

                final Intent fillInIntent = new Intent();
                fillInIntent.putExtra("symbol", data.getString(data.getColumnIndex(QuoteColumns.SYMBOL)));
                views.setOnClickFillInIntent(android.R.id.text1, fillInIntent);


                return views;
            }


            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(INDEX_STOCK_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
