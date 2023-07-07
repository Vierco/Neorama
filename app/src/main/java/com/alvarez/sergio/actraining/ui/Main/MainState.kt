package com.alvarez.sergio.actraining.ui.Main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.alvarez.sergio.actraining.R
import com.alvarez.sergio.actraining.domain.CustomError
import com.alvarez.sergio.actraining.domain.NeoEntityDomain
import com.alvarez.sergio.actraining.ui.Main.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildMainState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        ACCESS_COARSE_LOCATION
    )
) = MainState(context, scope, navController, locationPermissionRequester)

class MainState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequester: PermissionRequester,
) {
    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

    fun onNeoClicked(neo: NeoEntityDomain) {
        val action = MainFragmentDirections.actionMainToDetail(neo.id)
        navController.navigate(action)
    }

    fun errorToString(error: CustomError) = when (error) {
        CustomError.Connectivity -> context.getString(R.string.connectivity_error)
        is CustomError.Server -> context.getString(R.string.server_error) + error.code
        is CustomError.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}
