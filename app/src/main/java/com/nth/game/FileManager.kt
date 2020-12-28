package com.nth.game

import android.content.Context
import java.io.IOException
import java.io.InputStream

/**
 * Created by NguyenTienHoa on 12/23/2020
 */

class FileManager {

    companion object{
        @JvmStatic
        fun readFile(context: Context, fileName: String):String{
            try {
                val inputStream: InputStream = context.assets.open("$fileName.txt")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                return String(buffer)
            }catch (e: IOException){
            }
            return ""
        }

        @JvmStatic
        fun getMap(context: Context, fileName: String): Array<IntArray>? {
            val map = readFile(context, fileName)
            try {
                val items = map.replace("\\[".toRegex(), "").replace("\\]".toRegex(), "").replace("\\s".toRegex(), "").split(",").toTypedArray()
                val size = items.size
                val arr = IntArray(size)
                var row = 0
                for (i in 0 until size) {
                    arr[i] = items[i].toInt()
                    if (arr[i] == 10) {
                        row++
                    }
                }
                var col = size - row * 2
                val maps = Array(row + 1) { IntArray(col) }
                row = 0
                col = 0
                for (i in 0 until size) {
                    if (arr[i] == 13 || arr[i] == 10) {
                        if (arr[i] == 10) {
                            row++
                        }
                    } else {
                        maps[row][col] = arr[i] - 48
                        col++
                    }
                }
                return maps
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return Array(0) { IntArray(0) }
        }
    }

}