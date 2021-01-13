package com.nth.game

import android.graphics.Color
import android.graphics.Point
import com.nth.game.model.Level

/**
 * Created by NguyenTienHoa on 12/30/2020
 */

class Constant {
    companion object {
        //====== Thay doi khi Run Game ==========
        var screenW = 0
        var screenH = 0


        //========================================

        const val spaceBoard = 8
        const val spaceCandy = 10
        const val spaceBoardWidth = 200
        const val headerH = 260f
        const val footerH = 130f
        val colorBgBoard = Color.argb(125, 20, 180, 255)


        val listLevel: List<Level> = listOf(
                Level(1,"Level 1", "level1", 0, 0),
                Level(2,"Level 2", "level2", 0, 0),
                Level(3,"Level 3", "level3", 0, 0),
                Level(4,"Level 4", "level4", 0, 0),
                Level(5,"Level 5", "level5", 0, 0),
                Level(6,"Level 6", "level6", 0, 0),
                Level(7,"Level 7", "level7", 0, 0),
                Level(8,"Level 8", "level8", 0, 0),
                Level(9,"Level 9", "level9", 0, 0),
                Level(10,"Level 10", "level10", 0, 0),
        )

    }
}