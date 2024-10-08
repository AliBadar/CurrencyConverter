package com.testassignment.data.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.testassignment.core.database.AppLocalDataSource
import com.testassignment.core.network.Resource
import com.testassignment.core.network.sources.ApiRemoteDataSource
import com.testassignment.core.utils.MyPreference
import com.testassignment.core.utils.Utils.isNetworkAvailable
import com.testassignment.data.MockTestUtil
import com.testassignment.data.MainCoroutinesRule
import com.testassignment.data.mapper.ExchangeRatesMapper
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert

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

    @MockK
    lateinit var context: Context

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
        val givenExchangeRates = MockTestUtil.getExchangeRatesData()
        coEvery { apiRemoteDataSource.getExchangeRates() }.returns(Resource.success(givenExchangeRates))
        coEvery { myPreference.getTimeStamp() }.returns(System.currentTimeMillis())
        coEvery { myPreference.mContext }.returns(context)
        coEvery { context.isNetworkAvailable() } returns true
        coEvery { exchangeRatesMapper.mapToEntity(any()) }.answers { MockTestUtil.getMapEntity(givenExchangeRates) }
        coEvery { appLocalDataSource.getExchangeRatesData() }.returns(null)
        coEvery { appLocalDataSource.addExchangeRateData(any()) } just Runs // Mock method


        val exchangeRates = sut.getExchangeRates().first()
        MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(exchangeRates.status, CoreMatchers.`is`(Resource.Status.SUCCESS))
        MatcherAssert.assertThat(exchangeRates.data?.rates?.size, CoreMatchers.`is`(givenExchangeRates.rates?.currencies?.size))
    }

    @Test
    fun `get exchangeRatesData should return error from remote server`() = runBlocking {
        val givenErrorMessage = MockTestUtil.getErrorMessage()
        coEvery { apiRemoteDataSource.getExchangeRates() }.returns(Resource.error(null, givenErrorMessage))
        coEvery { myPreference.getTimeStamp() }.returns(System.currentTimeMillis())
        coEvery { myPreference.mContext }.returns(context)
        coEvery { context.isNetworkAvailable() } returns true
        coEvery { exchangeRatesMapper.mapToEntity(any()) }.answers { MockTestUtil.getMapEntity() }
        coEvery { appLocalDataSource.getExchangeRatesData() }.returns(null)
        coEvery { appLocalDataSource.addExchangeRateData(any()) } just Runs // Mock method


        val exchangeRates = sut.getExchangeRates().first()
        MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(exchangeRates.status, CoreMatchers.`is`(Resource.Status.ERROR))
        MatcherAssert.assertThat(exchangeRates.message, CoreMatchers.`is`(givenErrorMessage))
    }

    @Test
    fun `get exchangeRatesData should return success response from local database`() = runBlocking {
        val givenExchangeRates = MockTestUtil.getExchangeRatesData()
        coEvery { apiRemoteDataSource.getExchangeRates() }.returns(Resource.success(givenExchangeRates))
        coEvery { myPreference.getTimeStamp() }.returns(System.currentTimeMillis())
        coEvery { myPreference.mContext }.returns(context)
        coEvery { context.isNetworkAvailable() } returns true
        coEvery { myPreference.getBaseCurrency() }.returns(givenExchangeRates.base.toString())
        coEvery { exchangeRatesMapper.mapToEntity(any()) }.answers { MockTestUtil.getMapEntity(givenExchangeRates) }
        coEvery { appLocalDataSource.getExchangeRatesData() }.returns(givenExchangeRates.rates?.currencies)


        val exchangeRates = sut.getExchangeRates().first()
        MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(exchangeRates.status, CoreMatchers.`is`(Resource.Status.SUCCESS))
        MatcherAssert.assertThat(exchangeRates.data?.base, CoreMatchers.`is`(givenExchangeRates.base))
        MatcherAssert.assertThat(exchangeRates.data?.rates?.size, CoreMatchers.`is`(givenExchangeRates.rates?.currencies?.size))
    }

    @Test
    fun `get exchangeRatesData should return success response from remote server when time difference is greater than 30 minutes`() = runBlocking {
        val givenExchangeRates = MockTestUtil.getExchangeRatesData()
        coEvery { apiRemoteDataSource.getExchangeRates() }.returns(Resource.success(givenExchangeRates))
        coEvery { myPreference.getTimeStamp() }.returns(givenExchangeRates.timestamp!!.toLong())
        coEvery { myPreference.mContext }.returns(context)
        coEvery { context.isNetworkAvailable() } returns true
        coEvery { exchangeRatesMapper.mapToEntity(any()) }.answers { MockTestUtil.getMapEntity(givenExchangeRates) }
        coEvery { appLocalDataSource.getExchangeRatesData() }.returns(null)
        coEvery { appLocalDataSource.addExchangeRateData(any()) } just Runs // Mock method


        val exchangeRates = sut.getExchangeRates().first()
        MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(exchangeRates.status, CoreMatchers.`is`(Resource.Status.SUCCESS))
        MatcherAssert.assertThat(exchangeRates.data?.rates?.size, CoreMatchers.`is`(givenExchangeRates.rates?.currencies?.size))
    }

    @Test
    fun `get exchangeRatesData should return error  when no Internet is found`() = runBlocking {
        val givenErrorMessage = MockTestUtil.getNoInternetAndNoDataFoundMessage()
        coEvery { myPreference.mContext }.returns(context)
        coEvery { context.isNetworkAvailable() } returns false
        coEvery { appLocalDataSource.getExchangeRatesData() }.returns(null)


        val exchangeRates = sut.getExchangeRates().first()
        MatcherAssert.assertThat(exchangeRates, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(exchangeRates.status, CoreMatchers.`is`(Resource.Status.ERROR))
        MatcherAssert.assertThat(exchangeRates.message, CoreMatchers.`is`(givenErrorMessage))
    }


    @After
    fun tearDown() {
    }
}