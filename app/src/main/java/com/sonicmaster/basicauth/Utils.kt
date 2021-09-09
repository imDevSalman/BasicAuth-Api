package com.sonicmaster.basicauth

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sonicmaster.basicauth.network.Resource
import com.sonicmaster.basicauth.ui.login.LoginFragment
import org.json.JSONObject


fun View.toast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.handleAPiError(failure: Resource.Failure) {
    when {
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().toast("Incorrect credentials")
            }
        }
        failure.errorCode == 404 -> {
            requireView().toast("Resource not found")
        }
        failure.isNetworkError == true -> {
            requireView().toast("Please check your internet connection")
        }
        else -> {
            val error = JSONObject(failure.errorBody?.string()!!).getString("message")
            requireView().toast(error)

        }
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}