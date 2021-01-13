package com.nth.game.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nth.game.App
import com.nth.game.model.Level

/**
 * Created by NguyenTienHoa on 1/11/2021
 */

class DataManager {

    private var prefs: SharedPreferences =
        App.getInstance().getSharedPreferences("GameCandy", Context.MODE_PRIVATE)

    constructor()

    fun getStateSound():Boolean{
        return prefs.getBoolean("isSoundOff", false)
    }

    fun getStateMusic():Boolean{
        return prefs.getBoolean("isMusicOff", false)
    }

    fun changeStateSound(){
        val editor = prefs.edit()
        editor.putBoolean("isSoundOff", !prefs.getBoolean("isSoundOff", false))
        editor.apply()
    }

    fun changeStateMusic(){
        val editor = prefs.edit()
        editor.putBoolean("isMusicOff", !prefs.getBoolean("isMusicOff", false))
        editor.apply()
    }

    fun saveCurrentLevel(id: Int){
        val editor = prefs.edit()
        editor.putInt("CurrentLevel", id)
        editor.apply()
    }

    fun getCurrentLevelId():Int{
        return prefs.getInt("CurrentLevel", 0)
    }

    fun getHighScore():MutableList<Level>{
        val typeLevel = object : TypeToken<MutableList<Level>>() {}.type
        return Gson().fromJson(prefs.getString("HighScore", "[]"), typeLevel)
    }

    fun saveHighScore(currentLevel: Level){
        var listScore = getHighScore()
        if (listScore.size > 0){
            var isNotFount = true
            for (item in listScore){
                if (item.name == currentLevel.name ){
                    isNotFount = false
                    if (item.score < currentLevel.score){
                        item.score = currentLevel.score
                    }
                }
            }
            if (isNotFount){
                listScore.add(currentLevel)
            }
        }else{
            listScore.add(currentLevel)
        }
        listScore = bubbleSort(listScore)
        val editor = prefs.edit()
        editor.putString("HighScore", Gson().toJson(listScore))
        editor.apply()
    }

    private fun bubbleSort(arr: MutableList<Level>): MutableList<Level> {
        for (i in 0 until arr.size - 1) {
            for (j in 0 until arr.size - 1 - i) {
                if (arr[j].id > arr[j + 1].id) {
                    swap(arr, j, j + 1)
                }
            }
        }
        return arr
    }

    private fun swap(arr: MutableList<Level>, i: Int, j: Int) {
        val temp = arr[i]
        arr[i] = arr[j]
        arr[j] = temp
    }

    companion object{
        private var instances:DataManager? = null
        @JvmStatic
        fun getInstances():DataManager{
            if (instances == null){
                instances = DataManager()
            }
            return instances as DataManager
        }
    }

}