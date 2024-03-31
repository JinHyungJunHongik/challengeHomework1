package com.example.challengehomework1

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val registerData: MutableLiveData<member> = MutableLiveData()
    var errorIDText: MutableLiveData<String> = MutableLiveData()
    var errorPWDText: MutableLiveData<String> = MutableLiveData()
    var checkName: MutableLiveData<Boolean> = MutableLiveData(false)
    var checkId: MutableLiveData<Boolean> = MutableLiveData(false)
    var checkPwd: MutableLiveData<Boolean> = MutableLiveData(false)

    fun addData(data: member) {
        registerData.value = data
    }

    fun isNameAvailable(str: String) {
        checkName.value = str.length >= 2
    }

    fun isIdAvailable(str: String) {
        val regex = Regex("[0-9|a-z|A-Z]{1,10}")
        checkId.value = str.matches(regex)
    }

    fun isPasswordAvailable(str: String) {
        val regex = Regex("[0-9|a-z|A-Z]{8,20}")
        checkPwd.value = str.matches(regex)
    }

    fun setErrorIDText(str: String) {
        isIdAvailable(str)
        if (checkId.value == true && (str.length in 5..10)) {
            errorIDText.value = ""
        } else {
            errorIDText.value = "올바른 아이디를 입력해주세요"
        }
    }

    fun setErrorPwdText(str: String) {
        isPasswordAvailable(str)
        if (checkPwd.value == true && (str.length in 8..20)) {
            errorPWDText.value = ""
        } else {
            errorPWDText.value = "올바른 비밀번호를 입력해주세요"
        }
    }
    fun isButtonActivate(btn: Button) {
        val mHandler = Handler(Looper.getMainLooper())
        CoroutineScope(Dispatchers.IO).launch {
            while (true){
                delay(500)
                mHandler.postDelayed({
                    btn.isEnabled = checkName.value!! && checkId.value!! && checkPwd.value!!
                },0)
            }
        }
    }

}