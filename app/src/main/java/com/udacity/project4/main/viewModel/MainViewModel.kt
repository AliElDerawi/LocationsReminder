package com.udacity.project4.main.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.project4.base.BaseViewModel

class MainViewModel(private val mApp: Application) : BaseViewModel(mApp) {

    private var _hideToolbarLiveData = MutableLiveData<Boolean>()
    val hideToolbarLiveData: LiveData<Boolean>
        get() = _hideToolbarLiveData

    private var _showUpButtonLiveData = MutableLiveData<Boolean>()
    val showUpButtonLiveData: LiveData<Boolean>
        get() = _showUpButtonLiveData

    fun setHideToolbar(hideToolbar: Boolean) {
        _hideToolbarLiveData.value = hideToolbar
    }

}