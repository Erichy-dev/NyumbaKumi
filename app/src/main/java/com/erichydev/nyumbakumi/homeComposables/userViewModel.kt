package com.erichydev.nyumbakumi.homeComposables

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.erichydev.nyumbakumi.api.addOrGetUserData
import com.erichydev.nyumbakumi.data.UserData

class UserViewModel: ViewModel() {
    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    private val _toggleUserDataView = MutableLiveData(false)
    val toggleUserDataView: LiveData<Boolean> = _toggleUserDataView

    private val _upgradeMessage = MutableLiveData<String>()
    val upgradeMessage: LiveData<String> = _upgradeMessage

    private fun setUserData(user: UserData) {
        _userData.postValue(user)
    }

    fun toggleUserDataView(toggle: Boolean) {
        _toggleUserDataView.postValue(toggle)
    }

    fun setUpgradeMessage(message: String) {
        _upgradeMessage.postValue(message)
    }

    fun updateUserData(context: Context, userName: String, userEmail: String, views: Int?, userPhone: Int?) {
        addOrGetUserData(
            context,
            userName,
            userEmail,
            "Free",
            views,
            userPhone,
            onSuccess = {user -> setUserData(user)},
            onFailure = { _ -> /* Handle error, e.g., show a message */ }
        )
    }
}