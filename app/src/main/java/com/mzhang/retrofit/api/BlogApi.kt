package com.mzhang.retrofit.api

import com.mzhang.retrofit.models.Employee
import com.mzhang.retrofit.models.Post
import com.mzhang.retrofit.models.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*

interface BlogApi {
    @GET("posts")
    suspend fun getPosts(@Query("_page") page: Int = 1, @Query("_limit") limit: Int = 10): List<Post>

    @Headers("Platform: Android")
    @GET("posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post

    @GET("users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User

    // request body contains the complete new version
    @PUT("posts/{id}")
    suspend fun updatePost(@Path("id") postId: Int, @Body post: Post): Post

    // request body only needs to contain the specific changes to the resource
    @PATCH("posts/{id}")
    suspend fun patchPost(@Path("id") postId: Int, @Body params: Map<String, String>): Post

    @DELETE("posts/{id}")
    suspend fun deletePost(@Header("Auth-Token") auth: String, @Path("id") postId: Int)

    @POST("posts/")
    suspend fun createPost(@Body post: Post): Post

    @GET("posts/{id}")
    fun getPostViaCallback(@Path("id") postId: Int): Call<Post>

    @GET("users/{id}")
    fun getUserViaCallback(@Path("id") userId: Int): Call<User>

    @GET("employees")
    suspend fun getEmployees(): List<Employee>

    @POST("employees")
    suspend fun createEmployee(@Body employee: Employee): Employee

    object APIObject {
        fun getAPI(retrofit: Retrofit): BlogApi = retrofit.create(BlogApi::class.java)
    }
}