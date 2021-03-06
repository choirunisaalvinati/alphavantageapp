package com.alphavantage.app

import com.alphavantage.app.data.di.networkModule
import com.alphavantage.app.data.di.submodule.cryptoRepositoryModule
import com.alphavantage.app.data.di.submodule.generalRepositoryModule
import com.alphavantage.app.data.di.submodule.openApiRepositoryModule
import com.alphavantage.app.di.submodule.currenciesViewModules
import com.alphavantage.app.di.submodule.exchangeRateViewModules
import com.alphavantage.app.domain.di.forex.generalModules
import com.alphavantage.app.domain.di.general.openApiModules

val dataModules = listOf(networkModule, cryptoRepositoryModule, generalRepositoryModule, openApiRepositoryModule)
val domainModules = listOf(generalModules, openApiModules)
val vmModules = listOf(currenciesViewModules, exchangeRateViewModules)
val testModules = dataModules + domainModules + vmModules