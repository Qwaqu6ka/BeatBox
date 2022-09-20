package com.example.beatbox

import android.widget.SeekBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel(val beatBox: BeatBox): ViewModel() {

    override fun onCleared() {
        super.onCleared()
        beatBox.release()
    }

    private val _playbackSpeedPercent = MutableLiveData(INITIAL_PLAYBACK_SPEED_PERCENT)
    val playbackSpeedPercent: LiveData<Int> = _playbackSpeedPercent
    val playbackSpeedRate
        get() = 1.0f + playbackSpeedPercent.value!!.toFloat() / 100

    fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        _playbackSpeedPercent.value = progress
    }

    companion object {
        const val INITIAL_PLAYBACK_SPEED_PERCENT = 0
    }
}