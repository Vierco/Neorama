package com.alvarez.sergio.actraining.ui.Main

import android.annotation.SuppressLint
import android.graphics.PointF
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alvarez.sergio.actraining.R
import com.alvarez.sergio.actraining.data.MainViewModel
import com.alvarez.sergio.actraining.data.datasource.NeoLocalDataSource
import com.alvarez.sergio.actraining.databinding.FragmentMainBinding
import com.alvarez.sergio.actraining.ui.GraphicView
import dagger.hilt.android.AndroidEntryPoint
import extensions.TODAY
import extensions.YESTERDAY
import extensions.generateRandomPoints
import extensions.launchAndCollectEx
import extensions.openActivity
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var mainState: MainState

    val adapter = NeosAdapter { mainState.onNeoClicked(it) }

    val points: MutableList<PointF> = mutableListOf()

    @Inject
    lateinit var localDataSource: NeoLocalDataSource

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainState = buildMainState() // extension function in MainState

        val vb = FragmentMainBinding.bind(view).apply {
            recycler.adapter = adapter
        }

        viewLifecycleOwner.launchAndCollectEx(viewModel.mainState) { state ->
            vb.loadingDBVar = state.loading
            vb.neosDBVar = state.neos
            vb.error = state.error?.let {
                mainState.errorToString(it)
            }
        }

        mainState.requestLocationPermission {
            viewModel.onUIReady()
        }

        vb.neoDate.text = ("$YESTERDAY & $TODAY")

        vb.searchingBtn.setOnClickListener {
            points.clear()

            generateRandomPoints(viewModel.mainState.value.neos!!.size, points)
            val bundle = Bundle()
            bundle.putParcelableArrayList("points", ArrayList(points))

            requireActivity().openActivity<GraphicView>("points" to bundle)
        }

        with(vb.wip) {
            setHorizontallyScrolling(true)
            isSelected = true
        }
    }
}
