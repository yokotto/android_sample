package com.example.expenses

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val mainIntent = Intent(MyApplication.context,MainActivity::class.java)
        val secondIntent = Intent(MyApplication.context,SecondActivity::class.java)
        val thirdIntent = Intent(MyApplication.context,ThirdActivity::class.java)
        val fourthIntent = Intent(MyApplication.context,FourthActivity::class.java)
        //intent.putExtra(EXTRA_MESSAGE,data)

        val navigation = findViewById<BottomNavigationView>(R.id.second_bottom_nav)
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

        //初期表示
        supportFragmentManager.beginTransaction().
        replace(R.id.activity_second_fragment_container,HttpFragment()).commit()
    }
}