package com.eray.horoscopeapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.eray.horoscopeapp.R
import com.eray.horoscopeapp.databinding.FragmentLoginBinding
import com.eray.core.base.ui.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToHome()
    }

    private fun goToHome() {
        binding.goToHome.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.toUserPersonalDetailFragment())
        }
    }

    override fun getFragmentView(): Int = R.layout.fragment_login
}