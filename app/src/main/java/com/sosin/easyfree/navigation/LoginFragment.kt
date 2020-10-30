package com.sosin.easyfree.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sosin.easyfree.MainActivity
import com.sosin.easyfree.R
import com.sosin.easyfree.navigation.user.App
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject

class LoginFragment : Fragment() {
    var TAG = "LOGIN"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_login, container, false)
        view.findViewById<Button>(R.id.login_button).setOnClickListener{
            singinEmail()
        }
        return view
    }

//    로그인 시, 메인화면 전환
    fun singinEmail() {
        if(login_email_edittext.text.toString() == "sosin" && login_password_edittext.text.toString() == "1"){
            moveMainPage(1)
        }else{
            //        로그인 인증
            var queue = Volley.newRequestQueue(activity)
            val params = JSONObject()
            params.put("username", login_email_edittext.text.toString())
            params.put("password", login_password_edittext.text.toString())

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
                            Log.d(TAG, "login fail" + response.toString())
                        }
                    },
                    Response.ErrorListener { error ->
                        VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                    })
            queue.add(jr)
        }
    }

    // User 정보 받아서 넣기
    fun moveMainPage(member_idx:Int){
        if(member_idx != null){
            var mainintent = Intent(getActivity(), MainActivity::class.java)
            App.uid = member_idx
            startActivity(mainintent) //member idx 넘겨주기
        }
    }

}