package com.easyprep.easyprep.data.model

import java.io.Serializable

data class Nutriment(
    var nameId: Int = 0,
    var quantity: Float = 0.0f,
    var items: String = ""
) : Serializable