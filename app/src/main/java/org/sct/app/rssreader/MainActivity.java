package org.sct.app.rssreader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.sct.app.rss.MediaThumbnail;
import org.sct.app.rss.RSSFeed;
import org.sct.app.rss.RSSItem;
import org.sct.app.rss.RSSReader;
import org.sct.app.rss.RSSReaderException;

import java.util.List;


public class MainActivity extends Activity {

    public List<RSSItem> mRssItems;

    public ListView lv_rsslist = null;
    public RssListAdapter mAdapter = null;
    public Handler mHandler = null;
    public TextView tv_source_title = null;

    public String mSourceTitle = "知乎每日精选";
    public String mSourceUri = "http://www.zhihu.com/rss";

    public View mHeaderView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RSSReader reader = new RSSReader();
                try {
                    RSSFeed feed = reader.load(mSourceUri);
                    mRssItems = feed.getItems();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (RSSReaderException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        findView();
        initView();

    }

    public void findView() {
        lv_rsslist = (ListView) findViewById(R.id.lv_rsslist);
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.headerview, null);
        tv_source_title = (TextView) mHeaderView.findViewById(R.id.tv_source_title);
    }
    public void initView() {
        mAdapter = new RssListAdapter(this, LayoutInflater.from(this));
        lv_rsslist.setAdapter(mAdapter);
        lv_rsslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, ContentActivity.class);
                intent.putExtra("title", mRssItems.get(i-1).getTitle());
                intent.putExtra("content", mRssItems.get(i-1).getDescription());
                startActivity(intent);
            }
        });
        lv_rsslist.addHeaderView(mHeaderView);
        tv_source_title.setText(mSourceTitle);
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

    public class RssListAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;
        private Context mContext = null;

        public RssListAdapter(Context context, LayoutInflater inflater) {
            mContext = context;
            mInflater = inflater;
        }
        @Override
        public int getCount() {
            return mRssItems == null ? 0 : mRssItems.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parentView) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.item_rowdata, null);
                holder = new ViewHolder();
                holder.iv_image = (ImageView) convertView.findViewById(R.id.iv_image);
                holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            RSSItem item = mRssItems.get(position);
            holder.tv_description.setText(Html.fromHtml(item.getDescription()));
            holder.tv_title.setText(item.getTitle());
            List<MediaThumbnail> list = item.getThumbnails();
            if (list != null && list.size() > 0) {
                MediaThumbnail t = list.get(0);
                holder.iv_image.setImageURI(t.getUrl());
            }

            return convertView;
        }

        public  class ViewHolder {
            ImageView iv_image;
            TextView tv_title;
            TextView tv_description;
        }
    }
}
