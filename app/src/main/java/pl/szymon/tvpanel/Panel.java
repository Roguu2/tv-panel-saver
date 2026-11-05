package pl.szymon.tvpanel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/** Wspolny kod: WebView z panelem + ponawianie, gdy Mac jest offline. */
final class Panel {
    static final String PREFS = "panel";

    static String url(Context c) {
        SharedPreferences p = c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return p.getString("url", c.getString(R.string.default_url));
    }

    static void saveUrl(Context c, String url) {
        c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putString("url", url).apply();
    }

    @SuppressLint("SetJavaScriptEnabled")
    static WebView create(Context c) {
        final WebView w = new WebView(c);
        w.setBackgroundColor(Color.BLACK);
        w.setFocusable(false);
        WebSettings s = w.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        final String url = url(c);
        final Handler h = new Handler(Looper.getMainLooper());
        w.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView v, WebResourceRequest req, WebResourceError err) {
                if (req.isForMainFrame()) {
                    v.loadData("<body style='background:#000;color:#777;font:24px sans-serif;display:flex;align-items:center;justify-content:center;height:90vh'>"
                            + "Panel niedostępny (" + url + ") – ponawiam za 30 s</body>", "text/html; charset=utf-8", null);
                    h.postDelayed(() -> v.loadUrl(url), 30_000);
                }
            }
        });
        w.loadUrl(url);
        return w;
    }

    private Panel() {}
}
