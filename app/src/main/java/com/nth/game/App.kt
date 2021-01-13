package com.nth.game

import android.app.Application

/**
 * Created by NguyenTienHoa on 1/11/2021
 */

class App :Application() {

    companion object{
        private var instances:App? = null
        @JvmStatic
        fun getInstance():App{
            if (instances == null){
                instances = App()
            }
            return instances!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instances = this
    }
}