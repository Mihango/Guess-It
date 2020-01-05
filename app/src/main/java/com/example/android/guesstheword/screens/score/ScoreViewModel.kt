package com.example.android.guesstheword.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class ScoreViewModel(private val score: Int) : ViewModel() {

    private val _finalScore = MutableLiveData<Int>()
    val finalScore: LiveData<Int>
        get() = _finalScore

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        Timber.i("Final score $score")
        _finalScore.value = score
        _eventPlayAgain.value = false
    }

    fun playAgain(play: Boolean) {
        _eventPlayAgain.value = play
    }
}