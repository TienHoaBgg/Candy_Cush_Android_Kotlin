package com.nth.game

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nth.game.databinding.ActivityMainBinding
import java.io.IOException
import java.io.InputStream
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val content = FileManager.getMap(this,"level1")
        var index = 0
        binding.btnPlay.setOnClickListener {

            
//            val intArray = FileManager.convert(byteArray)


//            binding.txtContent.setText(text.toString())
//            debug(FileManager.readMap(this,"level1"))
//            startActivity(
//                Intent(
//                    this@MainActivity,
//                    PlayActivity::class.java
//                )
//            )
        }
    }



    fun debug(content: String){
        Log.i("Test", content)
    }

}