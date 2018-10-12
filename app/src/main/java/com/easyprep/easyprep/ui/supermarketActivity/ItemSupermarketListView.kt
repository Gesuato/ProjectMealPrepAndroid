package com.easyprep.easyprep.ui.supermarketActivity

import com.easyprep.easyprep.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_supermarketlist.*

class ItemSupermarketListView(private val title: String) : Item() {

    private var isChecked = false
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.item_supermarketlistId.text = title
        viewHolder.check_button.setBackgroundResource(setImageInCheckeButton())
        viewHolder.check_button.setOnClickListener {
            this.isChecked = !isChecked
            viewHolder.check_button.setBackgroundResource(setImageInCheckeButton())
        }

    }

    override fun getLayout(): Int = R.layout.item_supermarketlist

    private fun setImageInCheckeButton(): Int {
        return if (this.isChecked) {
            R.drawable.ic_check_blue_24dp
        } else {
            R.drawable.ic_check_black_24dp
        }
    }
}