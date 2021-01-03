package com.nth.game

import com.nth.game.model.Candy

/**
 * Created by NguyenTienHoa on 1/3/2021
 */
class Test {
    var test: Array<Array<Candy?>>

    init {
        test = Array(10) { arrayOfNulls(10) }
    }
}