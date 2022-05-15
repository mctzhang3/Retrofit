package com.mzhang.retrofit.uis.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mzhang.retrofit.models.Post
import java.lang.Exception
import androidx.lifecycle.viewModelScope
import com.mzhang.retrofit.api.BlogApi
import com.mzhang.retrofit.models.Employee
import com.mzhang.retrofit.network.RetrofitInstance
import kotlinx.coroutines.launch
import com.google.gson.Gson

private const val TAG = "MainViewModel"
val api = BlogApi.APIObject.getAPI(RetrofitInstance.getRetrofitInstance())

class MainViewModel : ViewModel() {
    private val _posts: MutableLiveData<List<Post>> = MutableLiveData()
    val posts: LiveData<List<Post>>
        get() = _posts

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private var currentPage = 1

    fun getPosts() {
        viewModelScope.launch {
            Log.i(TAG, "Query with page $currentPage")
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val fetchedPosts = api.getPosts(currentPage)
                currentPage += 1
                Log.i(TAG, "Got posts: $fetchedPosts")
                val currentPosts = _posts.value ?: emptyList()
                _posts.value = currentPosts + fetchedPosts
            } catch (e: Exception) {
                _errorMessage.value = e.message
                Log.e(TAG, "Exception $e")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getEmployees() {
        viewModelScope.launch {
            val fetchedEmployees = api.getEmployees()
            for (e in fetchedEmployees) {
                Log.i(TAG, "Got Employees $e")
            }
        }
    }

    fun createEmployee(name: String, role: String) {
        val e = Employee(name = name, role = role)
        val jsonString = Gson().toJson(e)
        Log.i(TAG, "Created jsonString: $jsonString")
        viewModelScope.launch {
            val employee = api.createEmployee(e)
            Log.i(TAG, "Created employee: $employee")
        }
    }
}
