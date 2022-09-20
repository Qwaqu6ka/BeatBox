package com.example.beatbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels { factory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.viewModel = viewModel

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(viewModel.beatBox.sounds)
        }

        viewModel.playbackSpeedPercent.observe(this@MainActivity) {
            binding.speedText.text = getString(R.string.playback_speed, it)
        }
    }

    private inner class SoundHolder(private val holderBinding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(holderBinding.root) {

        init {
            holderBinding.viewModel = SoundViewModel(viewModel.beatBox)
            holderBinding.button.setOnClickListener {
                val playbackRate = this@MainActivity.viewModel.playbackSpeedRate
                holderBinding.viewModel?.onButtonClicked(playbackRate)
            }
        }

        fun bind(sound: Sound) {
            holderBinding.apply {
                viewModel?.sound = sound
                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding: ListItemSoundBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.list_item_sound, parent, false)
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount(): Int = sounds.size
    }
}