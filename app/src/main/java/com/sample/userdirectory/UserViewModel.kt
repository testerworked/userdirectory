package com.sample.userdirectory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
    val userList = MutableLiveData<MutableList<User>>(mutableListOf())
}