package com.jans.googlemap.cut.edge.app.model.markerModels

import com.jans.googlemap.cut.edge.app.utils.ConfigApp
import retrofit2.http.GET

interface ApiService {
    @GET(ConfigApp.ENDPOINTS_MARKER)
    suspend fun getData(): ApiResponse
}