package com.nth.game

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.gson.Gson
import com.nth.game.model.FileMap
import java.io.IOException
import java.io.InputStream

/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class Utils {

    companion object{
        @JvmStatic
        fun readMap(context: Context, fileName: String):FileMap?{
            try {
                val inputStream: InputStream = context.assets.open("$fileName.txt")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                return Gson().fromJson(String(buffer), FileMap::class.java)
            }catch (e: IOException){
            }
            return null
        }

        @JvmStatic
        @BindingAdapter("updateText")
        fun updateText(tv: TextView, value: Any?) {
            tv.text = "$value"
        }
    }

}