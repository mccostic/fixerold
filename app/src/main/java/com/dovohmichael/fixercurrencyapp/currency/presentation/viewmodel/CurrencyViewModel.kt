package com.dovohmichael.fixercurrencyapp.currency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dovohmichael.fixercurrencyapp.core.domain.model.Result
import com.dovohmichael.fixercurrencyapp.currency.domain.usecase.ConvertCurrencyUseCase
import com.dovohmichael.fixercurrencyapp.currency.domain.usecase.GetCurrencyRateUseCase
import com.dovohmichael.fixercurrencyapp.currency.domain.usecase.GetCurrencyUseCase
import com.dovohmichael.fixercurrencyapp.currency.domain.usecase.GetHistoryUseCase
import com.dovohmichael.fixercurrencyapp.currency.presentation.CurrencyViewState
import com.dovohmichael.fixercurrencyapp.currency.presentation.mapper.DomainCurrencyRateToUiModelMapper
import com.dovohmichael.fixercurrencyapp.currency.presentation.mapper.DomainCurrencyToUiModelMapper
import com.dovohmichael.fixercurrencyapp.currency.presentation.mapper.DomainHistoryToUiModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.sql.Date
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val getCurrenciesUseCase: GetCurrencyUseCase,
                                            private val convertCurrencyUseCase: ConvertCurrencyUseCase,
                                            private val getCurrencyRateUseCase: GetCurrencyRateUseCase,
                                            private val mapDomainCurrencyToUIModel: DomainCurrencyToUiModelMapper,
                                            private val mapDomainCurrencyRateToUIModel:DomainCurrencyRateToUiModelMapper,
                                           ):ViewModel() {


    private val _viewState = MutableStateFlow<CurrencyViewState>(CurrencyViewState.Initial)
    val viewState: StateFlow<CurrencyViewState> = _viewState.stateIn(initialValue = CurrencyViewState.Initial, scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000))





    init {
        fetchCurrencies()
       // getHistory(base = "USD", target = "GHS", startDate = "2022-12-20", endDate = "2022-12-31")
        //convert("USD", "GHS",1.0)
        //getRate("USD","GHS","2022-12-30","1")
    }



    private fun fetchCurrencies() {
        viewModelScope.launch {
            _viewState.value = CurrencyViewState.Loading
           // if (refresh) {
                //val contentState = viewState.value
               // _viewState.value = CurrencyViewState.Content(isRefreshing = false, mutableListOf())
//            } else {
//                _viewState.value = CurrencyViewState.Loading
//            }
            when (val result = getCurrenciesUseCase.invoke()) {
                is Result.Fail -> {
                    _viewState.value = CurrencyViewState.Failed(result.error)
                }
                is Result.Success -> {
                    val currencyList = result.data
                        //.map(mapDomainForecastWeatherToUiModel)

                    _viewState.value = CurrencyViewState.Content(
                        currencyList = currencyList.map { mapDomainCurrencyToUIModel.map(it) }
                    )
                }
            }
        }
    }


     fun convert(base:String, target:String,amount: Double){

        val fullDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val  date=fullDateFormatter.format(LocalDateTime.now())
        viewModelScope.launch {
            when (val rateResult = getCurrencyRateUseCase.invoke(base=base,target=target,date=date)) {
                //conversion result could be successful or erroneous depending on success or
                //failure of the http network request and whether an exception was thrown when
                //trying to perform the request or not
                is Result.Success -> { //if successful, set as new value
                    //_conversionRate.value = conversionResult.data
                    Timber.tag("rateResult").d("${rateResult.data}")


                    val currencyRate = mapDomainCurrencyRateToUIModel.map(rateResult.data)
                    if(_viewState.value is CurrencyViewState.Content) {
                        var converted = currencyRate.rate * amount
                        _viewState.value = (_viewState.value as CurrencyViewState.Content).copy(convertedAmount = String.format("%.3f", converted),)

                   }
                }
                is Result.Fail -> { //if erroneous, initialize a conversionResult that describes
                    //the error message of the failed http network request or the exception that was
                    //while trying to perform the network request
//                    _conversionRate.value = ConversionResult(
//                        error = Error(
//                            902,
//                            conversionResult.exception.localizedMessage
//                        )
//                    )
                }
            }
        }
    }


    fun onCurrencyChanged(baseCurrency:String,baseAmount:String,targetCurrency: String) {
        if(_viewState.value is CurrencyViewState.Content) {
            //_viewState.value = (_viewState.value as CurrencyViewState.Content).copy(targetCurrency = newText)

            convert(base = baseCurrency, target = targetCurrency, amount =baseAmount.toDouble())
        }

    }


}

