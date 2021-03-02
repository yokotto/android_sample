package com.example.expenses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GraphFragment : Fragment() {

    private val scope = CoroutineScope(Dispatchers.Default)
    private val database = Room.databaseBuilder(MyApplication.context,AppDatabase::class.java,
            MyApplication.db_name).fallbackToDestructiveMigration().build()
    private var spendingList = ArrayList<SpendingEntity>()
    private var addSpendingList = ArrayList<SpendingEntity>()
    private var escapeList = ArrayList<SpendingEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //前のフラグメントから受け取ったデータがあればここへ
        //class変数に代入すれば流用可能。
        arguments?.let {
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun onViewCreated(view:View,savedInstanceState:Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.findViewById<TextView>(R.id.fragment_main_title).text = "出費グラフ"

        val spendingDao = database.spendingDao()

        //全データの取得
        scope.launch {
            spendingDao.getAll().forEach {
                spendingList.add(it)
            }
            withContext(Dispatchers.Main) {

            }
        }
    }

    companion object {}

}
