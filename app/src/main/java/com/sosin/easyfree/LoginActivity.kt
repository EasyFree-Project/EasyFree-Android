package com.sosin.easyfree

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sosin.easyfree.navigation.LoginFragment
import com.sosin.easyfree.navigation.SignupFragment
import com.sosin.easyfree.navigation.CameraFragment
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        login_text.setOnClickListener {
            // 로그인 fragment로 전환
            var loginFragment = LoginFragment()
            supportFragmentManager.beginTransaction().replace(R.id.login_content, loginFragment).commitAllowingStateLoss()
        }
        signup_text.setOnClickListener {
            // 회원가입 fragment로 전환
            var signupFragment = SignupFragment()
            supportFragmentManager.beginTransaction().replace(R.id.login_content, signupFragment).commitAllowingStateLoss()
        }
        login_text.performClick()
    }
}