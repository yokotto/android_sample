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
import androidx.room.Room
import com.example.expenses.databinding.FragmentStudentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class StudentFragment : Fragment() {

    private lateinit var binding : FragmentStudentBinding
    private lateinit var mStudentList : ArrayList<StudentEntity>
    private var con = MyApplication.context
    private val scope = CoroutineScope(Dispatchers.Default)
    private val database = Room.databaseBuilder(MyApplication.context, AppDatabase::class.java,
            MyApplication.db_name).fallbackToDestructiveMigration().build()
    private var studentNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentStudentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.fragment_room_recyclerView)

        val studentDao = database.studentDao()
        mStudentList = ArrayList()
        val activity = activity
        val updateButton = activity?.findViewById<Button>(R.id.activity_main_returnButton)
        updateButton?.text = "更新"
        val resetButton = activity?.findViewById<Button>(R.id.activity_main_resetButton)
        resetButton?.text = "消去"
        resetButton?.visibility = View.VISIBLE

        val studentView = binding.fragmentRoomRecyclerView
        studentView.adapter = StudentAdapter(mStudentList).apply {
            setOnItemClickListener(object : StudentAdapter.OnItemClickListener {
                override fun onItemClickListener(position: Int, holder: StudentAdapter.StudentViewHolder) {
                    holder.firstName.setOnClickListener {
                        Toast.makeText(con, "生徒番号${holder.number.text}:${holder.firstName.text}${holder.lastName.text}", Toast.LENGTH_SHORT).show()
                        val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
                        val studentData = StudentData().castToStudentData(mStudentList[position])
                        val arg = Bundle()
                        arg.putSerializable("studentData",studentData)
                        val nextFragment = StudentDetailFragment()
                        nextFragment.arguments = arg
                        fadeout.setAnimationListener((object : Animation.AnimationListener {
                            override fun onAnimationStart(p0: Animation?) {}
                            override fun onAnimationEnd(animation: Animation) {
                                activity!!.supportFragmentManager.beginTransaction().
                                replace(R.id.fragment_container,nextFragment).
                                addToBackStack(null).commit()
                            }
                            override fun onAnimationRepeat(p0: Animation?) {}
                        }))
                        holder.layoutAll.startAnimation(fadeout)
                    }
                    holder.lastName.setOnClickListener {
                        Toast.makeText(con, "生徒番号${holder.number.text}:${holder.firstName.text}${holder.lastName.text}", Toast.LENGTH_SHORT).show()
                        val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
                        val studentData = StudentData().castToStudentData(mStudentList[position])
                        val arg = Bundle()
                        arg.putSerializable("studentData",studentData)
                        val nextFragment = StudentDetailFragment()
                        nextFragment.arguments = arg
                        fadeout.setAnimationListener((object : Animation.AnimationListener {
                            override fun onAnimationStart(p0: Animation?) {}
                            override fun onAnimationEnd(animation: Animation) {
                                activity!!.supportFragmentManager.beginTransaction().
                                replace(R.id.fragment_container,nextFragment).
                                addToBackStack(null).commit()
                            }
                            override fun onAnimationRepeat(p0: Animation?) {}
                        }))
                        holder.layoutAll.startAnimation(fadeout)
                    }
                }
            })
        }

        //全データの取得
        scope.launch {
            studentDao.getAll().forEach {
                mStudentList.add(it)
            }
            withContext(Dispatchers.Main) {
                //recyclerViewのセット
                recyclerView.layoutManager = LinearLayoutManager(activity)
                // Adapter生成してRecyclerViewに値をセット
                //dataがなければ
                if(mStudentList.size<10) {
                    val studentArray = ArrayList<StudentEntity>()
                    studentNumber = if (mStudentList.size < 1) {
                        1
                    } else {
                        mStudentList.size + 1
                    }
                    for (i in 0..Random.nextInt(5, 10)) {
                        val studentData = createStudentData()
                        mStudentList.add(studentData)
                        studentArray.add(studentData)
                    }
                    scope.launch {
                        studentArray.forEach{
                            studentDao.insert(it)
                        }
                    }
                }
                studentView.adapter!!.notifyDataSetChanged()

            }
        }
        //更新ボタン
        updateButton?.setOnClickListener {
            if(mStudentList.size<=10) {
                Toast.makeText(MyApplication.context, "10人以下の為、不足人員を補充します。", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(MyApplication.context, "既定の人数を超えています。\nこれ以上増やせません。", Toast.LENGTH_SHORT).show()
            }
            activity.supportFragmentManager.beginTransaction().
            replace(R.id.fragment_container, StudentFragment()).commit()
        }
        //リセットボタン
        resetButton?.setOnClickListener {
            scope.launch {
                studentDao.deleteAll()
                withContext(Dispatchers.Main){
                    mStudentList.clear()
                    studentView.adapter!!.notifyDataSetChanged()
                }
            }
        }
    }

    private fun createStudentData() : StudentEntity {
        val grade = Random.nextInt(1, 3)
        val manImageList = arrayOf(R.drawable.man1, R.drawable.man2, R.drawable.man3)
        val womanImageList = arrayOf(R.drawable.woman1, R.drawable.woman2, R.drawable.woman3)
        val anotherImageList = arrayOf(R.drawable.bear,R.drawable.elephant,R.drawable.risu)
        val genderList = arrayOf("男","女","その他")
        val prefectureList = arrayOf("北海道", "青森県", "秋田県", "山形県", "岩手県", "宮城県", "福島県")
        val bloodList = arrayOf("A", "B", "AB", "O")
        val firstNameList = arrayOf("山田", "鈴木", "吉田", "田中", "岡村")
        val manLastNameList = arrayOf("太郎", "次郎", "雄太", "健", "康介")
        val womanLastNameList = arrayOf("花子", "ひな", "舞", "京子", "千秋")
        val anotherLastNameList = arrayOf("優", "星", "歩", "泉", "忍")
        val departmentList = arrayOf("普通科", "農学科", "家庭科", "ビジネス科", "情報科")
        val clubList = arrayOf("野球部", "サッカー部", "テニス部", "バレー部", "陸上部")
        val skillList = arrayOf("書道", "楽器", "裁縫", "イラスト", "パソコン")
        val hobbyList = arrayOf("アニメ", "ゲーム", "ドラマ", "スポーツ", "旅行")
        val favoriteFoodList = arrayOf("米", "野菜", "肉", "麺", "魚")

        val gender = genderList[Random.nextInt(0,genderList.size)]
        val studentImage = when (gender) {
            "男" -> manImageList[Random.nextInt(0, manImageList.size)]
            "女" -> womanImageList[Random.nextInt(0, womanImageList.size)]
            else -> anotherImageList[Random.nextInt(0,anotherImageList.size)]
        }
        val age = when (grade) {
            1 -> Random.nextInt(15, 17)
            2 -> Random.nextInt(16, 18)
            3 -> Random.nextInt(17, 19)
            else -> Random.nextInt(1, 101)
        }

        val birthMonth = Random.nextInt(1, 13).toString()
        val birthDay = when (birthMonth) {
            "1" -> Random.nextInt(1, 32).toString()
            "2" -> Random.nextInt(1, 29).toString()
            "3" -> Random.nextInt(1, 32).toString()
            "4" -> Random.nextInt(1, 31).toString()
            "5" -> Random.nextInt(1, 32).toString()
            "6" -> Random.nextInt(1, 31).toString()
            "7" -> Random.nextInt(1, 32).toString()
            "8" -> Random.nextInt(1, 32).toString()
            "9" -> Random.nextInt(1, 31).toString()
            "10" -> Random.nextInt(1, 32).toString()
            "11" -> Random.nextInt(1, 31).toString()
            "12" -> Random.nextInt(1, 32).toString()
            else -> ""
        }
        val birthday = "${birthMonth}月${birthDay}日"
        val birthplace = prefectureList[Random.nextInt(0, prefectureList.size)]

        val bloodType = bloodList[Random.nextInt(0, bloodList.size)]
        val firstName = firstNameList[Random.nextInt(0, firstNameList.size)]

        val lastName = when (gender) {
            "男" -> manLastNameList[Random.nextInt(0, manLastNameList.size)]
            "女" -> womanLastNameList[Random.nextInt(0, womanLastNameList.size)]
            else -> anotherLastNameList[Random.nextInt(0, anotherLastNameList.size)]
        }

        val department = departmentList[Random.nextInt(0, departmentList.size)]
        val club = clubList[Random.nextInt(0, clubList.size)]
        val skill = skillList[Random.nextInt(0, skillList.size)]
        val hobby = hobbyList[Random.nextInt(0, hobbyList.size)]
        val favoriteFood = favoriteFoodList[Random.nextInt(0, favoriteFoodList.size)]
        val memoList = arrayOf(
            "\nきんに君が好き。\nムキムキになりたいと常々思っている",
            "\n橋本環奈が好き。\n写真集はすべて持っている。",
            "\n五条悟が好き。\n休日はグッズを探しに行っている。",
            "\n堺雅人が好き。\nリーガルハイは全話リアルタイムで見ていた。",
            "\n猫アレルギーの為、猫を触れない。",
            "\n漢検１級,英検2級,数検3級"
        )
        var memo = memoList[Random.nextInt(0,memoList.size)]
        if(firstName=="吉田" && lastName=="千秋") memo = "\n狗巻先輩をこの世で唯一愛している"

        val studentData = StudentEntity(
                id = 0,
                grade = grade,
                student_number = studentNumber,
                student_image = studentImage,
                age = age,
                gender = gender,
                birthday = birthday,
                birthplace = birthplace,
                blood_type = bloodType,
                first_name = firstName,
                last_name = lastName,
                department = department,
                club = club,
                skill = skill,
                hobby = hobby,
                favorite_food = favoriteFood,
                memo = memo
        )
        studentNumber++
        return studentData
    }
}

//リストB Adapter
class StudentAdapter constructor(private var recyclerList: ArrayList<StudentEntity>):
        RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private lateinit var listener: OnItemClickListener

    //ホルダーを生成 使用するリストのフォーマットをセット
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_student, parent, false))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        //holder内のViewにセッティング クリックリスナーは実装後にセッティングする。
        val listView = recyclerList[position]
        holder.number.text = listView.student_number.toString()
        holder.image.setImageResource(listView.student_image)
        holder.image.tag = listView.student_image
        holder.firstName.text = listView.first_name
        holder.lastName.text = listView.last_name
        listener.onItemClickListener(position,holder)
    }

    //リストサイズの取得
    override fun getItemCount(): Int { return recyclerList.size }
    //インターフェースの作成
    interface OnItemClickListener{
        fun onItemClickListener(position:Int,holder:StudentViewHolder)
    }
    // リスナー
    fun setOnItemClickListener(listener: OnItemClickListener){ this.listener = listener }

    class StudentViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var layoutAll : LinearLayout = itemView.findViewById(R.id.list_student_layoutAll)
        var number : TextView = itemView.findViewById((R.id.list_student_number))
        var image : ImageView = itemView.findViewById(R.id.list_student_image)
        var firstName : TextView = itemView.findViewById(R.id.list_student_firstName)
        var lastName : TextView = itemView.findViewById(R.id.list_student_lastName)
    }
}
