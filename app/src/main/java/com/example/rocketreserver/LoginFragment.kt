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
import server.MockSignInMutation
import server.RegisterUserMutation
import server.UserQuery

class LoginFragment : Fragment() {
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submit.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                val myemail = binding.etEmail.text.toString()
                Log.d("first success", "$myemail")
                val response =
                    apolloClient(requireContext()).mutate(MockSignInMutation(email = myemail )).await()
                Log.d("Token", "Token before save")
                response?.data?.mockSignIn?.accessToken?.let {
                    User.setToken(requireContext(), it)
                    Log.d("Token", "Token $it is saved")
                }
                Log.d("first success", "Success ${response?.data}")

                if (response.data?.mockSignIn?.user?.isRegistered == true){
                    findNavController().navigate(
                        LoginFragmentDirections.openLaunchListFragment()
                    )

                }
                else{
                    findNavController().navigate(
                        LoginFragmentDirections.openRegisterFragment()
                    )


                }
            }
        }




    }
}