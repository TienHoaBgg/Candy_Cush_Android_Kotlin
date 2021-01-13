package com.nth.game.manager

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

/**
 * Created by NguyenTienHoa on 1/10/2021
 */

class MusicManager : MediaPlayer.OnErrorListener {
    private var mp: MediaPlayer? = null


    fun setData(context: Context, idMusic: Int) {
        mp?.release()
        mp = MediaPlayer()
        mp!!.setDataSource(context, Uri.parse("android.resource://com.nth.game/$idMusic"))
        mp?.setOnErrorListener(this)
        mp?.prepareAsync()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        return true
    }

    fun play(): Boolean {
        if (mp == null) {
            return false
        }
        mp?.start()
        return true
    }

    fun pause(): Boolean {
        if (mp == null) {
            return false
        }
        mp?.pause()
        return true
    }

    fun stop(): Boolean {
        if (mp == null) {
            return false
        }
        mp?.stop()
        return true
    }

    fun isPlay(): Boolean{
        if (mp == null) {
            return false
        }
        if (mp!!.isPlaying){
            return true
        }
        return false
    }

    fun release() {
        mp?.release()
    }

}