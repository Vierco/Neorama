package com.alvarez.sergio.actraining.ui.Detail

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import extensions.loadNeoImage

@BindingAdapter("idAssigned")
fun ImageView.setNeoImage(idAssigned: Int?) {
    if (idAssigned != null) loadNeoImage(idAssigned)
}
