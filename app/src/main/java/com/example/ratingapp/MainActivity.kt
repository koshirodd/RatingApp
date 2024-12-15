package com.example.ratingapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ratingapp.databinding.HomeMainBinding
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding:HomeMainBinding

    //Cameraアクティビティを起動するためのランチャーオブジェクト
    private val _cameraLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallbackFromCamera())

    //保存された画像のURI
    private var _imageUri : Uri? = null

    // アプリ起動時に実行されるメソッド。画面作成やデータの用意など初期処理を行う。
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = HomeMainBinding.inflate(layoutInflater)
        setContentView(R.layout.home_main)

        // 検索フィールド内の虫眼鏡アイコン
        val searchInput = findViewById<EditText>(R.id.searchInput)

        // TODO: メソッド切り分け
        searchInput.setOnTouchListener { v, event ->
            // ACTION_UP = タップして指を離す動作
            if (event.action == MotionEvent.ACTION_UP) {
                // アイコンの範囲内（範囲 = 2）をタップしたかどうか。
                val iconRange = 2
                if (event.x >= (searchInput.right - searchInput.compoundDrawables[iconRange].bounds.width())) {
                    // Accessibilityのためクリックしたことにする必要があるらしい
                    println("Magnifying glass icon clicked")
                    v.performClick()
                    // 検索内容がからではなければアイコンをクリックした際に画面遷移する。
                    val searchCondition = searchInput.text.toString().trim()
                    if (searchCondition.isNotEmpty()) {
                        val intent = Intent(this, SearchResultActivity::class.java)
                        // 検索内容を"SearchCondition"として遷移先画面に渡す。
                        intent.putExtra("SearchCondition", searchCondition)
                        startActivity(intent)
                    } else {
                        // TODO: 検索条件が入力されなかった場合の処理。画面上に何か警告を出す？
                    }
                    // イベントが実行されたことになる。
                    return@setOnTouchListener true  // Indicate that the event was handled
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


    fun onCameraImageClick(view: View){
        val dateformat = SimpleDateFormat("yyyyMMddHHmmss")
        val now = Date()

        val nowstr = dateformat.format(now)
        val filename = "CameraIntentSamplePhoto_${nowstr}.jpg"

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

        _imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        intent.putExtra(MediaStore.EXTRA_OUTPUT,_imageUri)
        _cameraLauncher.launch(intent)
    }

    private inner class ActivityResultCallbackFromCamera: ActivityResultCallback<ActivityResult> {
               override fun onActivityResult(result: ActivityResult){
            if (result.resultCode == RESULT_OK){
                //画像を表示するImageView取得
                /*
                val ivCamera = findViewById<ImageView>(R.id.cameraButton)
                ivCamera.setImageURI(_imageUri)
                */
            }
        }
    }
}