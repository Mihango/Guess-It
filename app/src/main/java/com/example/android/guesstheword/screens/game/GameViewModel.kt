package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel() {

    companion object {
        const val COUNT_DOWN_TIME = 10000L
        const val ONE_SECOND = 1000L
        const val DONE = 0L
    }

    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // boolean to trigger finish
    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean>
        get() = _eventGameFinished

    private val timer: CountDownTimer
    private val _time = MutableLiveData<Long>()
    val time: LiveData<Long>
        get() = _time

    init {
        Timber.i("GameViewModel created!")
        _eventGameFinished.value = false
        _score.value = 0
//        _word.value = ""
        resetList()
        nextWord()

        timer = object : CountDownTimer(COUNT_DOWN_TIME, ONE_SECOND) {
            override fun onFinish() {
                Timber.e("Countdown timer finished")
                _eventGameFinished.value = true
                _time.value = DONE
            }

            override fun onTick(millisUntilFinished: Long) {
                Timber.e("Countdown timer millisUntilFinished >>> $millisUntilFinished")
                _time.value = millisUntilFinished / ONE_SECOND
            }
        }
        timer.start()
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Timber.i("GameViewModel destroyed!")
    }


    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Moves to the next word in the list
     */
    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.postValue(wordList.removeAt(0))
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun gameFinishedCompletely() {
        _eventGameFinished.value = false
    }
}