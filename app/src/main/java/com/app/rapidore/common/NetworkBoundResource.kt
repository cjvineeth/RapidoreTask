package com.app.rapidore.common

import android.util.Log
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = query().first()

    //emit(Resource.Loading(data))
    Log.d("GetProducts Details", "Fetching data from network")
    if (shouldFetch(data)) {
        Log.d("GetProducts Details", "Fetching data from network")
        try {
            val fetchedData = fetch()
            saveFetchResult(fetchedData)
        } catch (throwable: Throwable) {
            emit(Resource.Error("Error fetching data from network", data))
            return@flow
        }
    } else {
        Log.d("NetworkBoundResource", "Using cached data")
    }

    emitAll(query().map { Resource.Success(it) })
}