package com.example.ratingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

// 全ての画面で共通する動作を定義する。
open class BaseActivity : AppCompatActivity() {

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setUpFooter()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setUpFooter()
    }

    private fun setUpFooter(){
        setUpHomeButton()
        setUpMyPostButton()
        // setUpCamerabutton()
    }

    private fun setUpHomeButton(){
        val footerHomeButton = findViewById<Button>(R.id.home_button)
        footerHomeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            // これがあるとアクティビティの履歴から削除されるので「戻る」ボタンなどで前の画面に戻れなくなる。
            finish()
        }
    }

    private fun setUpCamerabutton(){

    }

    private fun setUpMyPostButton(){
        val footerMyPostButton = findViewById<Button>(R.id.my_post_button)
        footerMyPostButton.setOnClickListener {
            val intent = Intent(this, MyPostActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    fun setUpHeaderTitle(title : String){
        val headerTitle = findViewById<TextView>(R.id.header_title)
        headerTitle.text = title
    }
}