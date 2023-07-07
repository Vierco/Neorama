package com.alvarez.sergio.actraining.ui.Main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alvarez.sergio.actraining.R
import com.alvarez.sergio.actraining.databinding.NeoItemBinding
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import extensions.basicDiffUtilEx
import extensions.inflateEx

class NeosAdapter(private val listener: (NeoEntityDomain) -> Unit) :
    ListAdapter<NeoEntityDomain, NeosAdapter.ViewHolder>(basicDiffUtilEx { old, new -> old.id == new.id }) {
    /** RecyclerView seems to work but basicDiffUtil could be relaunching the neos loading.
     *  I continue with the lessons while waiting to solve this problem.
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Warning: inflate as extension function
        val view = parent.inflateEx(R.layout.neo_item, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val neo = getItem(position)
        holder.bind(neo)
        holder.itemView.setOnClickListener { listener(neo) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val vb = NeoItemBinding.bind(view)
        fun bind(neo: NeoEntityDomain) {
            vb.neoItemView = neo
        }
    }
}
