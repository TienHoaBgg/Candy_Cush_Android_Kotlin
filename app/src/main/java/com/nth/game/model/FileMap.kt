package com.nth.game.model

/**
 * Created by NguyenTienHoa on 1/1/2021
 */

data class FileMap(var moveLimit:Int, var scoreTargets:IntArray, var tileMap:Array<IntArray>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileMap
        if (moveLimit != other.moveLimit) return false
        if (!scoreTargets.contentEquals(other.scoreTargets)) return false
        if (!tileMap.contentDeepEquals(other.tileMap)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = moveLimit
        result = 31 * result + scoreTargets.contentHashCode()
        result = 31 * result + tileMap.contentDeepHashCode()
        return result
    }
}