package com.example.rocketreserver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.rocketreserver.databinding.LaunchListFragmentBinding
import okhttp3.internal.cookieToString
import server.MockSignInMutation
import server.UserQuery

class LaunchListFragment : Fragment() {
    private lateinit var binding: LaunchListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LaunchListFragmentBinding.inflate(inflater)
        return binding.root
    }

    private var myAdapter: LaunchListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.launches.apply {
            layoutManager = LinearLayoutManager(requireContext())

            adapter = LaunchListAdapter(onJokeClick).also {
                myAdapter = it
            }
        }

        binding.addJoke.setOnClickListener {
            findNavController().navigate(
                LaunchListFragmentDirections.openJokeCreation()
            )
        }

        lifecycleScope.launchWhenResumed {
            Log.d("token", "${User.getToken(requireContext())}")
            val response = apolloClient(requireContext()).query(UserQuery()).await()
            response?.data?.user?.publishedJokes?.data?.map {
                Joke(it.text, it.id, it.audioUrl)
            }?.let {
                myAdapter?.setData(it)
                myAdapter?.notifyDataSetChanged()
            }
            Log.d("Second response", "Success ${response?.data}")

        }
            }

    val onJokeClick: (joke: Joke) -> Unit = {
        findNavController().navigate(
            LaunchListFragmentDirections.openLaunchDetails(it.audioUrl, it.text, it.id)
        )
    }

    }