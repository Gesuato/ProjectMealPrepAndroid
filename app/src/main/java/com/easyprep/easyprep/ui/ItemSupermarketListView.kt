package com.easyprep.easyprep.ui

import com.easyprep.easyprep.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_supermarketlist.*

class ItemSupermarketListView(private val title: String,private val color: Int) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.item_supermarketlistId.text = title
        viewHolder.item_supermarketlist_root.setBackgroundResource(color)
    }

    override fun getLayout(): Int = R.layout.item_supermarketlist

}