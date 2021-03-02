package com.example.expenses

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.expenses.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        val mainIntent = Intent(MyApplication.context, MainActivity::class.java)
        val secondIntent = Intent(MyApplication.context, SecondActivity::class.java)
        val thirdIntent = Intent(MyApplication.context, ThirdActivity::class.java)
        val fourthIntent = Intent(MyApplication.context, FourthActivity::class.java)
        //intent.putExtra(EXTRA_MESSAGE,data)

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_nav)
        navigation.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_menu -> {
                    startActivity(mainIntent)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_expenses -> {
                    startActivity(secondIntent)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_graph -> {
                    startActivity(thirdIntent)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_settings -> {
                    startActivity(fourthIntent)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

        binding.mainTitle.text = "生徒一覧"
        binding.activityMainReturnButton.text = "更新"

        //初期表示
        supportFragmentManager.beginTransaction().
        replace(R.id.fragment_container, StudentFragment()).
        addToBackStack(null).commit()

    }

}
