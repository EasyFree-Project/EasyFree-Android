package com.sosin.easyfree.navigation

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.*
import com.android.volley.toolbox.*
import com.sosin.easyfree.MainActivity
import com.sosin.easyfree.R
import kotlinx.android.synthetic.main.fragment_signup.*
import org.json.JSONObject
import javax.xml.transform.ErrorListener


class SignupFragment : Fragment() {

    val TAG = "SIGNUP"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_signup, container, false)
        var queue : RequestQueue = Volley.newRequestQueue(activity);

        view.findViewById<Button>(R.id.check_id_btn).setOnClickListener{
            checkId()
        }
        view.findViewById<Button>(R.id.signup_button).setOnClickListener{
            signUp()
//            sendcall()
        }

        return view
    }

    fun checkId(){
        Toast.makeText(
            activity,
            "Check ID Message" + signup_email_edittext.text.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun moveMainPage(member_idx: Int){
        if(member_idx != null){
            var mainintent = Intent(getActivity(), MainActivity::class.java)
            mainintent.putExtra("uid", member_idx.toString())
            startActivity(mainintent) // member_idx 넘겨주기
        }
    }

    fun signUp(){
        if(signup_password1_edittext.text.toString() == signup_password2_edittext.text.toString()){

            var queue = Volley.newRequestQueue(activity)
//        var stringRequest = StringRequest(
//            Request.Method.POST,
//            getString(R.string.server_test),
//            object : Response.Listener<String?> {
//                override fun onResponse(response: String?) {
//                    signup_email_edittext.setText("POST 성공" + response.toString())
//                }
//            },
//            object : Response.ErrorListener {
//                override fun onErrorResponse(error: VolleyError?) {
//                    signup_displayName_edittext.setText(error.toString())
//                }
//            })

            val params = JSONObject()
            params.put("username", signup_email_edittext.text.toString())
            params.put("password", signup_password1_edittext.text.toString())
            params.put("displayName", signup_displayName_edittext.text.toString())

            var jr = JsonObjectRequest(
                    Request.Method.POST,
                    getString(R.string.signup_url),
                    params,
                    Response.Listener<JSONObject> { response ->
                        try{
                            var statusCode = response.getString("statusCode")
                            var message = response.getString("message")
                            var member_idx = response.getJSONObject("data").getString("member_idx")
                            moveMainPage(member_idx.toInt())
                        } catch (e : Exception){
                            Log.d(TAG, "signup fail" + response.toString())
                        }

                    },
                    Response.ErrorListener { error ->
                        VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                    })

            queue.add(jr)
        }
        else{
            Toast.makeText(activity, "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
//        queue?.cancelAll(TAG)
    }

}