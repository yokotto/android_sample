package com.example.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expenses.databinding.FragmentHttpBinding
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class HttpFragment : Fragment() {

    private lateinit var binding : FragmentHttpBinding
    private val okHttpClient = OkHttpClient.Builder().build()
    private var con = MyApplication.context
    private val myPCAddress = "http://192.168.2.104:8080" //こちらだけでよい
    //private val localhost = "localhost:8080"
    //.url("http://www.bluecode.jp/test/api.php") サンプル

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHttpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.displayText = "処理を開始してください。"
        binding.leftButtonText = "通信"
        binding.rightButtonText = "NoData"
        var jResult = ""
        var result = ""

        binding.getHtml.setOnClickListener {
            binding.progressBar.show()
            Toast.makeText(con, "通信を開始します。", Toast.LENGTH_SHORT).show()
            val request = Request.Builder()
                    //.url("http://www.bluecode.jp/test/api.php")
                    .url(myPCAddress)
                    .build()

            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.message)
                    binding.displayText = e.message
                }

                //responseの格納 responseは一度のみ変数に格納できる。(呼び出し)
                override fun onResponse(call: Call, response: Response) {
                    val responseResult = response.body?.string().toString()
                    jResult = try {
                        val jsonData = JSONObject(responseResult)
                        println(jsonData.getString("status"))
                        println(jsonData.getString("message"))
                        jsonData.getString("message")
                    } catch(e:Exception) {
                        println(e.message)
                        "JSONデータはありませんでした。"
                    }
                    result = responseResult
                    binding.displayText = "通信が完了しました。"
                    binding.rightButtonText = "表示"
                }
            })
        }

        binding.displayButton.setOnClickListener {
            if(result!="") {
                binding.displayText = String.format("通常メッセージ:\n${result}\nJsonメッセージ:\n${jResult}")
            }
        }
    }

}