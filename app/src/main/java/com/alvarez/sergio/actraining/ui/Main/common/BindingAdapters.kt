package com.alvarez.sergio.actraining.ui.Main.common

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun View.setVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.GONE
}
