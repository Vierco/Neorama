package com.alvarez.sergio.actraining.ui.Detail

import android.text.SpannedString
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import extensions.toYesNoString

class DetailState {

    fun buildNeoDescription(neo: NeoEntityDomain?, sentryNeo: Boolean, hazardousNeo: Boolean): SpannedString {
        return buildSpannedString {
            bold { append("Id: ") }
            appendLine(neo?.id.toString())
            bold { append("H Absolute magnitude: ") }
            appendLine(neo?.absolute_magnitude_h.toString())
            bold { append("NASA JPL url: ") }
            appendLine(neo?.nasa_jpl_url.toString())
            bold { append("It's a sentinel object?: ") }
            appendLine(sentryNeo.toYesNoString())
            bold { append("It’s a potentially hazardous asteroid?: ") }
            appendLine(hazardousNeo.toYesNoString())
        }
    }
}
