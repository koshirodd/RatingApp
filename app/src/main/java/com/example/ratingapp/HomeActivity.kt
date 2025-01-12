package com.example.ratingapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ratingapp.databinding.HomeMainBinding
import java.text.SimpleDateFormat
import java.util.Date

class HomeActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding:HomeMainBinding

    //Cameraアクティビティを起動するためのランチャーオブジェクト
    private val _cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallbackFromCamera())

    //保存された画像のURI
    private var _imageUri : Uri? = null

    // アプリ起動時に実行されるメソッド。画面作成やデータの用意など初期処理を行う。
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 検索フィールド内の虫眼鏡アイコン
        val searchInput = binding.searchBar.root.findViewById<EditText>(R.id.search_input)

        val recyclerView = binding.mainSection

        // 縦にリスト内のアイテムを表示するためにLinearLayoutManagerを使う。
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = PostAdapter(getPosts())

        setupSearchInputTouchListener(searchInput)
    }

    // 虫眼鏡アイコンをクリックした際の挙動
    @SuppressLint("ClickableViewAccessibility")
    private fun setupSearchInputTouchListener(searchInput : EditText) {
        searchInput.setOnTouchListener { v, event ->
            // ACTION_UP = Tap and release action
            if (event.action == MotionEvent.ACTION_UP) {
                val iconRange = 2
                if (event.x >= (searchInput.right - searchInput.compoundDrawables[iconRange].bounds.width())) {
                    println("Magnifying glass icon clicked")
                    v.performClick()
                    val searchCondition = searchInput.text.toString().trim()
                    if (searchCondition.isNotEmpty()) {
                        val intent = Intent(this, SearchResultActivity::class.java)
                        intent.putExtra("SearchCondition", searchCondition)
                        startActivity(intent)
                    } else {
                        // TODO: Handle empty search condition
                    }
                    return@setOnTouchListener true
                }
            }
            false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }



    @SuppressLint("SimpleDateFormat")
    fun onCameraImageClick(view: View){
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val now = Date()

        val nowStr = dateFormat.format(now)
        val filename = "CameraIntentSamplePhoto_${nowStr}.jpg"

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

        _imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        intent.putExtra(MediaStore.EXTRA_OUTPUT,_imageUri)
        _cameraLauncher.launch(intent)
    }

    // dbからデータを取得していないので手動で定義して返す。
    private fun getPosts():List<PostModel>{
        return listOf(
            PostModel(R.drawable.test_image, "Sample Post 1"),
            PostModel(R.drawable.test_image, "Sample Post 2"),
            PostModel(R.drawable.test_image, "Sample Post 3")
        )
    }

    private inner class ActivityResultCallbackFromCamera: ActivityResultCallback<ActivityResult> {
        override fun onActivityResult(result: ActivityResult){
           /* if (result.resultCode == RESULT_OK){
                //画像を表示するImageView取得

                val ivCamera = findViewById<ImageView>(R.id.cameraButton)
                ivCamera.setImageURI(_imageUri)

            }*/
        }
    }
}