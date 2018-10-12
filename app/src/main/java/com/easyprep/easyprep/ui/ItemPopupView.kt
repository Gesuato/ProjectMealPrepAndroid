package com.easyprep.easyprep.ui

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.View
import com.easyprep.easyprep.R
import kotlinx.android.synthetic.main.item_popup.view.*

class ItemPopupView(context: Context, attributeSet: AttributeSet) :
    CardView(context, attributeSet) {

    init {
        View.inflate(context, R.layout.item_popup, this)
    }

    fun setTitleItemPopup(title: String) {
        this.titleItemPopup.text = title
    }
    fun setBackgroundImage(image: Int){
        this.imageButtonPopup.setBackgroundResource(image)
    }
}