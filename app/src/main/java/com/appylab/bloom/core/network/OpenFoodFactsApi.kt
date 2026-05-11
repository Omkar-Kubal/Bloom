package com.appylab.bloom.core.network

import com.appylab.bloom.core.network.dto.OpenFoodFactsProductResponse
import com.appylab.bloom.core.network.dto.OpenFoodFactsSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenFoodFactsApi {
    @GET("cgi/search.pl?search_simple=1&action=process&json=1")
    suspend fun searchProducts(
        @Query("search_terms") query: String,
        @Query("page_size") pageSize: Int = 20,
        @Query("page") page: Int = 1
    ): OpenFoodFactsSearchResponse

    @GET("api/v0/product/{barcode}.json")
    suspend fun getByBarcode(
        @Path("barcode") barcode: String
    ): OpenFoodFactsProductResponse
}
