package com.easyprep.easyprep.data.model

import java.io.Serializable

data class Meal(
    var index: Int = 0,
    var nameId: Int = 0,
    var nutriments: ArrayList<Nutriment>,
    var valueIsChanged: Boolean
) : Serializable