package ch.srg.dataProvider.integrationlayer.request

import ch.srg.dataProvider.integrationlayer.data.remote.MediaStatisticPost
import ch.srg.dataProvider.integrationlayer.data.remote.MediaStatisticResult
import ch.srg.dataProvider.integrationlayer.data.remote.ShowStatisticResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IlStatisticService {

    @GET("2.0/mediaStatistic/byUrn/{urn}")
    suspend fun getMediaStatisticByUrn(@Path("urn") urn: String): MediaStatisticResult

    @POST("2.0/mediaStatistic/byUrn/{urn}/clicked")
    suspend fun postMediaClicked(@Path("urn") urn: String, @Body mediaStatisticsBody: MediaStatisticPost): MediaStatisticResult

    @POST("2.0/mediaStatistic/byUrn/{urn}/liked")
    suspend fun postMediaLiked(@Path("urn") urn: String, @Body mediaStatisticsBody: MediaStatisticPost): MediaStatisticResult

    @POST("2.0/mediaStatistic/byUrn/{urn}/shared/{service}")
    suspend fun postMediaShared(
        @Path("urn") urn: String,
        @Path("service") service: String,
        @Body body: MediaStatisticPost
    ): MediaStatisticResult

    @POST("2.0/showStatistic/byUrn/{showUrn}/searchResultClicked")
    suspend fun postSearchResultClickedByUrn(@Path("showUrn") urn: String): ShowStatisticResult
}
