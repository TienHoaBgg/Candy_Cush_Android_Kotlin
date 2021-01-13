package com.nth.game.manager

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.provider.ContactsContract
import com.nth.game.App
import com.nth.game.R
import com.nth.game.view.MainActivity

/**
 * Created by NguyenTienHoa on 1/10/2021
 */

class SoundManager {

    private var soundPool :SoundPool
    private var context: Context = App.getInstance().applicationContext
    private var checkMatch = 0
    private var sound1 = 0
    private var sound2 = 0
    private var sound3 = 0
    private var sound4 = 0
    private var sound5 = 0
    private var soundMoveAccept = 0
    private var soundMoveCancel = 0
    private var buttonPress = 0
    private var dataManager:DataManager = DataManager.getInstances()

    constructor(){
        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build()
            SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build()
        } else SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        setupAudio()
    }

    private fun setupAudio(){
        try {
            soundMoveAccept = soundPool.load(context, R.raw.move_accepted, 1)
            soundMoveCancel = soundPool.load(context, R.raw.move_cancelled, 1)
            checkMatch = soundPool.load(context, R.raw.checkmark, 2)
            buttonPress = soundPool.load(context, R.raw.button_press, 1)
            sound1 = soundPool.load(context, R.raw.multi_3, 1)
        }catch (e:Resources.NotFoundException){
        }
    }

    fun playMoveAccept(){
        if (!dataManager.getStateSound()) soundPool.play(soundMoveAccept,1f,1f,1,0,1f)
    }

    fun playMoveCancel(){
        if (!dataManager.getStateSound())soundPool.play(soundMoveCancel,1f,1f,1,0,1f)
    }

    fun buttonPress(){
        if (!dataManager.getStateSound())soundPool.play(buttonPress,1f,1f,1,0,1f)
    }

    fun match1(){
        if (!dataManager.getStateSound())soundPool.play(checkMatch,1f,1f,2,0,1f)
    }

    fun star1(){
        if (!dataManager.getStateSound())soundPool.play(sound1,1f,1f,2,0,1f)
    }

    fun release() {
        soundPool.release()
    }

    companion object{
        private var instances: SoundManager? = null
        @JvmStatic
        fun getInstance(): SoundManager {
            if (instances == null){
                instances = SoundManager()
            }
            return instances as SoundManager
        }
    }

}