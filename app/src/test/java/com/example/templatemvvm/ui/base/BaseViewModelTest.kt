package com.example.templatemvvm.ui.base

import org.mockito.Mockito.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify

class BaseViewModelTest {

    private lateinit var baseViewModelTest: BaseViewModelTest

    @Before
    fun before() {
        baseViewModelTest = mock(BaseViewModelTest::class.java)
    }

    @Test
    fun addDisposable() {
        baseViewModelTest.addDisposable()
        verify(baseViewModelTest).addDisposable()
    }

    @Test
    fun onCleared() {
        baseViewModelTest.onCleared()
        verify(baseViewModelTest).onCleared()
    }
}