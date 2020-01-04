package com.example.android.guesstheword.screens.game

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.guesstheword.getOrAwaitValue
import com.example.android.guesstheword.mock
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

@RunWith(JUnit4::class)
class GameViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private lateinit var gameViewModel: GameViewModel

    private val scoreObserve: Observer<Int> = mock()

    @Before
    fun setUp() {
        gameViewModel = GameViewModel()
        gameViewModel.score.observeForever(scoreObserve)
    }

    @Test
    fun addition_twoAndTwo_result4() {
        assertThat(4, `is`(2 + 2))
    }


    @Test
    fun onCorrect_clicked_wordChangesAndScoreIncreases() {
        // current score is 0
        val initialScore = gameViewModel.score.value ?: 0
        val currentWord = gameViewModel.word.value as String

        println("Test score $initialScore >>>>> ")

        //on correct expect increase by 1
        gameViewModel.onCorrect()

        val captor = ArgumentCaptor.forClass(Int::class.java)
        captor.run {
            verify(scoreObserve, times(2)).onChanged(capture())
            assertThat(gameViewModel.score.getOrAwaitValue(), `is`(initialScore + 1))
            assertThat(gameViewModel.word.getOrAwaitValue(), not(currentWord)) // check word value changes
        }

    }

    @Test
    fun onSkip_clicked_wordChangesAndScoreDecreases() {
        // current score is 0
        val initialScore = gameViewModel.score.value as Int
        val currentWord = gameViewModel.word.value as String

        //on correct expect increase by 1
        gameViewModel.onSkip()

        val captor = ArgumentCaptor.forClass(Int::class.java)
        captor.run {
            verify(scoreObserve, times(2)).onChanged(capture())
            assertThat(gameViewModel.score.getOrAwaitValue(), `is`(initialScore - 1))
            assertThat(gameViewModel.word.getOrAwaitValue(), not(currentWord)) // check word value changes
        }
    }
}