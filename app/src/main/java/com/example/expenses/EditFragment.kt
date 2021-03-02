package com.example.expenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditFragment : Fragment() {

    companion object {}

    private val scope = CoroutineScope(Dispatchers.Default)
    private val database = Room.databaseBuilder(MyApplication.context,AppDatabase::class.java,
            MyApplication.db_name).fallbackToDestructiveMigration().build()
    private val mSpendingList = ArrayList<SpendingEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view:View,savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.findViewById<TextView>(R.id.fragment_main_title).text = "出費項目追加"
        val fadeout = AnimationUtils.loadAnimation(MyApplication.context,R.anim.fead_out)
        val recyclerView: RecyclerView = view.findViewById(R.id.fragment_edit_recycler_view)

        val confirmButton = view.findViewById<Button>(R.id.fragment_edit_confirmButton)
        val cancelButton = view.findViewById<Button>(R.id.fragment_edit_cancelButton)

        val spendingDao = database.spendingDao()

        val itemList = arrayListOf(
                "食費","住居費","電気代",
                "ガス代","水道費","保険料",
                "食費","日用品費", "被服費",
                "美容費","交際費","教育費",
                "医療費","特別日","雑費",
                "その他","固定費","変動費",
                "その他生活費","特別費")

        val entriesList = ArrayList<String>()

        //リストにセット
        itemList.forEach{
            mSpendingList.add(SpendingEntity(0,it,0,1))
        }
        //全データの取得
        scope.launch {
            spendingDao.getAll().forEach {
                entriesList.add(it.entries);
            }
            withContext(Dispatchers.Main) {
                //recyclerViewのセット
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(activity)
                // Adapter生成してRecyclerViewに値をセット
                recyclerView.adapter = SpendingAdapter(this@EditFragment.mSpendingList)
            }
        }

        //ボタンアクションのセット
        confirmButton.setOnClickListener {
            fadeout.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
                override fun onAnimationRepeat(p0: Animation?) {}
            })
            it.startAnimation(fadeout)
        }
        cancelButton.setOnClickListener {
            fadeout.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {}
                override fun onAnimationRepeat(p0: Animation?) {}
            })
            it.startAnimation(fadeout)
        }
    }

    inner class SpendingAdapter constructor(private var spendingList:List<SpendingEntity>):
            RecyclerView.Adapter<EditFragment.SpendingAdapter.ItemViewHolder>(){
        //ホルダーを生成 使用するリストのフォーマットをセット
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            return ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
        }
        override fun onBindViewHolder(holder : ItemViewHolder, position: Int) {
            holder.item.text = spendingList[position].entries
            val check = spendingList[position].display_flag
            if(check==1) {
                holder.displayFlag.isChecked
            }
        }
        inner class ItemViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
            var item : TextView = itemView.findViewById(R.id.list_item_textItem)
            var displayFlag : CheckBox = itemView.findViewById(R.id.list_item_checkBox)
        }

        override fun getItemCount(): Int {
            return spendingList.size
        }
    }

}

