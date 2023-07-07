package com.alvarez.sergio.actraining.ui

import android.graphics.PointF
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alvarez.sergio.actraining.databinding.ActivityGraphicViewBinding
import com.alvarez.sergio.actraining.ui.utils.VisibleUniverse

/**
 * The values of the mapview are concrete,
 * * since they are not subject to possible future modifications, deciding not to use constant variables.
 */

class GraphicView : AppCompatActivity() {

    private lateinit var vb: ActivityGraphicViewBinding
    private lateinit var visibleUniverse: VisibleUniverse

    private var points: MutableList<PointF> = mutableListOf()

    private fun updateCartesianMap(points: MutableList<PointF>?) {
        if (points != null) {
            vb.mapView.update(100f, 100f, 100f, 1f, points)
        }
    }

    fun makeScale(view: View) {
        val from = view.id
        val scale = if (from == vb.scaleLess.id) 0.7f else 1.3f
        vb.mapView.makeScale(scale)
    }

    @Deprecated("getParcelableArrayList is deprecated")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityGraphicViewBinding.inflate(layoutInflater)
        setContentView(vb.root)

        visibleUniverse = VisibleUniverse(this, null)

        val bundle = intent.getBundleExtra("points")
        points = bundle?.getParcelableArrayList("points") ?: arrayListOf()

        updateCartesianMap(points)

        with(vb.wip) {
            setHorizontallyScrolling(true)
            isSelected = true
        }
    }
}
