package com.example.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expenses.databinding.FragmentWebviewBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class WebViewFragment : Fragment() {

    private lateinit var binding : FragmentWebviewBinding
    private val okHttpClient = OkHttpClient.Builder().build()
    private var con = MyApplication.context
    private val myPCAddress = "http://192.168.2.104:8080" //こちらだけでよい
    private val homeAddress = "https://www.google.co.jp/"
    //private val localhost = "localhost:8080"
    //.url("http://www.bluecode.jp/test/api.php") サンプル

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentWebviewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.displayText = "処理を開始してください。"
        binding.leftButtonText = "ホーム"
        binding.rightButtonText = "MyPage"
        var jResult = ""
        var result = ""

        var url = "demo"
        val webView = binding.fragmentWebViewWebView
        webView.webViewClient = WebViewClient()
        webView.loadUrl(String.format("${homeAddress}/${url}"))

        binding.fragmentWebViewGetHome.setOnClickListener {
            webView.webViewClient = WebViewClient()
            webView.loadUrl(String.format("${homeAddress}/${url}"))
        }

        binding.fragmentWebViewDisplayButton.setOnClickListener {
            webView.webViewClient = WebViewClient()
            webView.loadUrl(String.format("${myPCAddress}/${url}"))
        }
    }
//
}