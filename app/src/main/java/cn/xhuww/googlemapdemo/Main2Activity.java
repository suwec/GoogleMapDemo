package cn.xhuww.googlemapdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class Main2Activity extends AppCompatActivity {
    private String url = "http://maps.google.com/maps/api/directions/xml?origin=23.055291,113.391802" +
            "&destination=23.046604,113.397510&sensor=false&mode=walking";
            /*"https://www.google.com/maps/dir/?api=1&origin=113.26798,23.137729&destination=114.123768,22.537507";*/
    private String url2 = "http://maps.google.com/maps/api/directions/output?parameters?origin=52.31,16.71&destination=51.27,6.75&sensor=false";
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        webView = findViewById(R.id.webview);
        webView.setHorizontalScrollBarEnabled(false);// 滚动条水平不显示
        webView.setVerticalScrollBarEnabled(false); // 垂直不显示
        webView.getSettings().setJavaScriptEnabled(true);// 允许 JS的使用
        webView.getSettings().setAllowFileAccess(false); //修复file域漏洞
        webView.getSettings().setAllowFileAccessFromFileURLs(false);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);// 可以使用JS打开新窗口
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setNeedInitialFocus(false);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 解决缓存问题
        webView.setWebViewClient(new WebViewClient2(this));
        webView.setWebChromeClient(new BankWebChromeClient(this));
        webView.loadUrl(url);
    }

    class WebViewClient2 extends WebViewClient {
        private Activity activity;

        public WebViewClient2(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                String htmlText = "<html>" + "<head>错误号：" + errorCode + ";</head>"
                        + "<body>" + description + "</body></html>";
                view.loadDataWithBaseURL("about:blank", htmlText, "text/html", "utf-8",
                        null);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
            return true;
        }

    }

    class BankWebChromeClient extends WebChromeClient {
        /** 视频全屏参数 */
        private Activity mContext;
        private ProgressBar pb;

        public BankWebChromeClient() {
        }

        public BankWebChromeClient(Activity context) {
            this.mContext = context;
        }

        public BankWebChromeClient(ProgressBar pb,Activity context) {
            this.mContext = context;
            this.pb = pb;
        }

        @Override
        public View getVideoLoadingProgressView() {
            FrameLayout frameLayout = new FrameLayout(mContext);
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return frameLayout;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (pb != null) {
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                    pb.setProgress(0);
                }else {
                    if (View.GONE == pb.getVisibility()) {
                        pb.setVisibility(View.VISIBLE);
                    }
                    pb.setProgress(newProgress);
                }
            }
            if (newProgress == 100) {
                //((BaseActivity)mContext).stopProgress();
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message,
                                  String defaultValue, JsPromptResult result) {
            return super.onJsPrompt(view, url, message, defaultValue, result);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message,
                                        JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public boolean onJsTimeout() {
            return super.onJsTimeout();
        }
    }
}
