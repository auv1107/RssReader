package org.sct.app.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ContentActivity extends Activity {
    private TextView tv_title = null;
    private TextView tv_content = null;
    private String title = null;
    private String content = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        findView();
        initView();
    }

    public void initView() {
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        tv_title.setText(title);
        tv_content.setText(Html.fromHtml(content));
    }

    public void findView() {
        tv_title = (TextView) findViewById(R.id.title);
        tv_content = (TextView) findViewById(R.id.content);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
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
