package com.alphavantage.app.domain.di.forex

import com.alphavantage.app.domain.usecase.forex.CalculateExchangeRate
import com.alphavantage.app.domain.usecase.forex.GetForexSeries
import com.alphavantage.app.domain.usecase.general.SelectCurrency
import org.koin.dsl.module

val generalModules = module(override = true) {
    single { CalculateExchangeRate(get()) }
    single { SelectCurrency() }
    single { GetForexSeries(get(), get()) }
}