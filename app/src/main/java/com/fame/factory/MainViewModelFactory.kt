package com.fame.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fame.repository.MainRepository
import com.fame.viewmodel.MainViewModel

class MainViewModelFactory (private val repository: MainRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
       }

}