package com.example.expenses

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MenuFragment : Fragment() {

    val con = MyApplication.context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)

        val strList = arrayOf(
                "RecyclerFragment"
                ,"Anim1Fragment"
                ,"Anim2Fragment"
                ,"HttpFragment"
                ,"StudentFragment")

        val titleList = ArrayList<String>()
        for(element in strList){
            titleList.add(element)
        }

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(RecyclerFragment())
        fragmentList.add(Anim1Fragment())
        fragmentList.add(Anim2Fragment())
        fragmentList.add(HttpFragment())
        fragmentList.add(StudentFragment())

        //recyclerViewの作成
        val adapter = TitleAdapter(titleList)
        val recyclerView = view.findViewById<RecyclerView>(R.id.fragment_main_recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        //インターフェースの実装
        adapter.setOnItemClickListener(object:TitleAdapter.OnItemClickListener{
            override fun onItemClickListener(view:View,position:Int,clickedText:String){
                Toast.makeText(con,"${clickedText}がタップされました", Toast.LENGTH_SHORT).show()
                val nextFragment = fragmentList[position]
                fadeout.setAnimationListener((object : Animation.AnimationListener {
                    override fun onAnimationStart(p0: Animation?) {}
                    override fun onAnimationEnd(animation:Animation){
                        childFragmentManager.beginTransaction().replace(R.id.fragment_container,nextFragment).
                        addToBackStack(null).commit()
                    }
                    override fun onAnimationRepeat(p0: Animation?) {}
                }))
                view.startAnimation(fadeout)
            }
        })

        //Viewの取得
        val anim1Button = view.findViewById<Button>(R.id.fragment_button_anim1)
        val anim2Button = view.findViewById<Button>(R.id.fragment_button_anim2)

        //各ボタンにアクションとアニメーションを設定する
        anim1Button.setOnClickListener {

            fadeout.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    childFragmentManager.beginTransaction().replace(R.id.fragment_container, Anim1Fragment()).commit()
                }
                override fun onAnimationRepeat(p0: Animation?) {}
            })
            it.startAnimation(fadeout)
        }

        anim2Button.setOnClickListener{

            fadeout.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    childFragmentManager.beginTransaction().replace(R.id.fragment_container, Anim2Fragment()).commit()
                }
                override fun onAnimationRepeat(p0: Animation?) {}
            })
            it.startAnimation(fadeout)
        }
    }
}

class TitleAdapter constructor(private var titleList:ArrayList<String>):
        RecyclerView.Adapter<TitleViewHolder>() {

    private lateinit var listener: OnItemClickListener

    //ホルダーを生成 使用するリストのフォーマットをセット
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_title, parent, false))
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val number = titleList[position]
        holder.titleText.text = number
        holder.titleText.setOnClickListener{
            listener.onItemClickListener(it,position,number)
        }
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(view: View, position: Int, clickedText: String)
    }

    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }
}

class TitleViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
    var titleText : TextView = itemView.findViewById(R.id.list_title_text)
}