package com.testassignment.core.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.testassignment.core.MockTestUtil
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: AppDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        dao = database.citiesDao()
    }

    @Test
    fun saveExchangeRateInDataBaseShouldSucceed(): Unit = runBlocking{
        val mockData = MockTestUtil.getExchangeRatesList()
        dao.addExchangeRateData(mockData)

        val allRates = dao.getExchangeRatesData()
        MatcherAssert.assertThat(allRates, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(allRates, CoreMatchers.`is`(mockData))
        MatcherAssert.assertThat(allRates.size, CoreMatchers.`is`(mockData.size))
    }

    @Test
    fun updateExchangeRateInDataBaseShouldSucceed(): Unit = runBlocking{
        val mockData = MockTestUtil.getExchangeRatesList()
        dao.addExchangeRateData(mockData)

        mockData[0].rate = 2.0
        mockData[1].rate = 6.7123
        dao.addExchangeRateData(mockData)

        val allRates = dao.getExchangeRatesData()
        MatcherAssert.assertThat(allRates, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(allRates[0].rate, CoreMatchers.`is`(2.0))
        MatcherAssert.assertThat(allRates[1].rate, CoreMatchers.`is`(6.7123))
        MatcherAssert.assertThat(allRates.size, CoreMatchers.`is`(mockData.size))
    }

    @After
    fun tearDown() {
        database.close()
    }
}