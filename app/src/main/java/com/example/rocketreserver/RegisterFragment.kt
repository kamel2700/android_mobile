package com.example.rocketreserver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo.coroutines.await
import com.example.rocketreserver.databinding.LoginFragmentBinding
import com.example.rocketreserver.databinding.RegisterFragmentBinding
import server.MockSignInMutation
import server.RegisterUserMutation
import server.UserQuery

class RegisterFragment : Fragment() {
    private lateinit var binding: RegisterFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.submit.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                val mynickname = binding.etNickname.text.toString()
                Log.d("first success", "$mynickname")
                val response =
                    apolloClient(requireContext()).mutate(RegisterUserMutation(nickname = mynickname))
                        .await()
                findNavController().navigate(
                    RegisterFragmentDirections.open2LaunchListFragment())
            }
        }
    }
}