package com.example.candidatepostsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf

class PostViewModel : ViewModel() {
    var postList = mutableStateListOf<Post>()
        private set
    var errorMessage by mutableStateOf("")
        private set
    var isLoading by mutableStateOf(true)
        private set

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = ""
            try {
                val posts = RetrofitClient.apiService.getPosts()  // Direct call with suspend
                postList.clear()
                postList.addAll(posts)
            } catch (e: Exception) {
                errorMessage = "Failed to load posts: ${e.localizedMessage ?: "Unknown error"}"
            }
            isLoading = false
        }
    }
}
