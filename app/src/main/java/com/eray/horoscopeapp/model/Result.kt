package com.eray.horoscopeapp.model

sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Failed<T>(val exception: Exception) : Result<T>()
}