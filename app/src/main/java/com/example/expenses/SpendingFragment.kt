package com.example.expenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SpendingFragment : Fragment() {

    companion object {}

    private val scope = CoroutineScope(Dispatchers.Default)
    private val database = Room.databaseBuilder(MyApplication.context,AppDatabase::class.java,
            MyApplication.db_name).fallbackToDestructiveMigration().build()
    private val spendingList = ArrayList<SpendingEntity>()
    private val mSpendingList = ArrayList<SpendingEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_spending, container, false)
    }

    override fun onViewCreated(view:View,savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fadeout = AnimationUtils.loadAnimation(MyApplication.context,R.anim.fead_out)
        val mainActivity = MainActivity()
        mainActivity.findViewById<TextView>(R.id.fragment_main_title).text = "出費"
        val recyclerView : RecyclerView = view.findViewById(R.id.fragment_spending_recycler_view)

        val editButton = view.findViewById<Button>(R.id.fragment_spending_editButton)
        val itemButton = view.findViewById<Button>(R.id.fragment_spending_itemButton)
        val graphButton = view.findViewById<Button>(R.id.fragment_spending_graphButton)

        val spendingDao = database.spendingDao()

        spendingList.add(SpendingEntity(0,"サンプル費",1000,1))
        //全データの取得
        scope.launch {
            spendingDao.getAll().forEach {
                spendingList.add(it)
            }
            withContext(Dispatchers.Main) {
                //recyclerViewのセット
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(activity)
                // Adapter生成してRecyclerViewに値をセット
                recyclerView.adapter = SpendingAdapter(spendingList)
            }
        }

        //ボタンアクションのセット
        editButton.setOnClickListener {
            fadeout.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    fragmentManager!!.beginTransaction().replace(R.id.fragment_container, EditFragment()).commit()
                }
                override fun onAnimationRepeat(p0: Animation?) {}
            })
            it.startAnimation(fadeout)
        }
        itemButton.setOnClickListener {
            fadeout.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    fragmentManager!!.beginTransaction().replace(R.id.fragment_container, ItemFragment()).commit()
                }
                override fun onAnimationRepeat(p0: Animation?) {}
            })
            it.startAnimation(fadeout)
        }
        graphButton.setOnClickListener {
            fadeout.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    fragmentManager!!.beginTransaction().replace(R.id.fragment_container, GraphFragment()).commit()
                }
                override fun onAnimationRepeat(p0: Animation?) {}
            })
            it.startAnimation(fadeout)
        }
    }

    inner class SpendingAdapter constructor(private var spendingList:List<SpendingEntity>):
            RecyclerView.Adapter<SpendingAdapter.SpendingViewHolder>(){
        //ホルダーを生成 使用するリストのフォーマットをセット
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpendingViewHolder {
            return SpendingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_spending, parent, false))
        }
        override fun onBindViewHolder(holder : SpendingViewHolder, position: Int) {
            holder.item.text = spendingList[position].entries
            holder.amount.text = spendingList[position].amount.toString()
        }
        inner class SpendingViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
            var item : TextView = itemView.findViewById(R.id.simple_list_item)
            var amount : TextView = itemView.findViewById(R.id.simple_list_amount)
        }
        override fun getItemCount(): Int {
            return spendingList.size
        }
    }

}

