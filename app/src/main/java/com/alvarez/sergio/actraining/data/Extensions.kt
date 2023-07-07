package extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.alvarez.sergio.actraining.App
import com.alvarez.sergio.actraining.BuildConfig
import com.alvarez.sergio.actraining.R
import com.alvarez.sergio.actraining.domain.CustomError
import com.bumptech.glide.Glide
import com.google.android.material.internal.ContextUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.aviran.cookiebar2.CookieBar
import java.io.IOException
import java.time.LocalDate
import kotlin.math.roundToInt

/**
 *Set SnackBar duration in milliseconds.
 */
const val SNACKBAR_DURATION = 3000L

/**
 *Number of NEO images you want to include in the application (drawable folder).
 */
const val MAX_IMAGES = 24

/**
 * Array of local NEO images names.
 */
var NEO_IMAGES_ARRAY = arrayListOf(
    "neo1", "neo2", "neo3", "neo4", "neo5",
    "neo6", "neo7", "neo8", "neo9", "neo10",
    "neo11", "neo12", "neo13", "neo15", "neo16",
    "neo17", "neo18", "neo19", "neo20", "neo21",
    "neo23", "neo23", "neo24", "neo25", "neo26"

)

//region Related to image loading
fun ImageView.loadNeoImage(id: Int) {
    Glide.with(context)
        .load(getNameOfNeoImageEx(id))
        .into(this)
}

@SuppressLint("DiscouragedApi")
fun ImageView.getNameOfNeoImageEx(imageName: Int): Int {
    val image = NEO_IMAGES_ARRAY[imageName]
    return resources.getIdentifier(image, "drawable", BuildConfig.APPLICATION_ID)
}
//endregion

//region Related to RecyclerView
inline fun <T> basicDiffUtilEx(
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame(oldItem, newItem)
}

fun ViewGroup.inflateEx(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
//endregion

//region Exceptions
fun Throwable.toError(): CustomError = when (this) {
    is IOException -> CustomError.Connectivity
    is retrofit2.HttpException -> CustomError.Server(code())
    else -> CustomError.Unknown(message ?: "")
}

suspend fun <T> tryCall(action: suspend () -> T): Either<CustomError, T> = try {
    action().right()
} catch (e: Exception) {
    e.toError().left()
}
//endregion

// region Context
val Context.app: App get() = applicationContext as App
val Fragment.app: App get() = requireContext().app
//endregion

//region Getting dates
val TODAY: LocalDate = LocalDate.now()
val YESTERDAY: LocalDate = TODAY.minusDays(1)
val TOMORROW: LocalDate = TODAY.plusDays(1)

/**
 * Here you can include your own API Key for NASA Open APIs.
 * "DEMO_KEY" has some limitations.
 */
val NASA_OPEN_API_URL = "https://api.nasa.gov/neo/rest/v1/feed?start_date=$YESTERDAY&end_date=$TODAY&api_key=DEMO_KEY"
//endregion

//region Fake data

fun fakeCoordX(): Float {
    val rango = 900 - (-900)
    val numeroAleatorio = (Math.random() * rango) - 900
    return numeroAleatorio.toInt().toFloat()
}

fun fakeCoordY(): Float {
    val rango = 800 - (-800)
    val numeroAleatorio = (Math.random() * rango) - 800
    return numeroAleatorio.toInt().toFloat()
}

fun fakeNeoId(): String {
    val rango = 5000000 - 1000000
    val numeroAleatorio = (Math.random() * rango) - 0
    return numeroAleatorio.roundToInt().toString()
}

fun generateRandomPoints(size: Int, points: MutableList<PointF>) {
    for (point in 1..size) {
        points.add(PointF(fakeCoordX(), fakeCoordY()))
    }
}

//endregion

//region ViewModels
fun <T> LifecycleOwner.launchAndCollectEx(
    flow: Flow<T>,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    body: (T) -> Unit
) {
    lifecycleScope.launch {
        repeatOnLifecycle(state) {
            flow.collect(body)
        }
    }
}
//endregion

inline fun <reified T : Activity> Context.openActivity(vararg params: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundleOf(*params))
    this.startActivity(intent)
}

@SuppressLint("RestrictedApi")
fun Context.showSnackBar(title: String, message: String) {
    CookieBar.build(ContextUtils.getActivity(this))
        .setTitle(title)
        .setTitleColor(R.color.gr_top)
        .setMessage(message)
        .setMessageColor(R.color.white)
        .setIcon(R.mipmap.ic_launcher)
        .setDuration(SNACKBAR_DURATION)
        .setBackgroundColor(R.color.purple)
        .show()
}

fun Boolean.toYesNoString(): String {
    return if (this) "yes" else "no"
}
