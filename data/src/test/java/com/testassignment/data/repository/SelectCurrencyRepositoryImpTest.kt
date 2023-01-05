package com.testassignment.data.repository

import com.testassignment.core.network.Resource
import io.mockk.MockKAnnotations
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class SelectCurrencyRepositoryImpTest {

    lateinit var sut: SelectCurrencyRepositoryImp

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = SelectCurrencyRepositoryImp()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `get currenciesList should return list of currencies`() = runBlocking {
        val currenciesList = sut.selectCurrency().first()

        MatcherAssert.assertThat(currenciesList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(currenciesList[0].code, CoreMatchers.`is`("AED"))
        MatcherAssert.assertThat(currenciesList[0].name, CoreMatchers.`is`("United Arab Emirates Dirham"))

    }
}