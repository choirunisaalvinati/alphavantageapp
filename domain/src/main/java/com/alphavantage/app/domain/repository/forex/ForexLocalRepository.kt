package com.alphavantage.app.domain.repository.forex

import com.alphavantage.app.domain.model.forex.ExchangeRate
import com.alphavantage.app.domain.model.forex.ForexSeries
import com.alphavantage.app.domain.model.general.Currency

interface ForexLocalRepository {

    suspend fun saveExchangeRate(
        newExchangeRate: ExchangeRate,
        fromCurrency: Currency,
        toCurrency: Currency,
        oldExchangeRateId: Long?
    )

    fun getExchangeRate(
        currency1: Currency,
        currency2: Currency
    ): ExchangeRate?

    suspend fun saveDailySeries(
        series: ForexSeries,
        fromCurrency: Currency,
        toCurrency: Currency
    )

    fun getDailySeries(
        fromCurrency: Currency,
        toCurrency: Currency
    ): ForexSeries?
}