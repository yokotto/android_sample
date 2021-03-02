package com.example.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList


class RecyclerFragment : Fragment() {

    private val con = MyApplication.context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val leftAddButton = view.findViewById<Button>(R.id.fragment_recycler_buttonLeftTop)
        val leftRemoveButton = view.findViewById<Button>(R.id.fragment_recycler_buttonLeftBottom)
        val centerAddButton = view.findViewById<Button>(R.id.fragment_recycler_buttonCenterTop)
        val centerRemoveButton = view.findViewById<Button>(R.id.fragment_recycler_buttonCenterBottom)
        val rightAddButton = view.findViewById<Button>(R.id.fragment_recycler_buttonRightTop)
        val rightRemoveButton = view.findViewById<Button>(R.id.fragment_recycler_buttonRightBottom)
        val resetButton = view.findViewById<Button>(R.id.fragment_recycler_resetButton)

        val layoutBottomOnTop = view.findViewById<LinearLayout>(R.id.fragment_recycler_layoutBottomOnTop)
        val layoutBottomOnBottom = view.findViewById<LinearLayout>(R.id.fragment_recycler_layoutBottomOnBottom)

        //listAの用意
        val listATexts = arrayOf("吉田智広","吉田千秋","吉田チモシー","吉田わたげ","吉田ちもこ","吉田わたお")
        val recyclerListA = ArrayList<String>()
        listATexts.forEach{ recyclerListA.add(it) }

        //recyclerView1の生成
        val recyclerView1 = view.findViewById<RecyclerView>(R.id.fragment_recycler_recyclerViewTop)
        recyclerView1.layoutManager = LinearLayoutManager(activity)
        recyclerView1.adapter = RecyclerAdapter1(recyclerListA).apply {
            //listAイベント
            setOnItemClickListener(object : RecyclerAdapter1.OnItemClickListener {
                override fun onItemClickListener(position: Int, holder: RecyclerAdapter1.RecyclerViewHolder1) {
                    holder.mainText1.setOnClickListener {
                        Toast.makeText(con, "${position+1}番目の${holder.mainText1.text}がタップされました", Toast.LENGTH_SHORT).show()
                        val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
                        fadeout.setAnimationListener((object : Animation.AnimationListener {
                            override fun onAnimationStart(p0: Animation?) {}
                            override fun onAnimationEnd(animation: Animation) {}
                            override fun onAnimationRepeat(p0: Animation?) {}
                        }))
                        holder.mainText1.startAnimation(fadeout)
                    }
                }
            })
        }

        //listBの用意
        val texts = arrayOf("うさこ","ちびうさ","うさおうじ","うさひめ","うさたろう","うさやま","うさがわ","うさのり","ちもげ")
        val photos = arrayOf(
                R.drawable.rabbits1,R.drawable.rabbits2,R.drawable.rabbits3,R.drawable.rabbits4,
                R.drawable.rabbits5,R.drawable.rabbits6,R.drawable.rabbits7,R.drawable.rabbits8,
                R.drawable.rabbits9)
        val recyclerListB = ArrayList<RecyclerData>()
        val recyclerDataB = createRecyclerData(photos,texts)
        recyclerDataB.forEach{ recyclerListB.add(it) }

        //recyclerView2の作成
        val recyclerView2 = view.findViewById<RecyclerView>(R.id.fragment_recycler_recyclerViewBottom)
        recyclerView2.layoutManager = LinearLayoutManager(activity)
        recyclerView2.adapter = RecyclerAdapter2(recyclerListB).apply {
            //listBイベント
            setOnItemClickListener(object : RecyclerAdapter2.OnItemClickListener {
                override fun onItemClickListener(position: Int, holder: RecyclerAdapter2.RecyclerViewHolder2) {
                    //number
                    holder.number.setOnClickListener {
                        Toast.makeText(con, "${position+1}番目の${holder.number.text}がタップされました", Toast.LENGTH_SHORT).show()
                        val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
                        fadeout.setAnimationListener((object : Animation.AnimationListener {
                            override fun onAnimationStart(p0: Animation?) {}
                            override fun onAnimationEnd(animation: Animation) {}
                            override fun onAnimationRepeat(p0: Animation?) {}
                        }))
                        holder.number.startAnimation(fadeout)
                    }
                    //text
                    holder.listText.setOnClickListener {
                        Toast.makeText(con, "${position+1}番目の${holder.listText.text}がタップされました", Toast.LENGTH_SHORT).show()
                        val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
                        fadeout.setAnimationListener((object : Animation.AnimationListener {
                            override fun onAnimationStart(p0: Animation?) {}
                            override fun onAnimationEnd(animation: Animation) {}
                            override fun onAnimationRepeat(p0: Animation?) {}
                        }))
                        holder.listText.startAnimation(fadeout)
                    }
                    //button
                    holder.listButton.setOnClickListener {
                        val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
                        val expansion = AnimationUtils.loadAnimation(MyApplication.context,R.anim.expansion)
                        val newName = renameAlphabet((holder.listText.text.toString()))
                        fadeout.setAnimationListener((object : Animation.AnimationListener {
                            override fun onAnimationStart(p0: Animation?) {}
                            override fun onAnimationEnd(animation: Animation) {
                                if(newName!="") {
                                    val imageTag = holder.image.tag as Int
                                    if (newName != "") {
                                        recyclerListB.add(
                                            RecyclerData(
                                                imageTag, recyclerListB.size + 1, newName
                                            )
                                        )
                                        recyclerView2.adapter!!.notifyDataSetChanged()
                                        Toast.makeText(con, "増殖が完了しました。", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            override fun onAnimationRepeat(p0: Animation?) {}
                        }))
                        holder.listButton.startAnimation(fadeout)
                        holder.image.startAnimation(expansion)
                        if(newName!="") {
                            Toast.makeText(con, "${position + 1}番目の${holder.listText.text}を増殖開始。少々お待ちください。", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(con, "これ以上増やせません", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

        //leftAddButtonの設定
        leftAddButton.setOnClickListener {
            val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
            it.startAnimation(fadeout)
            layoutBottomOnTop.startAnimation(fadeout)
            if(recyclerListA.size>0) recyclerListA.add("同居人${recyclerListA.size+1}") else recyclerListA.add("同居人1")
            (recyclerView1.adapter as RecyclerAdapter1).notifyDataSetChanged()
        }
        //leftRemoveButtonの設定
        leftRemoveButton.setOnClickListener{
            val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
            it.startAnimation(fadeout)
            layoutBottomOnTop.startAnimation(fadeout)
            if(recyclerListA.size>0) {
                recyclerListA.removeAt(recyclerListA.lastIndex)
                (recyclerView1.adapter as RecyclerAdapter1).notifyDataSetChanged()
            } else {
                Toast.makeText(con, "削除するデータがありません。", Toast.LENGTH_SHORT).show()
            }
        }

        //centerAddButtonの設定
        centerAddButton.setOnClickListener{
            val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
            it.startAnimation(fadeout)
            layoutBottomOnTop.startAnimation(fadeout)
            layoutBottomOnBottom.startAnimation(fadeout)
            //listAの追加
            if(recyclerListA.size>0) {
                recyclerListA.add("同居人${recyclerListA.size+1}")
                (recyclerView1.adapter as RecyclerAdapter1).notifyDataSetChanged()
            } else {
                recyclerListA.add("同居人1")
                (recyclerView1.adapter as RecyclerAdapter1).notifyDataSetChanged()
            }
            //listBの追加
            if(recyclerListB.size>0) {
                recyclerListB.add(RecyclerData(R.drawable.risu, recyclerListB.size+1, "新うさぎ${recyclerListB.size+1}"))
                (recyclerView2.adapter as RecyclerAdapter2).notifyDataSetChanged()
            } else {
                recyclerListB.add(RecyclerData(R.drawable.risu, 1, "新うさぎ1"))
                (recyclerView2.adapter as RecyclerAdapter2).notifyDataSetChanged()
            }
        }
        //centerRemoveButtonの設定
        centerRemoveButton.setOnClickListener {
            val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
            it.startAnimation(fadeout)
            layoutBottomOnTop.startAnimation(fadeout)
            layoutBottomOnBottom.startAnimation(fadeout)
            if(recyclerListA.size>0 && recyclerListB.size>0) {
                recyclerListA.removeAt(recyclerListA.lastIndex)
                recyclerListB.removeAt(recyclerListB.lastIndex)
                (recyclerView1.adapter as RecyclerAdapter1).notifyDataSetChanged()
                (recyclerView2.adapter as RecyclerAdapter2).notifyDataSetChanged()
            } else {
                Toast.makeText(con, "削除するデータがありません。", Toast.LENGTH_SHORT).show()
            }
        }
        //rightAddButtonの設定
        rightAddButton.setOnClickListener {
            val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
            it.startAnimation(fadeout)
            layoutBottomOnBottom.startAnimation(fadeout)
            if(recyclerListB.size>0) recyclerListB.add(RecyclerData(R.drawable.risu, recyclerListB.size+1, "新うさぎ${recyclerListB.size+1}"))
            else recyclerListB.add(RecyclerData(R.drawable.risu, 1, "新うさぎ1"))
            (recyclerView2.adapter as RecyclerAdapter2).notifyDataSetChanged()
        }
        //rightRemoveButtonの設定
        rightRemoveButton.setOnClickListener{
            val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
            it.startAnimation(fadeout)
            layoutBottomOnBottom.startAnimation(fadeout)
            if(recyclerListB.size>0) {
                recyclerListB.removeAt(recyclerListB.lastIndex)
                (recyclerView2.adapter as RecyclerAdapter2).notifyDataSetChanged()
            } else {
                Toast.makeText(con, "削除するデータがありません。", Toast.LENGTH_SHORT).show()
            }
        }
        //resetButtonの設定
        resetButton.setOnClickListener{
            val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
            it.startAnimation(fadeout)
            recyclerListA.clear()
            recyclerListB.clear()
            listATexts.forEach{ aData -> recyclerListA.add(aData) }
            recyclerDataB.forEach{ bData -> recyclerListB.add(bData) }
            (recyclerView1.adapter as RecyclerAdapter1).notifyDataSetChanged()
            (recyclerView2.adapter as RecyclerAdapter2).notifyDataSetChanged()
        }
    }

    //listBデータの生成
    private fun createRecyclerData(imageArray:Array<Int>,textArray:Array<String>) : ArrayList<RecyclerData> {
        val recyclerData = ArrayList<RecyclerData>()
        for(i in imageArray.indices) recyclerData.add(RecyclerData(imageArray[i],i+1,textArray[i]))
        return recyclerData
    }
    
    //増殖時名称変更
    private fun renameAlphabet(name:String) : String {
        val newName: String
        val alphabetArray = arrayOf("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z")
        val alphabet = name.substring(name.lastIndex)

        //増やしすぎの場合は変わらない。
        if(alphabet == alphabetArray[alphabetArray.lastIndex]) {
            Toast.makeText(con, "これ以上増やせません", Toast.LENGTH_SHORT).show()
            return ""
        }

        newName = if(alphabetArray.contains(alphabet)){
            name.replace(alphabet,alphabetArray[alphabetArray.indexOf(alphabet)+1])
        } else {
            name + "A"
        }
        return newName
    }

}

//リストA Adapter
class RecyclerAdapter1 constructor(private var recyclerList: ArrayList<String>):
        RecyclerView.Adapter<RecyclerAdapter1.RecyclerViewHolder1>() {

    private lateinit var listener: OnItemClickListener

    //ホルダーを生成 使用するリストのフォーマットをセット
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder1 {
        return RecyclerViewHolder1(LayoutInflater.from(parent.context).inflate(R.layout.list_recycler1, parent, false))
    }
    override fun onBindViewHolder(holder: RecyclerViewHolder1, position: Int) {
        val listView = recyclerList[position]
        holder.mainText1.text = listView//このholderはテキスト１つのみ
        listener.onItemClickListener(position,holder)
    }
    override fun getItemCount(): Int { return recyclerList.size }
    //インターフェースの作成
    interface OnItemClickListener{ fun onItemClickListener(position:Int,holder:RecyclerViewHolder1) }
    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){ this.listener = listener }

    class RecyclerViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mainText1 : TextView = itemView.findViewById(R.id.list_recycler1_text)
    }
}

//リストB Adapter
class RecyclerAdapter2 constructor(private var recyclerList: ArrayList<RecyclerData>):
        RecyclerView.Adapter<RecyclerAdapter2.RecyclerViewHolder2>() {

    private lateinit var listener: OnItemClickListener

    //ホルダーを生成 使用するリストのフォーマットをセット
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder2 {
        return RecyclerViewHolder2(LayoutInflater.from(parent.context).inflate(R.layout.list_recycler2, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder2, position: Int) {
        //holder内のViewにセッティング クリックリスナーは実装後にセッティングする。
        val listView = recyclerList[position]
        holder.image.setImageResource(listView.image)
        holder.image.tag = listView.image
        //val imageView = holder.image
        holder.number.text = listView.number.toString()
        holder.listText.text = listView.listText
        //listener.onItemClickListener(position,holder.image,holder.listText,holder.number,holder.listButton)
        listener.onItemClickListener(position,holder)

    }

    //リストサイズの取得
    override fun getItemCount(): Int { return recyclerList.size }
    //インターフェースの作成
    interface OnItemClickListener{
        //fun onItemClickListener(position:Int,positionImage:View,positionNumber:View, positionText:View,positionButton:View)
        fun onItemClickListener(position:Int,holder:RecyclerViewHolder2)
    }
    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){ this.listener = listener }

    class RecyclerViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView){
        var image : ImageView = itemView.findViewById((R.id.list_recycler2_imageButton))
        var number : TextView = itemView.findViewById(R.id.list_recycler2_number)
        var listText : TextView = itemView.findViewById(R.id.list_recycler2_text)
        var listButton : Button = itemView.findViewById(R.id.list_recycler2_button)
    }
}

