package com.freight.shipper.core.platform

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.core.content.ContextCompat

/**
 * @CreatedBy Sanjay Kushwah on 8/28/2019.
 * sanjaykushwah0601@gmail.com
 */
open class HintSpinnerAdapter1<T>(
    context: Context, val list: MutableList<T>, private var hint: String? = null
) : ArrayAdapter<T>(
    context, android.R.layout.simple_list_item_1, list
) {


    init {

    }

    private var ctx: Context = context

    fun hasHint(): Boolean {
        return hint != null
    }

    @CallSuper
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val textView: TextView
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        }
        textView = convertView as TextView
        if (hint != null && position == 0) {
            textView.text = hint
            textView.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.tertiary_text_light
                )
            )
        } else {
            val item = list.get(position)
            item?.also {
                textView.text = getLabelFor(it)
            }

            textView.setTextColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.primary_text_light
                )
            )
        }
        return convertView
    }

    @CallSuper
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val textView: TextView
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            textView = convertView as TextView
            textView.setCompoundDrawables(
                null, null,
                ContextCompat.getDrawable(context, android.R.drawable.spinner_background), null
            )
        } else {
            textView = convertView as TextView
        }
        if (hint != null && position == 0) {
            textView.text = hint
        } else {
            val item = list?.get(position)
            item?.also {
                textView.text = getLabelFor(it)
            }
        }
        return convertView
    }

    override fun isEnabled(position: Int): Boolean {
        return if (hint != null) position != 0 && super.isEnabled(position) else super.isEnabled(
            position
        )
    }

    open fun getLabelFor(item: T): String {
        return item.toString()
    }

    open fun getKeyFor(item: T): String {
        return item.toString()
    }

    fun getItemAtPosition(position: Int): T {
        return list.get(position)
    }

    fun selectItemByTitle(spinner: Spinner, title: String?) {
        if (title == null) return
        var position: Int = -1
        list.forEachIndexed { index, item ->
            if (title == getLabelFor(item)) position = index
        }
        if (position != -1) spinner.setSelection(position + 1)
    }

    fun selectItemById(spinner: Spinner, id: String?) {
        if (id == null) return
        var position: Int = -1
        list.forEachIndexed { index, item ->
            if (id == getKeyFor(item)) position = index
        }
        if (position != -1) spinner.setSelection(position + 1)
    }

    override fun getCount(): Int {
        return if (hint != null) {
            super.getCount() + 1
        } else {
            super.getCount()
        }
    }

    fun setHint(hint: String) {
        this.hint = hint
    }

    fun setList(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }


}
