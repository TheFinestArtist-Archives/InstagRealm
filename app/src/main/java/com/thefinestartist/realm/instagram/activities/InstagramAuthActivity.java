/**
 * Copyright 2013 The Finest Artist
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thefinestartist.realm.instagram.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout_;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.thefinestartist.realm.instagram.R;
import com.thefinestartist.realm.instagram.instagram.networks.InstagramAPI;

import butterknife.Bind;
import butterknife.ButterKnife;

public class InstagramAuthActivity extends AppCompatActivity {

    public static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/?client_id=8275787745ef444c8ad78eaa89b701f3&redirect_uri=http://thefinestartist.com&response_type=token";

    @Bind(R.id.webView)
    WebView webview;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout_ swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        webview.setWebViewClient(new MyWebViewClient());
        webview.setWebChromeClient(new MyWebChromeClient());
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setAllowFileAccess(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setAppCacheEnabled(true);
        webview.getSettings().setDisplayZoomControls(false);

        webview.loadUrl(AUTH_URL);

        swipeRefreshLayout.setColorSchemeResources(R.color.insta_blue);
        swipeRefreshLayout.setEnabled(false);
    }

    public class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int progress) {
            if (progress < 100)
                swipeRefreshLayout.setRefreshing(true);

            if (progress == 100)
                swipeRefreshLayout.setRefreshing(false);
        }
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (url.contains("#access_token=")) {
                String[] splited = url.split("#access_token=");
                if (splited.length == 2) {
                    InstagramAPI.accessToken = splited[1];
                    Intent intent = new Intent(InstagramAuthActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.setVisibility(View.VISIBLE);
            final Animation fade = new AlphaAnimation(0.0f, 1.0f);
            fade.setDuration(200);
            view.startAnimation(fade);
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}