package com.example.rocketreserver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rocketreserver.databinding.LaunchItemBinding

class LaunchListAdapter(
    private val onClick: (joke: Joke) -> Unit,
    private val data: MutableList<Joke> = mutableListOf<Joke>()
) :
    RecyclerView.Adapter<LaunchListAdapter.ViewHolder>() {

    class ViewHolder(val binding: LaunchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(joke: Joke){
            binding.audioUrl.text = joke.audioUrl
            binding.jokeText.text = joke.text
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<Joke>){
        this.data.clear()
        this.data.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LaunchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(data[position])
        holder.itemView.setOnClickListener {
            onClick(data[position])
        }
    }

}