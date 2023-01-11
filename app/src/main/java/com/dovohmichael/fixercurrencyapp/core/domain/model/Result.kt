package com.dovohmichael.fixercurrencyapp.core.domain.model

/**
 * A result class for treating errors as first class citizens(Operation Result Pattern)
 */
sealed class Result<out Error, out Data> {

    data class Fail<Error>(val error: Error) : Result<Error, Nothing>()

    data class Success<Data>(val data: Data) : Result<Nothing, Data>()

}