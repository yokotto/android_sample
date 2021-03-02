package com.example.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.expenses.databinding.ActivityMainBinding
import com.example.expenses.databinding.FragmentStudentDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class StudentDetailFragment : Fragment() {

    private lateinit var binding : FragmentStudentDetailBinding
    private lateinit var mStudentList : ArrayList<StudentEntity>
    private var con = MyApplication.context
    private val scope = CoroutineScope(Dispatchers.Default)
    private val database = Room.databaseBuilder(MyApplication.context, AppDatabase::class.java,
            MyApplication.db_name).fallbackToDestructiveMigration().build()
    private var studentNumber = 1
    lateinit var studentData : StudentData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        studentData = StudentData()
        arguments?.let {
            studentData = it.getSerializable("studentData") as StudentData
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentStudentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity
        val returnButton = activity?.findViewById<Button>(R.id.activity_main_returnButton)
        returnButton?.text = "戻る"
        returnButton?.setOnClickListener {
            activity.supportFragmentManager.popBackStack()
        }
        //リセットボタンを非表示にする。
        val resetButton = activity?.findViewById<Button>(R.id.activity_main_resetButton)
        resetButton?.visibility = INVISIBLE
        resetButton?.setOnClickListener {  }

        //val mainActivity = MainActivity()
        //mainActivity.findViewById<TextView>(R.id.fragment_main_title).text = "生徒詳細"
        //val fadeout = AnimationUtils.loadAnimation(MyApplication.context, R.anim.fead_out)
        //初期状態のセット
        setLayoutToStudentDetail(studentData)
        when(studentData.gender) {
            "男" -> binding.color = resources.getColor(R.color.blue2)
            "女" -> binding.color = resources.getColor(R.color.orange1)
            else -> binding.color = resources.getColor(R.color.green1)
        }

        val studentDao = database.studentDao()
        mStudentList = ArrayList()

        //全生徒データの取得
        scope.launch {
            studentDao.getAll().forEach {
                mStudentList.add(it)
            }
        }
    }

    private fun setLayoutToStudentDetail(studentData:StudentData){
        binding.fragmentStudentDetailStudentImage.setImageResource(studentData.studentImage)
        binding.fragmentStudentDetailGrade.text = String.format("${studentData.grade}年")
        binding.fragmentStudentDetailStudentNumber.text = String.format("生徒番号:${studentData.studentNumber}")
        binding.fragmentStudentDetailStudentFirstName.text = String.format(studentData.firstName)
        binding.fragmentStudentDetailStudentLastName.text = String.format(studentData.lastName)
        binding.fragmentStudentDetailAge.text = String.format("${studentData.age}才")
        binding.fragmentStudentDetailGender.text = studentData.gender
        binding.fragmentStudentDetailBloodType.text = String.format("${studentData.bloodType}型")
        binding.fragmentStudentDetailBirthday.text = String.format("誕生日:${studentData.birthday}")
        binding.fragmentStudentDetailBirthplace.text = String.format("出身:${studentData.birthplace}")
        binding.fragmentStudentDetailDepartment.text = String.format("特技:${studentData.department}")
        binding.fragmentStudentDetailClub.text = String.format("クラブ:${studentData.club}")
        binding.fragmentStudentDetailSkill.text = String.format("特技:${studentData.skill}")
        binding.fragmentStudentDetailHobby.text = String.format("趣味:${studentData.hobby}")
        binding.fragmentStudentDetailFavoriteFood.text = String.format("好物:${studentData.favoriteFood}")
        binding.fragmentStudentDetailMemo.text = String.format("備考${studentData.memo}")
    }

    private fun editStudentData() : StudentEntity {
        val grade = Random.nextInt(1, 3)
        val manImageList = arrayOf(R.drawable.man1, R.drawable.man2, R.drawable.man3)
        val womanImageList = arrayOf(R.drawable.woman1, R.drawable.woman2, R.drawable.woman3)
        val genderList = arrayOf("男","女","その他")
        val prefectureList = arrayOf("北海道", "青森県", "秋田県", "山形県", "岩手県", "宮城県", "福島県")
        val bloodList = arrayOf("A", "B", "AB", "O")
        val firstNameList = arrayOf("山田", "鈴木", "吉田", "田中", "岡村")
        val manLastNameList = arrayOf("太郎", "次郎", "雄太", "健", "康介")
        val womanLastNameList = arrayOf("花子", "ひな", "舞", "京子", "唯")
        val anotherLastNameList = arrayOf("優", "星", "歩", "泉", "忍")
        val departmentList = arrayOf("普通科", "農学科", "家庭科", "ビジネス科", "情報科")
        val clubList = arrayOf("野球部", "サッカー部", "テニス部", "バレー部", "陸上部")
        val skillList = arrayOf("書道", "楽器", "裁縫", "イラスト", "パソコン")
        val hobbyList = arrayOf("アニメ", "ゲーム", "ドラマ", "スポーツ", "旅行")
        val favoriteFoodList = arrayOf("米", "野菜", "肉", "麺", "魚")

        val gender = genderList[Random.nextInt(0,genderList.lastIndex)]
        val studentImage = when (gender) {
            "男" -> manImageList[Random.nextInt(0, manImageList.lastIndex)]
            "女" -> womanImageList[Random.nextInt(0, womanImageList.lastIndex)]
            else -> R.drawable.elephant
        }
        val age = when (grade) {
            1 -> Random.nextInt(15, 16)
            2 -> Random.nextInt(16, 17)
            3 -> Random.nextInt(17, 18)
            else -> Random.nextInt(1, 100)
        }

        val birthMonth = Random.nextInt(1, 12).toString()
        val birthDay = when (birthMonth) {
            "1" -> Random.nextInt(1, 31).toString()
            "2" -> Random.nextInt(1, 28).toString()
            "3" -> Random.nextInt(1, 31).toString()
            "4" -> Random.nextInt(1, 30).toString()
            "5" -> Random.nextInt(1, 31).toString()
            "6" -> Random.nextInt(1, 30).toString()
            "7" -> Random.nextInt(1, 31).toString()
            "8" -> Random.nextInt(1, 31).toString()
            "9" -> Random.nextInt(1, 30).toString()
            "10" -> Random.nextInt(1, 31).toString()
            "11" -> Random.nextInt(1, 30).toString()
            "12" -> Random.nextInt(1, 31).toString()
            else -> ""
        }
        val birthday = "${birthMonth}月${birthDay}日"
        val birthplace = prefectureList[Random.nextInt(0, prefectureList.lastIndex)]

        val bloodType = bloodList[Random.nextInt(0, bloodList.lastIndex)]
        val firstName = firstNameList[Random.nextInt(0, firstNameList.lastIndex)]

        val lastName = when (gender) {
            "男" -> manLastNameList[Random.nextInt(0, manLastNameList.lastIndex)]
            "女" -> womanLastNameList[Random.nextInt(0, womanLastNameList.lastIndex)]
            else -> anotherLastNameList[Random.nextInt(0, anotherLastNameList.lastIndex)]
        }

        val department = departmentList[Random.nextInt(0, departmentList.lastIndex)]
        val club = clubList[Random.nextInt(0, clubList.lastIndex)]
        val skill = skillList[Random.nextInt(0, skillList.lastIndex)]
        val hobby = hobbyList[Random.nextInt(0, hobbyList.lastIndex)]
        val favoriteFood = favoriteFoodList[Random.nextInt(0, favoriteFoodList.lastIndex)]

        val memoList = arrayOf(
                "\nきんに君が好き。広瀬すずを殺したいと常々思っている",
                "\nきんに君が好き。田中みな実を殺したいと常々思っている",
                "\n五条悟に乱暴されたいと常々思っている。"
        )
        val memo = memoList[Random.nextInt(0,2)]

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
