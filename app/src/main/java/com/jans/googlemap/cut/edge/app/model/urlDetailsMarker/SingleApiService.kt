package com.jans.googlemap.cut.edge.app.model.urlDetailsMarker

import com.jans.googlemap.cut.edge.app.activities.ItemsScreen
import com.jans.googlemap.cut.edge.app.utils.ConfigApp
import retrofit2.http.GET
import retrofit2.http.Url

interface SingleApiService {
    @GET
    suspend fun getData(@Url url: String): SingleApiResponse
}