package com.example.challengehomework1

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

data class member(val name: String, val id: String, val password: String){}

class MainActivity : AppCompatActivity() {
    lateinit var signUp : Button
    lateinit var logIn : Button
    lateinit var inputId : EditText
    lateinit var inputPwd : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()


        val resultLauncher : ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result -> if(result.resultCode == RESULT_OK){
                val id = result.data?.getStringExtra("id")
                val password = result.data?.getStringExtra("password")
                inputId.setText(id)
                inputPwd.setText(password)
               }
        }
        signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            resultLauncher.launch(intent)
        }
        logIn.setOnClickListener {
            isLogin()
        }
    }

    private fun init() {
        signUp = findViewById(R.id.btn_signup)
        logIn = findViewById(R.id.btn_login)
        inputId = findViewById(R.id.input_id)
        inputPwd = findViewById(R.id.input_password)
    }
    private fun isLogin() {
        Toast.makeText(this@MainActivity, "로그인 중...", Toast.LENGTH_SHORT).show()
    }
}