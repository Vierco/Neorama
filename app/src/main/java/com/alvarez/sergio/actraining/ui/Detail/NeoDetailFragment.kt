package com.alvarez.sergio.actraining.ui.Detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alvarez.sergio.actraining.R
import com.alvarez.sergio.actraining.databinding.FragmentNeoDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import extensions.launchAndCollectEx

@AndroidEntryPoint
class NeoDetailFragment : Fragment(R.layout.fragment_neo_detail) {

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var vb: FragmentNeoDetailBinding
    private lateinit var detailState: DetailState

    var hazardousNeo: Boolean = false
    var sentryNeo: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb = FragmentNeoDetailBinding.bind(view)

        detailState = DetailState()

        vb.isThisNeoSaved.setOnClickListener {
            viewModel.onNeoToObserveClicked()
        }

        viewLifecycleOwner.launchAndCollectEx(viewModel.state) { state ->
            vb.neo = state.neo

            hazardousNeo = state.neo?.is_potentially_hazardous_asteroid ?: false
            sentryNeo = state.neo?.is_sentry_object ?: false

            vb.fields.text = detailState.buildNeoDescription(vb.neo, sentryNeo, hazardousNeo)
        }
    }
}
