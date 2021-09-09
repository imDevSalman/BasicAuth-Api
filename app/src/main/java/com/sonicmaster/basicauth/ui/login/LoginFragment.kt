package com.sonicmaster.basicauth.ui.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.sonicmaster.basicauth.Constants.BASIC_AUTH
import com.sonicmaster.basicauth.Constants.BASIC_AUTH_HEADER
import com.sonicmaster.basicauth.Constants.GrantType
import com.sonicmaster.basicauth.R
import com.sonicmaster.basicauth.databinding.FragmentLoginBinding
import com.sonicmaster.basicauth.preferences.UserPreferences
import com.sonicmaster.basicauth.handleAPiError
import com.sonicmaster.basicauth.network.ApiInterface
import com.sonicmaster.basicauth.network.Resource
import com.sonicmaster.basicauth.repository.AuthRepository
import com.sonicmaster.basicauth.toast
import com.sonicmaster.basicauth.ui.base.BaseFragment
import com.sonicmaster.basicauth.visible


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        UserPreferences.init(requireContext())

        if (UserPreferences.getAccessToken() != null) {
            findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            gotoProfile.setOnClickListener {
                val username = usernameEdt.text.toString().trim()
                val password = passwordEdt.text.toString().trim()

                val imm =
                    context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)

                if (username.length < 10) {
                    usernameEdt.error = "Invalid data"
                    usernameEdt.requestFocus()
                    return@setOnClickListener
                }
                if (password.length < 6) {
                    passwordEdt.error = "Password must be more than 6 characters"
                    passwordEdt.requestFocus()
                    return@setOnClickListener
                } else {
                    viewModel.login(username, password)
                }

            }

        }
        viewModel.loginResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.loginProgress.visible(true)
                }
                is Resource.Success -> {
                    println("debug: login ${it.value.message}")
                    if (it.value.success) {
                        val username = it.value.data.user_contact
                        val password = it.value.data.user_password
                        viewModel.getTokenFromNetwork(username, password, GrantType.PASSWORD.type)
                    } else {
                        binding.loginProgress.visible(false)
                        requireView().toast(it.value.message)
                    }
                }
                is Resource.Failure -> {
                    binding.loginProgress.visible(false)
                    handleAPiError(it)
                    println(it.errorCode)
                }
            }
        })

        viewModel.tokenResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    findNavController().navigate(R.id.action_loginFragment_to_profileFragment)
                    requireView().toast("Login Successful")
                    binding.loginProgress.visible(false)

                    viewModel.saveTokens(it.value.access_token, it.value.refresh_token)

                    println("debug: token ${it.value.access_token}")
                    println("debug: refresh token ${it.value.refresh_token}")
                    println("debug: token-type ${it.value.token_type}")
                }
                is Resource.Failure -> {
                    binding.loginProgress.visible(false)
                    handleAPiError(it)
                    println(it.errorCode)
                }
            }
        })
    }

    override fun getViewModel() = LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepo() =
        AuthRepository(
            remoteDataSource.buildApi(ApiInterface::class.java, BASIC_AUTH_HEADER, BASIC_AUTH),
            requireContext()
        )
}