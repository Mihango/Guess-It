package com.example.android.guesstheword.screens.game

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.android.guesstheword.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GameViewModelTest {

    @get:Rule
    val testRule = InstantTaskExecutorRule()

    private lateinit var gameViewModel: GameViewModel

    private val scoreObserve: Observer<Int> = mock()

    @Before
    fun setUp() {
        gameViewModel = GameViewModel()
        gameViewModel.scoreLiveData.observeForever(scoreObserve)
    }

    @Test
    fun addition_twoAndTwo_result4() {
        assertThat(4, `is`(2 + 2))
    }


    @Test
    fun onCorrect_clicked_wordChangesAndScoreIncreases() {
        // current score is 0
        val initialScore = gameViewModel.scoreLiveData.value as Int
        val currentWord = gameViewModel.wordLiveData.value as String

        //on correct expect increase by 1
        gameViewModel.onCorrect()

//        val captor = ArgumentCaptor.forClass(Int::class.java)
//        captor.run {
//            // verify(scoreObserve, times(2)).onChanged(capture())
//            assertThat(gameViewModel.scoreLiveData.value, `is`(initialScore + 1))
//        }
        assertThat(gameViewModel.scoreLiveData.getOrAwaitValue(), `is`(initialScore + 1))
        assertThat(gameViewModel.wordLiveData.getOrAwaitValue(), not(currentWord)) // check word value changes
    }

    @Test
    fun onSkip_clicked_wordChangesAndScoreDecreases() {
        // current score is 0
        val initialScore = gameViewModel.scoreLiveData.value as Int
        val currentWord = gameViewModel.wordLiveData.value as String

        //on correct expect increase by 1
        gameViewModel.onSkip()

        assertThat(gameViewModel.scoreLiveData.getOrAwaitValue(), `is`(initialScore - 1))
        assertThat(gameViewModel.wordLiveData.getOrAwaitValue(), not(currentWord)) // check word value changes
    }

    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}