package com.easyprep.easyprep.ui

import com.easyprep.easyprep.R
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_expandable_header.*

class ExpandableHeaderItem(private val title: String,private val color: Int) : Item(), ExpandableItem {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.item_expandable_header_title.text = title
        viewHolder.item_expandable_header_icon.setImageResource(getRotatedIconResId())
        viewHolder.item_expandable_header_root.setBackgroundResource(color)

        viewHolder.item_expandable_header_root.setOnClickListener {
            expandableGroup.onToggleExpanded()
            viewHolder.item_expandable_header_icon.setImageResource(getRotatedIconResId())
        }
    }

    private lateinit var expandableGroup: ExpandableGroup
    override fun getLayout() = R.layout.item_expandable_header

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    private fun getRotatedIconResId() =
        if (expandableGroup.isExpanded) {
            R.drawable.ic_keyboard_arrow_up_black_24dp
        } else {
            R.drawable.ic_keyboard_arrow_down_black_24dp
        }

}