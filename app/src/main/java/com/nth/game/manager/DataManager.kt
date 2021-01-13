package com.nth.game.manager

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.nth.game.App
import com.nth.game.view.MainActivity

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
        editor.putBoolean("isSoundOff",!prefs.getBoolean("isSoundOff", false))
        editor.apply()
    }

    fun changeStateMusic(){
        val editor = prefs.edit()
        editor.putBoolean("isMusicOff",!prefs.getBoolean("isMusicOff", false))
        editor.apply()
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