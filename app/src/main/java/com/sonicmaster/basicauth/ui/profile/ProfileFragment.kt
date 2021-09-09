package com.sonicmaster.basicauth.ui.profile

import android.os.Bundle
import android.view.*
import com.bumptech.glide.Glide
import com.sonicmaster.basicauth.Constants.BEARER_AUTH_HEADER
import com.sonicmaster.basicauth.databinding.FragmentProfileBinding
import com.sonicmaster.basicauth.preferences.UserPreferences
import com.sonicmaster.basicauth.handleAPiError
import com.sonicmaster.basicauth.network.ApiInterface
import com.sonicmaster.basicauth.network.Resource
import com.sonicmaster.basicauth.repository.UserRepository
import com.sonicmaster.basicauth.toast
import com.sonicmaster.basicauth.ui.base.BaseFragment
import com.sonicmaster.basicauth.visible


class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, UserRepository>() {
    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepo(): UserRepository {

        val token = UserPreferences.getAccessToken()

        val apiInterface =
            remoteDataSource.buildApi(ApiInterface::class.java, BEARER_AUTH_HEADER, token!!)

        println("debug: profile $token")
        return UserRepository(apiInterface)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getUser()


        viewModel.userResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    binding.profileProgress.visible(true)
                }
                is Resource.Success -> {
                    binding.profileProgress.visible(false)
                    if (it.value.success) {
                        val data = it.value.data
                        binding.apply {
                            userImage.apply {
                                Glide.with(this).load(data.user_image).into(this)
                            }
                            userId.text = data.id
                            userName.text = data.user_name
                            userEmail.text = data.user_email
                            userContact.text = data.user_contact
                            userAge.text = data.user_age
                            userGender.text = data.user_gender
                            userCity.text = data.user_city
                            userAddress.text = data.user_address
                            userNationality.text = data.user_nationality
                            userCard.text = data.user_card_account_no
                            userPincode.text = data.user_pincode
                            userState.text = data.user_state
                            userDob.text = data.user_dob
                        }
                    } else {
                        requireView().toast(it.value.message)
                    }
                }
                is Resource.Failure -> {
                    binding.profileProgress.visible(false)
                    handleAPiError(it)
                }
            }
        })

    }


}