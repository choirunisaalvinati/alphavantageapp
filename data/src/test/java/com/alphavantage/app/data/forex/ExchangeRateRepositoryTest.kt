package com.alphavantage.app.data.forex

import com.alphavantage.app.data.networkModule
import com.alphavantage.app.data.remote.api.ForexService
import com.alphavantage.app.data.remote.implementation.ForexRemoteRepositoryImplementation
import com.alphavantage.app.domain.model.Result
import com.alphavantage.app.domain.repository.forex.ForexRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit

class ExchangeRateRepositoryTest : KoinTest {

    private val retrofit by inject<Retrofit>()
    private lateinit var repository: ForexRemoteRepository
    private val mainThreadSurrogate = newSingleThreadContext("UI Thread")

    @Before
    fun setUp() {
        startKoin {
            modules(networkModule)
        }

        val service = retrofit.create(ForexService::class.java)
        repository = ForexRemoteRepositoryImplementation(service)

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun testRunExchangeRate() {
        val res = runBlocking {
            repository.getExchangeRate("USD", "JPY")
        }

        // TODO gak bisa test karena realtime
        assertEquals(Result.Status.SUCCESS, res.status)
        assertNotNull(res.data)
        assertEquals(108.65, res.data!!.rate, 0.001)
        assertEquals(108.65, res.data!!.bidPrice, 0.001)
        assertEquals(108.65, res.data!!.askPrice, 0.001)
    }

    @Test
    fun testErrorExchangeRate() {
        val res = runBlocking {
            repository.getExchangeRate("USD", "JPK")
        }

        assertEquals(Result.Status.ERROR, res.status)
        assertNull(res.data)
        // Not necessary
        assertEquals(
            "Call failed: The **demo** API key is for demo purposes only. Please claim your free API key at (https://www.alphavantage.co/support/#api-key) to explore our full API offerings. It takes fewer than 20 seconds, and we are committed to making it free forever.",
            res.message
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()

        stopKoin()
    }
}