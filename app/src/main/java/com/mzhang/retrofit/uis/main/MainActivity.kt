package com.mzhang.retrofit.uis.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.mzhang.retrofit.R
import com.mzhang.retrofit.models.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initObserver()
        createPost()
//        viewModel.getPosts()
    }

    private fun initObserver() {
        viewModel.posts.observe(this) { posts ->
            Log.i(TAG, "Number of posts: ${posts.size}")
        }
    }

    // Sample of how to create our own CoroutineScope to make network requests
    private fun createPost() {
        CoroutineScope(Dispatchers.IO).launch {
            val localNewPost = Post(2, 32, "My post title", "Body of post id #32")
            val newPost = api.createPost(localNewPost)
            Log.i(TAG, "New post $newPost")
        }
    }
}