package com.fit2081.fit2081a2.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fit2081.fit2081a2.network.fruityVice.*
import kotlinx.coroutines.launch

class FruitViewModel : ViewModel(){
    private val repository = FruitRepository()


    var fruit by mutableStateOf<Fruit?>(null)
        private set

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    fun fetchFruit(name: String,context: Context) {
        isLoading = true
        errorMessage = null
        viewModelScope.launch {
            try {
                fruit = repository.getFruitByName(name)
                errorMessage = null
            } catch (e: Exception) {
                fruit = null
                errorMessage = "Error: ${e.localizedMessage}"
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }
}