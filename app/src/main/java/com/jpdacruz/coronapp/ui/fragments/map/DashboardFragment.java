package com.jpdacruz.coronapp.ui.fragments.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
    private FusedLocationProviderClient fusedLocationClient;

    //private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_map, container, false);

        myWebView = root.findViewById(R.id.webview);

        url = "https://bing.com/covid/local/argentina?ref=opal&PC=OPALAND";
        //url ="https://bing.com/covid/local/argentina?fbclid=IwAR1sWNx01rBLcO86-Iwt1ao5BayH7ShzzbAz6M-wsBNzdQtma2PEAZac2v8";
        //url = "https://www.google.com/maps/d/u/0/viewer?mid=1S0vCi3BA-7DOCS13MomK7KebkPsvYl8C&ll=-10.575356084459642%2C-54.67684365000002&z=3";

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        myWebView.loadUrl(url);

        return root;
    }
}
