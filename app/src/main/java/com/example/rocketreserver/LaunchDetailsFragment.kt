package com.example.rocketreserver

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rocketreserver.databinding.LaunchDetailsFragmentBinding

class LaunchDetailsFragment : Fragment(), MediaPlayer.OnPreparedListener {

    private lateinit var binding: LaunchDetailsFragmentBinding
    val args: LaunchDetailsFragmentArgs by navArgs()

    private var mediaPlayer: MediaPlayer? = null
    private var isPlayerPrepared: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LaunchDetailsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.audioUrl.text = args.audioUrl
        binding.jokeText.text = args.text
        binding.backButton.setOnClickListener {
            findNavController().navigate(
                LaunchDetailsFragmentDirections.onBackLaunchListFragment()
            )
        }
        binding.playButton.setOnClickListener {
            if (isPlayerPrepared) {
                mediaPlayer?.isPlaying?.let {
                    if (it) {
                        mediaPlayer?.pause()
                    } else {
                        mediaPlayer?.start()
                    }
                }
            }
        }
        initMediaPlayer()
        setDataSource()
    }

    private fun initMediaPlayer(){
        mediaPlayer = MediaPlayer()
        mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
    }

    private fun setDataSource(){
        mediaPlayer?.setDataSource(args.audioUrl)
        mediaPlayer?.setOnPreparedListener(this)
        mediaPlayer?.prepareAsync()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        isPlayerPrepared = true
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        isPlayerPrepared = false
    }

}