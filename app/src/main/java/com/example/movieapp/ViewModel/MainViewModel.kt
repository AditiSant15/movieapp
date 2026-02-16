package com.example.movieapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.Domain.FilmItemModel
import com.example.movieapp.Repository.MainRepository


class MainViewModel: ViewModel(){
    private val repository = MainRepository()

    fun loadUpcoming(): LiveData<MutableList<FilmItemModel>> {
        return repository.loadUpcoming()
    }

    fun loadItems(): LiveData<MutableList<FilmItemModel>> {
        return repository.loadItems()
    }


}