package com.example.templatemvvm.data.remote

import com.example.templatemvvm.data.mappers.SignInResponse
import com.example.templatemvvm.data.mappers.SignUpResponse
import com.example.templatemvvm.data.models.Product
import com.example.templatemvvm.data.models.ProductsResponse
import com.example.templatemvvm.data.models.Screens
import com.example.templatemvvm.data.models.User
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*

interface RemoteService {
    @FormUrlEncoded
    @POST("register")
    fun signUp(
        @Field("name") name: String, @Field("email") email: String
        , @Field("password") password: String, @Field("zipcode") zipCode: String,
        @Field("country") country: String, @Field("city") city: String
    ): Single<SignUpResponse>

    @FormUrlEncoded
    @POST("login")
    fun signIn(@Field("email") email: String, @Field("password") password: String): Single<SignInResponse>

    @GET("getUser")
    fun getUser(@Query("idUser") idUser: String): Single<User>

    @GET("products")
    fun getProductDetail(@Path("id") productId: Int): Observable<Product>

    @GET("products")
    fun getProducts(@Header("Authorization") api_key: String): Observable<ProductsResponse>

    @GET("getScreens")
    fun getScreens(): Single<Screens>
}