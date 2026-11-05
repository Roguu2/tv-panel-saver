package pl.szymon.tvpanel;

import android.service.dreams.DreamService;
import android.webkit.WebView;

/** Wygaszacz ekranu: pokazuje panel. Dowolny przycisk pilota go zamyka. */
public class PanelDream extends DreamService {
    private WebView web;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setScreenBright(true);
        web = Panel.create(this);
        setContentView(web);
    }

    @Override
    public void onDetachedFromWindow() {
        if (web != null) { web.destroy(); web = null; }
        super.onDetachedFromWindow();
    }
}
