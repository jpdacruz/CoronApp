package com.jpdacruz.coronapp.ui.fragments.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.jpdacruz.coronapp.R;
import com.jpdacruz.coronapp.db.constantes.Constantes;

public class DashboardFragment extends Fragment {

    private String url;
    WebView myWebView;
    private ProgressBar progressBar;

    //private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);

        myWebView = root.findViewById(R.id.webview);
        progressBar = root.findViewById(R.id.progressBarMap);

        url = "https://bing.com/covid/local/argentina?ref=opal&PC=OPALAND";

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.loadUrl(url);
        progressBar.setVisibility(View.GONE);

        return root;
    }
}
