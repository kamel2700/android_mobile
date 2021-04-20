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
import com.example.rocketreserver.databinding.CreatejokeFragmentBinding
import com.example.rocketreserver.databinding.LoginFragmentBinding
import com.example.rocketreserver.databinding.RegisterFragmentBinding
import server.CreateJokeMutation
import server.MockSignInMutation
import server.RegisterUserMutation
import server.UserQuery

class CreateJokeFragment : Fragment() {
    private lateinit var binding: CreatejokeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CreatejokeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.submitJoke.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                val response =
                    apolloClient(requireContext()).mutate(CreateJokeMutation(binding.etJoke.text.toString()))
                        .await()
                findNavController().navigate(
                    CreateJokeFragmentDirections.open3LaunchListFragment()
                )
            }
        }
    }
}