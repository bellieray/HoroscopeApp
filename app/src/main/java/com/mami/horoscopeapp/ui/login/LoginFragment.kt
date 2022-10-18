package com.mami.horoscopeapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mami.horoscopeapp.R
import com.mami.horoscopeapp.databinding.FragmentLoginBinding
import com.mami.horoscopeapp.ui.base.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToHome()
    }

    private fun goToHome() {
        binding.goToHome.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }

    override fun getFragmentView(): Int = R.layout.fragment_login
}