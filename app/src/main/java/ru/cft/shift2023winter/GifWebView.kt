package ru.cft.shift2023winter

import android.content.Context
import android.webkit.WebView

class GifWebView(context: Context?, path: String?) : WebView(context!!) {
    init {
        loadUrl(path!!)
    }
}