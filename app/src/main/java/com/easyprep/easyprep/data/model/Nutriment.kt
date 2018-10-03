package com.easyprep.easyprep.data.model

import java.io.Serializable

data class Nutriment(
    var nameId: Int,
    var quantity: Float,
    var items: String
) : Serializable