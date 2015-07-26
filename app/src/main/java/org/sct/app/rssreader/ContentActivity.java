package org.sct.app.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class ContentActivity extends Activity {
    private TextView tv_title = null;
    private TextView tv_content = null;
    private String title = null;
    private String content = null;
    private WebView mWebView = null;
    private Button btn_exchange = null;
    private int mode = MODE_TEXT_ONLY;

    public static int MODE_TEXT_ONLY = 0;
    public static int MODE_WEBVIEW = 1;

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
        mWebView.setContentDescription(Html.fromHtml(content));
        //mWebView.loadData(Html.fromHtml(content).toString(), "text/html", null);
        mWebView.loadDataWithBaseURL(null, content, "text/html","utf-8", null);
        btn_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode == MODE_TEXT_ONLY) {
                    mode = MODE_WEBVIEW;
                    mWebView.setVisibility(View.VISIBLE);
                    tv_content.setVisibility(View.GONE);
                } else {
                    mode = MODE_TEXT_ONLY;
                    mWebView.setVisibility(View.GONE);
                    tv_content.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void findView() {
        tv_title = (TextView) findViewById(R.id.title);
        tv_content = (TextView) findViewById(R.id.content);
        mWebView = (WebView) findViewById(R.id.webView);
        btn_exchange = (Button) findViewById(R.id.btn_exchange);
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
