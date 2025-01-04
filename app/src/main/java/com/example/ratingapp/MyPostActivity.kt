package com.example.ratingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MyPostActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_post_main)
        setUpHeaderTitle("自分の投稿一覧")
    }
}