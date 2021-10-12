package com.rappi.android.network.service

import com.rappi.android.models.network.PeopleResponse
import com.rappi.android.models.network.PersonDetail
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {
    /**
     * [People Popular](https://developers.themoviedb.org/3/people/get-popular-people)
     *
     * Get the list of popular people on TMDb. This list updates daily.
     *
     * @param [page] Specify the page of results to query.
     *
     * @return [PeopleResponse] response
     */
    @GET("person/popular?language=en")
    suspend fun fetchPopularPeople(@Query("page") page: Int): ApiResponse<PeopleResponse>

    /**
     * [Person Detail](https://developers.themoviedb.org/3/people/get-person-details)
     *
     * Get the primary person details by id.
     *
     * @para [id] Specify the id of results to query.
     *
     * @return [PersonDetail] response
     */
    @GET("person/{person_id}")
    suspend fun fetchPersonDetail(@Path("person_id") id: Long): ApiResponse<PersonDetail>
}