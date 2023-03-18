// FILE         : Food.java
// PROJECT      : Party Planner
// PROGRAMMER(s): Beunard Lecaj, Jainish Patel, Raj Dudhat, Yujung Park
// FIRST VERSION: 2023-03-10
// DESCRIPTION  : This file is AdapterView class file.
// Food is AdapterView for RSSFeed, and gets RSS feeds and displays them.
// REFERENCE    : Week6_WorkWithFIles

package com.example.partyplanner;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Food extends Activity implements AdapterView.OnItemClickListener
{

    private Button myGet = null;
    private Button myParse = null;

    final String FILENAME = "recipe_feed.xml";
    RSSFeed feed = null;

    TextView titleTextView = null;
    ListView itemListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        titleTextView = (TextView)findViewById(R.id.titleTextView);
        itemListView = (ListView)findViewById(R.id.itemsListView);


        myGet = (Button)findViewById(R.id.MyGet);
        myGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFeed().execute(new String[]{"https://houseandhome.com/category/food/feed/"});
            }
        });

    }

    class DownloadFeed extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {

            try{
                // get the URL
                URL url = new URL(params[0]);

                // get the input stream
                InputStream in = url.openStream();

                // get the output stream
                Context context = Food.this;
                FileOutputStream out =
                        context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

                // read input and write output
                byte[] buffer = new byte[1024];
                int bytesRead = in.read(buffer);
                StringBuffer sb = new StringBuffer();
                while (bytesRead != -1)
                {
                    sb.append(new String(buffer, "UTF-8"));
                    out.write(buffer, 0, bytesRead);
                    bytesRead = in.read(buffer);
                }
                Log.i("Food", sb.toString());
                out.close();
                in.close();
            }
            catch (IOException e) {
                Log.e("Recipe RSSFeed reader", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d("Recipe RSSFeed reader", "RSS Feed downloaded: " + new Date());
            new ReadFeed().execute();
        }
    }

    class ReadFeed extends AsyncTask<Void, Void, RSSFeed> {

        @Override
        protected RSSFeed doInBackground(Void... params) {
            try {
                // get the XML reader
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                XMLReader xmlreader = parser.getXMLReader();

                // set content handler
                RSSHandler theRssHandler = new RSSHandler();
                xmlreader.setContentHandler(theRssHandler);

                // read the file from internal storage
                FileInputStream in = openFileInput(FILENAME);

                // parse the data
                InputSource is = new InputSource(in);
                xmlreader.parse(is);

                // return the feed
                RSSFeed feed = theRssHandler.getFeed();
                return feed;
            }
            catch (Exception e) {
                Log.e("Recipe RSSFeed reader", e.toString());
                return null;
            }
        }

        // This is executed after the feed has been read
        @Override
        protected void onPostExecute(RSSFeed result) {
            Log.d("Recipe RSSFeed reader", "Feed read: " + new Date());

            // update the display for the activity
            Food.this.feed = result;
            Food.this.updateDisplay();
        }
    }

    public void updateDisplay(){
        if (feed == null) {
            titleTextView.setText("Unable to get RSS feed");
            return;
        }

        // set the title for the feed
        titleTextView.setText(feed.getTitle());

        // get the items for the feed
        ArrayList<RSSItem> items = feed.getAllItems();

        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data =
                new ArrayList<HashMap<String, String>>();
        for (RSSItem item : items) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("date", item.getPubDateFormatted());
            map.put("title", item.getTitle());
            data.add(map);
        }

        // create the resource, from, and to variables
        int resource = R.layout.listview_item;
        String[] from = {"date", "title"};
        int[] to = {R.id.pubDateTextView, R.id.titleTextView};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);
        itemListView.setOnItemClickListener(this);
        itemListView.setAdapter(adapter);

        Log.d("Recipe RSSFeed reader", "Feed displayed: " + new Date());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {


        // Toast.makeText(this, "In OnClick", Toast.LENGTH_SHORT).show();
        // get the item at the specified position
        RSSItem item = feed.getItem(position);

        // create an intent
        Intent intent = new Intent(this, SingleItem.class);

        intent.putExtra("date", item.getPubDate());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("description", item.getDescription());
        intent.putExtra("link", item.getLink());

        this.startActivity(intent);

    }
}
