package com.example.simplesensorappjc.model

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application): AndroidViewModel(application) {

    private var _location = MutableLiveData<Location?>(null)
    val location: LiveData<Location?>
        get() = _location

    fun setLocation(location: Location) {
        _location.value = location
    }
}








































