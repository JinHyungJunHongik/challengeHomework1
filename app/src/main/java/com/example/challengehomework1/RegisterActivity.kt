package com.example.challengehomework1

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var viewModel: RegisterViewModel
    private lateinit var name: EditText
    private lateinit var id: EditText
    private lateinit var password: EditText
    private lateinit var btnRegister: Button
    private lateinit var errorNameText: TextView
    private lateinit var errorIdText: TextView
    private lateinit var errorPwdText: TextView
    private lateinit var registerData: MutableLiveData<member>
    private lateinit var errorId: MutableLiveData<String>
    private lateinit var errorPwd: MutableLiveData<String>
    private lateinit var checkName: MutableLiveData<Boolean>
    private lateinit var checkId: MutableLiveData<Boolean>
    private lateinit var checkPwd: MutableLiveData<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        //각종 초기화 코드 담음
        init()

        //EditText의 TextChangeListener, TextWatcher 사용
        name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.isNameAvailable(name.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        id.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setErrorIDText(id.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setErrorPwdText(password.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        //임시로 단일 데이터에 해당 가입 정보 저장 및 registerActivityForResult 사용
        btnRegister.setOnClickListener {
            val Member = member(
                name.text.toString(),
                id.text.toString(),
                password.text.toString()
            )
            viewModel.addData(Member)
            registerMessage()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id", id.text.toString())
            intent.putExtra("password", password.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
        idCheck()
        passwordCheck()
        viewModel.isButtonActivate(btnRegister)

    }





    //뷰모델의 데이터를 뽑아오는 함수
    private fun registerMessage() {
        registerData.observe(this) {
            val name = it.name
            val id = it.id
            val password = it.password
            Toast.makeText(
                this@RegisterActivity,
                "이름 : $name, 아이디 : $id, 비밀번호 : $password",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun idCheck() {
        errorId.observe(this) {
            errorIdText.text = it
        }
    }

    private fun passwordCheck() {
        errorPwd.observe(this) {
            errorPwdText.text = it
        }
    }

    private fun init(){
        name = findViewById(R.id.edit_name)
        id = findViewById(R.id.edit_ID)
        password = findViewById(R.id.edit_password)
        btnRegister = findViewById(R.id.btn_register)
        errorNameText = findViewById(R.id.errorText_name)
        errorIdText = findViewById(R.id.errorText_id)
        errorPwdText = findViewById(R.id.errorText_password)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        errorIdText.setTextColor(Color.rgb(255, 0, 0))
        errorPwdText.setTextColor(Color.rgb(255, 0, 0))
        errorNameText.setTextColor(Color.rgb(255, 0, 0))
        errorIdText.text = ""
        errorPwdText.text = ""
        errorNameText.text = ""
        btnRegister.isEnabled = false
        registerData = viewModel.registerData
        errorId = viewModel.errorIDText
        errorPwd = viewModel.errorPWDText
        checkName = viewModel.checkName
        checkId = viewModel.checkId
        checkPwd = viewModel.checkPwd
    }
}