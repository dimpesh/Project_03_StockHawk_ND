package com.sam_chordas.android.stockhawk.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.GraphDisplayActivity;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

/**
 * Created by DIMPESH : ${month}
 */

/*
public class WidgetProvider extends AppWidgetProvider
{

    String tag=WidgetProvider.class.getSimpleName();
    
    // Some variables to use in code

    public static final String ACTION="com.sam_chordas.android.stockhawk.widget.WidgetProvider.INTENT_ACTION";
    public static final String SYMBOL = "com.sam_chordas.android.stockhawk.widget.WidgetProvider.EXTRA_SYMBOL";
    public static final String NAME = "com.sam_chordas.android.stockhawk.widget.WidgetProvider.EXTRA_NAME";
    public static final String CURRENCY = "com.sam_chordas.android.stockhawk.widget.WidgetProvider.EXTRA_CURRENCY";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


//        final int n=appWidgetIds.length;
//        for(int i=0;i<n;++i)
//        {
//            RemoteViews remoteViews=updateWidgetListView(context,appWidgetIds[i]);
//            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
//        }
//
//        super.onUpdate(context, appWidgetManager, appWidgetIds);


        // For single view
        String description="New Widget";
        for(int appWidgetId : appWidgetIds)
        {
            int layoutId=R.layout.widget_provider_layout;
            RemoteViews views= new RemoteViews(context.getPackageName(),layoutId);

            // Add the data to RemoteViews
            views.setTextViewText(R.id.widget_list,description);


            // Creating intent to launch MainActivity..
            Intent launchIntent=new Intent(context, MyStocksActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);


        }

    }

//    private RemoteViews updateWidgetListView(Context context, int appWidgetId)
//    {
//        // Layout to show on Widget..
//        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget_provider_layout);
//
//        // We need remoteview service to provide adapter
//        Intent intent=new Intent(context,WidgetService.class);
//        // passing id to remoteview service
//
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
//        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//        // setting adapter
//        remoteViews.setRemoteAdapter(appWidgetId,R.id.listViewWidget,intent);
//
//        // empty view in case no data available
//        remoteViews.setEmptyView(R.id.listViewWidget,R.id.emptyWidgetView);
//
//        return remoteViews;
//
//    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//        AppWidgetManager manager = AppWidgetManager.getInstance(context);
//        if(intent.getAction().equals(ACTION)){
//
//
////            String symbol =
////            String name = intent.getStringExtra(EXTRA_NAME);
////            String currency = intent.getStringExtra(EXTRA_CURRENCY);
////            Intent showHistoricalData = new Intent(context, StockDetails.class);
////            showHistoricalData.putExtra("symbol_name",intent.getStringExtra(SYMBOL));
////            showHistoricalData.putExtra("name", name);
////            showHistoricalData.putExtra("currency", currency);
////            showHistoricalData.putExtra("lasttradedate", lasttradedate);
////            showHistoricalData.putExtra("daylow", daylow);
////            showHistoricalData.putExtra("dayhigh", dayhigh);
////            showHistoricalData.putExtra("yearlow", yearlow);
////            showHistoricalData.putExtra("yearhigh", yearhigh);
////            showHistoricalData.putExtra("earningsshare", earningsshare);
////            showHistoricalData.putExtra("marketcaptalization", marketcapitalization);
////            showHistoricalData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            context.startActivity(showHistoricalData);
//        }
//
//        super.onReceive(context, intent);
//    }
}



*/

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Created by DIMPESH : ${month}
 */
public class WidgetProvider extends AppWidgetProvider
{
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int widgetId : appWidgetIds) {
            RemoteViews mViews = initViews(context, appWidgetManager, widgetId);
            // Create an Intent to launch MainActivity
            Intent intent = new Intent(context, MyStocksActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            mViews.setOnClickPendingIntent(R.id.widget, pendingIntent);


            // Set up the collection
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                setRemoteAdapter(context, mViews);
            } else {
                setRemoteAdapterV11(context, mViews);
            }
            boolean useDetailActivity = context.getResources()
                    .getBoolean(R.bool.use_detail_activity);
            Intent clickIntentTemplate = useDetailActivity
                    ? new Intent(context, GraphDisplayActivity.class)
                    : new Intent(context, MyStocksActivity.class);
            PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mViews.setPendingIntentTemplate(R.id.widget_list, clickPendingIntentTemplate);
            mViews.setEmptyView(R.id.widget_list, R.id.widget_empty);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetIds, mViews);

//              Earlier for this
//            appWidgetManager.updateAppWidget(widgetId,mViews);
//            Intent intent = new Intent(context, GraphDisplayActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//            mViews.setOnClickPendingIntent(R.id.widget, pendingIntent);


            /*
        // setting collection for Specific Version

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            {
                setRemo
            }

        }
        */
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }


    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private RemoteViews initViews(Context context,AppWidgetManager widgetManager,int widgetId)
    {
        RemoteViews mView=new RemoteViews(context.getPackageName(),R.layout.widget_detail);
        Intent intent=new Intent(context,WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        mView.setRemoteAdapter(widgetId,R.id.widgetCollectionList,intent);

        return mView;

    }



    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list,
                new Intent(context, WidgetService.class));
    }

    /**
     * Sets the remote adapter used to fill in the list items
     *
     * @param views RemoteViews to set the RemoteAdapter
     */
    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.widget_list,
                new Intent(context, WidgetService.class));
    }
}
