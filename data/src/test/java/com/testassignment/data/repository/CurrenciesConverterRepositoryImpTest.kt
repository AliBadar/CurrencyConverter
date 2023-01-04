package com.testassignment.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.testassignment.core.database.AppLocalDataSource
import com.testassignment.core.network.Resource
import com.testassignment.core.network.sources.ApiRemoteDataSource
import com.testassignment.core.utils.MyPreference
import com.testassignment.data.FakeRemoteData
import com.testassignment.data.MainCoroutinesRule
import com.testassignment.data.mapper.ExchangeRatesMapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrenciesConverterRepositoryImpTest {

    lateinit var sut: CurrenciesConverterRepositoryImp

    @MockK
    lateinit var apiRemoteDataSource: ApiRemoteDataSource

    @MockK
    lateinit var appLocalDataSource: AppLocalDataSource

    @MockK
    lateinit var exchangeRatesMapper: ExchangeRatesMapper

    @MockK
    lateinit var myPreference: MyPreference

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        sut = CurrenciesConverterRepositoryImp(apiRemoteDataSource, appLocalDataSource, exchangeRatesMapper, myPreference)
    }

    @Test
    fun `get exchangeRatesData should return success response from remote server`() = runBlocking {
        val givenExchangeRates = FakeRemoteData.getExchangeRatesData()
        coEvery { apiRemoteDataSource.getExchangeRates() }.returns(Resource.success(givenExchangeRates))
        coEvery { myPreference.getTimeStamp() }.returns(System.currentTimeMillis())
        coEvery { exchangeRatesMapper.mapToEntity(any()) }.answers { FakeRemoteData.getMapEntity(givenExchangeRates) }
        coEvery { appLocalDataSource.getExchangeRatesData() }.returns(null)
        coEvery { appLocalDataSource.addExchangeRateData(any()) } just Runs // Mock method


        val exchangeRates = sut.getExchangeRates().first()
        MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(exchangeRates.status, CoreMatchers.`is`(Resource.Status.SUCCESS))
        MatcherAssert.assertThat(exchangeRates.data?.rates?.size, CoreMatchers.`is`(givenExchangeRates.rates?.currencies?.size))
    }

    @Test
    fun `get exchangeRatesData should return error from remote server`() = runBlocking {
        val givenErrorMessage = FakeRemoteData.getErrorMessage()
        coEvery { apiRemoteDataSource.getExchangeRates() }.returns(Resource.error(null, givenErrorMessage))
        coEvery { myPreference.getTimeStamp() }.returns(System.currentTimeMillis())
        coEvery { exchangeRatesMapper.mapToEntity(any()) }.answers { FakeRemoteData.getMapEntity() }
        coEvery { appLocalDataSource.getExchangeRatesData() }.returns(null)
        coEvery { appLocalDataSource.addExchangeRateData(any()) } just Runs // Mock method


        val exchangeRates = sut.getExchangeRates().first()
        MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(exchangeRates.status, CoreMatchers.`is`(Resource.Status.ERROR))
        MatcherAssert.assertThat(exchangeRates.message, CoreMatchers.`is`(givenErrorMessage))
    }

    @After
    fun tearDown() {
    }
}