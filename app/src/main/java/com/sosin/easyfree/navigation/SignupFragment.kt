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

    fun moveMainPage(responseCode: Int){
        if(responseCode == 200){
            startActivity(Intent(getActivity(), MainActivity::class.java))
        }
    }

    fun signUp(){
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
        params.put("username", "onapp")
        params.put("password", "123123")
        params.put("displayName", "jaejin")

        var jr = JsonObjectRequest(
            Request.Method.POST,
            getString(R.string.signup_url),
            params,
            Response.Listener<JSONObject> { response ->
                Log.d(TAG, "/post request OK! Response: $response")
            },
            Response.ErrorListener { error ->
                VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
            })
//            {
//            @Throws(AuthFailureError::class)
//            fun getHeaders(): Map<String, String> {
//                val headers = HashMap<String, String>()
//                headers.put("Content-Type", "application/json")
//                return headers
//            }

        queue.add(jr)
    }

    override fun onStop() {
        super.onStop()
//        queue?.cancelAll(TAG)
    }

    fun sendcall() {
        //RequestQueue initialized
        var mRequestQueue = Volley.newRequestQueue(activity)

        //String Request initialized
        var mStringRequest = object : StringRequest(Request.Method.POST, getString(R.string.signup_url), Response.Listener { response ->
            Toast.makeText(activity, "Logged In Successfully" + response.toString(), Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener { error ->
            Log.d("This is the error", "Error :" + error.toString())
            Toast.makeText(activity, "Please make sure you enter correct password and username", Toast.LENGTH_SHORT).show()
        }) {
            override fun getBodyContentType(): String {
                return "application/json"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                val params2 = HashMap<String, String>()
                params2.put("username", "onapp")
                params2.put("password", signup_password1_edittext.text.toString())
                params2.put("displayName", signup_displayName_edittext.text.toString())
                Log.d("This is the error", JSONObject(params2 as Map<*, *>).toString())
                return JSONObject(params2 as Map<*, *>).toString().toByteArray()
            }

        }
        mRequestQueue!!.add(mStringRequest!!)
    }

}