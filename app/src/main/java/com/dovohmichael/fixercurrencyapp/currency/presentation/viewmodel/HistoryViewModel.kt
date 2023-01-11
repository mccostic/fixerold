package com.dovohmichael.fixercurrencyapp.currency.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dovohmichael.fixercurrencyapp.core.domain.model.Result
import com.dovohmichael.fixercurrencyapp.currency.domain.usecase.GetHistoryUseCase
import com.dovohmichael.fixercurrencyapp.currency.presentation.CurrencyViewState
import com.dovohmichael.fixercurrencyapp.currency.presentation.HistoryViewState
import com.dovohmichael.fixercurrencyapp.currency.presentation.mapper.DomainHistoryToUiModelMapper
import com.dovohmichael.fixercurrencyapp.util.Helpers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
@HiltViewModel
class HistoryViewModel @Inject constructor(private val mapDomainHistoryToUIModel: DomainHistoryToUiModelMapper,
                                           private val getHistoryUseCase: GetHistoryUseCase
): ViewModel(){

    private val _viewState = MutableStateFlow<HistoryViewState>(HistoryViewState.Initial)
    val viewState: StateFlow<HistoryViewState> = _viewState.stateIn(initialValue = HistoryViewState.Initial, scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000))


    fun getHistory(base: String, target: String) {
        val startDate: String = Helpers.lastThreeDaysDate()
            val endDate:String = Helpers.todayDate()
        _viewState.value = HistoryViewState.Loading
        val popularCurrencies = mutableListOf("EUR","JPY","GBP","AUD","CAD","CHF","HKD","SGD","USD","CNY","SEK")
        val topMostPopularCurrencies = popularCurrencies.filter { s->s !=target }.toMutableList()
        topMostPopularCurrencies.add(target)
        val top = topMostPopularCurrencies.joinToString()
        Timber.tag("TOP").d(top)
        viewModelScope.launch {
            when (val historyResult = getHistoryUseCase.invoke(base = base, target = top, startDate=startDate,endDate = endDate)) {
                //conversion result could be successful or erroneous depending on success or
                //failure of the http network request and whether an exception was thrown when
                //trying to perform the request or not
                is Result.Success -> { //if successful, set as new value
                    //_conversionRate.value = conversionResult.data
                    Timber.tag("HISTORY").d("${historyResult.data}")


                    val hustoryList = mapDomainHistoryToUIModel.map(historyResult.data)
                    _viewState.value = HistoryViewState.Content(hustoryList)
                   /* if(_viewState.value is CurrencyViewState.Content) {
                        // _viewState.value = (_viewState.value as CurrencyViewState.Content).copy(convertedAmount = "${conversionResult.data.convertedAmount}",)

                    }*/
                }
                is Result.Fail -> {
                    _viewState.value = HistoryViewState.Failed(error = historyResult.error)
                }
            }
        }
    }

}