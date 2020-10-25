package com.sosin.easyfree.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sosin.easyfree.MainActivity
import com.sosin.easyfree.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

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

    var auth : FirebaseAuth? = null
//    로그인 시, 메인화면 전환
    fun singinEmail(){
        Toast.makeText(activity, "Login Message" + login_email_edittext.text.toString() + login_password_edittext.text.toString(), Toast.LENGTH_SHORT).show()
//        로그인 인증
        if(login_email_edittext.text.toString() == "sosin" && login_password_edittext.text.toString() == "123123"){
            moveMainPage()
        }

//        auth?.signInWithEmailAndPassword(login_email_edittext.text.toString(),
//            login_password_edittext.text.toString())
//            ?.addOnCompleteListener {
//                    task ->
//                if(task.isSuccessful){
//                    //Login
//                    moveMainPage(task.result!!.user)
//                }else{
//                    //Show the error
//                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_LONG).show()
//                }
//            }
    }

    // User 정보 받아서 넣기
    fun moveMainPage(){
//        if(user != null){
            startActivity(Intent(getActivity(), MainActivity::class.java))
//        }
    }


}