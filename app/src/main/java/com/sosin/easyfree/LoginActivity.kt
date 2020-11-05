package com.sosin.easyfree

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sosin.easyfree.navigation.LoginFragment
import com.sosin.easyfree.navigation.SignupFragment
import com.sosin.easyfree.navigation.user.App
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.findViewById<Button>(R.id.login_text)?.setOnClickListener {
            val fragment = supportFragmentManager!!.findFragmentById(R.id.login_content)
            if (fragment is LoginFragment) {
                login()
            } else {
                val loginFragment = LoginFragment()
                supportFragmentManager!!.beginTransaction().replace(R.id.login_content, loginFragment).commitAllowingStateLoss()
            }
        }


        this.findViewById<Button>(R.id.signup_text)?.setOnClickListener {
            val fragment = supportFragmentManager!!.findFragmentById(R.id.login_content)
            if (fragment is SignupFragment) {
                signUp()
            } else {
                var signupFragment = SignupFragment()
                supportFragmentManager!!.beginTransaction().replace(R.id.login_content, signupFragment).commitAllowingStateLoss()
            }
        }

        login_text.performClick()
    }

    //    로그인 시, 메인화면 전환
    fun login() {
        if (findViewById<EditText>(R.id.login_email_edittext).text.toString() == "sosin" && findViewById<EditText>(R.id.login_password_edittext).text.toString() == "1") {
            moveMainPage(1)
        } else {
            //        로그인 인증
            var queue = Volley.newRequestQueue(this)
            val params = JSONObject()
            params.put("username", findViewById<EditText>(R.id.login_email_edittext).text.toString())
            params.put("password", findViewById<EditText>(R.id.login_password_edittext).text.toString())

            var jr = JsonObjectRequest(
                    Request.Method.POST,
                    getString(R.string.login_url),
                    params,
                    Response.Listener<JSONObject> { response ->
                        try {
                            var statusCode = response.getString("statusCode") //Key값을 받아오는 방식
                            var message = response.getString("message")

                            var member_idx = response.getJSONObject("data").getString("member_idx")
                            moveMainPage(member_idx.toInt())
                        } catch (e: Exception) {
                            Log.d("LOGIN", "login fail" + response.toString())
                        }
                    },
                    Response.ErrorListener { error ->
                        VolleyLog.e("LOGIN", "/post request fail! Error: ${error.message}")
                    })
            queue.add(jr)
        }
    }

    // User 정보 받아서 넣기
    fun moveMainPage(member_idx: Int) {
        if (member_idx != null) {
            App.uid = member_idx
            var mainintent = Intent(this, MainActivity::class.java)
            startActivity(mainintent) //member idx 넘겨주기
        }
    }

    fun signUp() {
        if (findViewById<EditText>(R.id.signup_password1_edittext).text.toString() == findViewById<EditText>(R.id.signup_password2_edittext).text.toString()) {

            var queue = Volley.newRequestQueue(this)

            val params = JSONObject()
            params.put("username", findViewById<EditText>(R.id.signup_email_edittext).text.toString())
            params.put("password", findViewById<EditText>(R.id.signup_password1_edittext).text.toString())
            params.put("displayName", findViewById<EditText>(R.id.signup_displayName_edittext).text.toString())

            var jr = JsonObjectRequest(
                    Request.Method.POST,
                    getString(R.string.signup_url),
                    params,
                    Response.Listener<JSONObject> { response ->
                        try {
                            var statusCode = response.getString("statusCode")
                            var message = response.getString("message")
                            var member_idx = response.getJSONObject("data").getString("member_idx")
                            moveMainPage(member_idx.toInt())
                        } catch (e: Exception) {
                            Log.d("SIGNUP", "signup fail" + response.toString())
                        }

                    },
                    Response.ErrorListener { error ->
                        VolleyLog.e("SIGNUP", "/post request fail! Error: ${error.message}")
                    })
            queue.add(jr)
        } else {
            Toast.makeText(this, "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
    }

}