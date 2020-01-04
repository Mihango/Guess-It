package com.example.android.guesstheword.screens.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class GameViewModel : ViewModel() {

    // The current word
    val wordLiveData = MutableLiveData<String>()

    // The current score
    val scoreLiveData = MutableLiveData<Int>()

    // boolean to trigger finish
    val gameFinished = MutableLiveData<Boolean>()

    init {
        Timber.i("GameViewModel created!")
        gameFinished.value = false
        scoreLiveData.value = 0
        wordLiveData.value = ""
        resetList()
        nextWord()
    }

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    override fun onCleared() {
        super.onCleared()
        Timber.i("GameViewModel destroyed!")
    }

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
    fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            gameFinished.postValue(true)
        } else {
            wordLiveData.postValue(wordList.removeAt(0))
        }
    }

    fun increaseScore() {
        scoreLiveData.postValue(scoreLiveData.value?.plus(1))
    }

    fun reduceScore() {
        scoreLiveData.postValue(scoreLiveData.value?.minus(1))
    }
}