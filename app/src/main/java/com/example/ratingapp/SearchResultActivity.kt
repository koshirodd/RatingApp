package com.example.ratingapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView

class SearchResultActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_main)
        setUpHeaderTitle("検索結果")
    }
}