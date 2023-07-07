package com.alvarez.sergio.actraining.ui.Main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alvarez.sergio.actraining.domain.NeoEntityDomain

@BindingAdapter("items")
fun RecyclerView.setItems(neos: List<NeoEntityDomain>?) {
    if (neos != null) {
        (adapter as? NeosAdapter)?.submitList(neos)
    }
}
