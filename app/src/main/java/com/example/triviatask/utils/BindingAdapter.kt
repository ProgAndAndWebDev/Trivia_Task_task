package com.example.triviatask.utils

import android.R
import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.example.triviatask.model.data.response.apiCategory.TriviaCategory
import com.example.triviatask.ui.base.BaseAdapter
import com.example.triviatask.model.data.domain.CheckOptions
import com.example.triviatask.utils.Constant.LEMON_TAG
import com.mcdev.quantitizerlibrary.HorizontalQuantitizer
import com.mcdev.quantitizerlibrary.QuantitizerListener


@BindingAdapter(value = ["app:entries"])
fun setEntries(view: Spinner, entries: List<TriviaCategory>?) {
    if (entries != null) {
        ArrayAdapter(
            view.context,
            R.layout.simple_spinner_dropdown_item, (entries.map { it.name })
        )
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                view.adapter = it
            }
    }
}


@BindingAdapter(value = ["app:selectedValue"])
fun selectedItem(view: Spinner, item: String?) {
    if (view.selectedItem?.toString() != item) {
        view.setSelection(view.getSelectedIndex(item))
    }
}

@InverseBindingAdapter(attribute = "selectedValue", event = "SpinnerOnItemSelected")
fun captureSelectedValue(view: Spinner): String? {
    return view.selectedItem.toString()
}

@BindingAdapter("SpinnerOnItemSelected")
fun setSelectedListener(view: Spinner, changeListener: InverseBindingListener) {
    view.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            changeListener.onChange()
        }
        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
}


@BindingAdapter(value = ["selectRadioButtonIndex"])
fun setSelectedChildIndex(view: RadioGroup, index: Int) {

    if (view.checkedRadioButtonId != index) {
        view.setSelectedIndex(index)
    }
}

@InverseBindingAdapter(attribute = "selectRadioButtonIndex", event = "selectRadioButtonIndex")
fun getChildIndex(view: RadioGroup): Int? {
    return view.getSelectedIndex()
}

@BindingAdapter("selectRadioButtonIndex")
fun setRadioListener(view: RadioGroup, attChange: InverseBindingListener) {
    view.setOnCheckedChangeListener { group, checkedId ->
        attChange.onChange()
    }
}

@SuppressLint("ResourceType")
@BindingAdapter(value = ["value"])
fun setPikerNumber(view: HorizontalQuantitizer, value: Int?) {
    if (view.value != value) {
        value?.let { view.value = it }
    }
    view.setMinusIconColor("#5f66d0")
    view.setMinusIconBackgroundColor("#23E4AA6B")
    view.setPlusIconColor("#e4aa6b")
    view.setPlusIconBackgroundColor("#325F66D0")
}

@InverseBindingAdapter(attribute = "value", event = "pikerNumberChangeEvent")
fun getPikerNumber(view: HorizontalQuantitizer): Int? {
    return view.value
}

@BindingAdapter("pikerNumberChangeEvent")
fun setPikerListener(view: HorizontalQuantitizer, attChange: InverseBindingListener) {


    view.setQuantitizerListener(object : QuantitizerListener {
        override fun onDecrease() {
            attChange.onChange()
        }

        override fun onIncrease() {
            attChange.onChange()
        }
    })
}
@BindingAdapter(value = ["app:optionsBackgroundColor"])
fun setBackgroundColor(view: TextView, state: CheckOptions) {
    view.setTextColor(ContextCompat.getColor(view.context, com.example.triviatask.R.color.primary_text_color))
    view.background =
        when (state) {
            CheckOptions.UNSELECTED -> {
                ContextCompat.getDrawable(view.context, com.example.triviatask.R.drawable.default_options_background)
            }
            CheckOptions.SELECTED_CORRECT -> {
                ContextCompat.getDrawable(view.context, com.example.triviatask.R.drawable.currect_options_background)
            }
            CheckOptions.SELECTED_INCORRECT -> {
                ContextCompat.getDrawable(view.context, com.example.triviatask.R.drawable.incurrect_options_background)
            }
        }
}


@BindingAdapter(value = ["app:items"])
fun <T> setRecyclerItems(view: RecyclerView, items: List<T>?) =
    (view.adapter as BaseAdapter<T>?)?.let {
        Log.i(LEMON_TAG, "Items : ${items.toString()}")
        if (items != null)
            it.setItem(items)
        else
            it.setItem(emptyList())
    }

@BindingAdapter(value = ["app:showOnSuccess"])
fun <T> showOnSuccess(view: View, state: State<T>?) {
    if (state is State.Success) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter(value = ["app:showOnError"])
fun <T> showOnError(view: View, state: State<T>?) {
    if (state is State.Error) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.INVISIBLE
    }
}

@BindingAdapter(value = ["app:showOnLoading"])
fun <T> showOnLoading(view: View, state: State<T>?) {
        if (state is State.Loading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }

}
@BindingAdapter(value = ["app:ifWinner"  , "app:total"])
fun checkWinner(view: TextView, valueScore: Int , totalNumber: Int) {
    view.text = if(valueScore > totalNumber/2 ) { "You Win" }
    else {  "You Lost"}
}


@BindingAdapter(value = ["app:ifCongrats" , "app:total"])
fun checkCongrats(view: TextView, valueScore: Int , totalNumber: Int) {
    view.text = if(valueScore > totalNumber/2 ) {  "Congrats!"  }
    else { "Hard Luck!" }
}

@BindingAdapter(value = ["app:ifCelebration"  , "app:total"])
fun checkCelebration(view: View, valueScore: Int, totalNumber: Int) {
    view.visibility = if(valueScore > totalNumber/2 ) { View.VISIBLE }
    else { View.GONE }
}

@BindingAdapter(value = ["app:ifLost"  , "app:total"])
fun checkLost(view: View, valueScore: Int, totalNumber: Int) {
    view.visibility = if(valueScore <= totalNumber/2 ) { View.VISIBLE }
    else { View.GONE }
}



