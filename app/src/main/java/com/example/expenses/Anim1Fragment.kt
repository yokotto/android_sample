package com.example.expenses

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Anim1Fragment : Fragment(){

    private var param1: String? = null
    private var param2: String? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_anim1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Viewの取得
        val baseButton = view.findViewById<Button>(R.id.fragment_anim1_button)
        val topButton = view.findViewById<Button>(R.id.fragment_anim1_buttonTop)
        val bottomButton = view.findViewById<Button>(R.id.fragment_anim1_buttonBottom)
        val leftButton = view.findViewById<Button>(R.id.fragment_anim1_buttonLeft)
        val rightButton = view.findViewById<Button>(R.id.fragment_anim1_buttonRight)

        val fadeout = AnimationUtils.loadAnimation(MyApplication.context,R.anim.fead_out)
        val objAnim = ObjectAnimator()

        //topアニメ
        val topMove = ObjectAnimator.ofFloat(baseButton,"y",-100f)
        topMove.duration = 1000

        //bottomアニメ
        val bottomMove = ObjectAnimator.ofFloat(baseButton,"y",100f)
        bottomMove.duration = 1000

        //leftアニメ
        val leftMove = ObjectAnimator.ofFloat(baseButton,"x",-100f)
        leftMove.duration = 1000

        //bottomアニメ
        val rightMove = ObjectAnimator.ofFloat(baseButton,"x",100f)
        rightMove.duration = 1000

        //各ボタンにアクションとアニメーションを設定する
        topButton.setOnClickListener {
            topMove.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation : Animator){}
                override fun onAnimationEnd(animation : Animator){
                    val point = it.getLocationPointInWindow()
                    Log.v("LOG","X ${point.x} Y ${point.y}")
                    val pointScreen = it.getLocationPointOnScreen()
                    Log.v("LOG","X ${pointScreen.x} Y ${pointScreen.y}")
                }
                override fun onAnimationRepeat(animation : Animator){}
                override fun onAnimationCancel(animation : Animator){}
            })
            topMove.start()
        }

        bottomButton.setOnClickListener {
            bottomMove.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation : Animator){}
                override fun onAnimationEnd(animation : Animator){}
                override fun onAnimationRepeat(animation : Animator){}
                override fun onAnimationCancel(animation : Animator){}
            })
            bottomMove.start()
        }

        leftButton.setOnClickListener {
            leftMove.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation : Animator){}
                override fun onAnimationEnd(animation : Animator){}
                override fun onAnimationRepeat(animation : Animator){}
                override fun onAnimationCancel(animation : Animator){}
            })
            leftMove.start()
        }

        rightButton.setOnClickListener {
            rightMove.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation : Animator){}
                override fun onAnimationEnd(animation : Animator){}
                override fun onAnimationRepeat(animation : Animator){}
                override fun onAnimationCancel(animation : Animator){}
            })
            rightMove.start()
        }
    }

    private fun View.getLocationPointInWindow(): Point {
        val array = IntArray(2)
        this.getLocationInWindow(array)
        return Point(array[0], array[1])
    }

    private fun View.getLocationPointOnScreen(): Point {
        val array = IntArray(2)
        this.getLocationOnScreen(array)
        return Point(array[0], array[1])
    }

    private fun toastLocationInWindow(view: View) {
        val point = view.getLocationPointInWindow()
        Toast.makeText(MyApplication.context, "X ${point.x} Y ${point.y}", Toast.LENGTH_LONG).show()
    }

    private fun toastLocationOnScreen(view: View) {
        val point = view.getLocationPointOnScreen()
        Toast.makeText(MyApplication.context, "X ${point.x} Y ${point.y}", Toast.LENGTH_LONG).show()
    }

    override fun onStart() { super.onStart() }
    override fun onResume() { super.onResume() }
    override fun onPause() { super.onPause() }
    override fun onStop() { super.onStop() }
    override fun onSaveInstanceState(outState: Bundle) { super.onSaveInstanceState(outState) }
    override fun onDestroyView() { super.onDestroyView() }
    override fun onDestroy() { super.onDestroy() }
}