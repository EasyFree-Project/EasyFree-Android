package com.sosin.easyfree.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sosin.easyfree.MainActivity
import com.sosin.easyfree.R
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_signup, container, false)

        view.findViewById<Button>(R.id.check_id_btn).setOnClickListener{
            checkId()
        }
        view.findViewById<Button>(R.id.signup_button).setOnClickListener{
            singUp()
        }
        return view
    }

    var auth : FirebaseAuth? = null
    fun checkId(){
        Toast.makeText(activity, "Check ID Message" + signup_email_edittext.text.toString(),Toast.LENGTH_SHORT).show()
    }

//    회원가입 시, 메인화면으로 전환
    fun singUp(){
        Toast.makeText(activity, "Sign Up Message" + signup_email_edittext.text.toString() + signup_password1_edittext.text.toString() +
                signup_password2_edittext.text.toString(), Toast.LENGTH_SHORT).show()
//        회원 가입 후 메인화면 전환
//        auth?.createUserWithEmailAndPassword(login_email_edittext.text.toString(),
//            login_password_edittext.text.toString())
//            ?.addOnCompleteListener {
//                    task ->
//                if(task.isSuccessful){
//                    //Creating a user account
//                    moveMainPage(task.result!!.user)
//                }else if(task.exception?.message.isNullOrEmpty()) {
//                    //Show the error message
//                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_LONG).show()
//                }
//            }
    }

    fun moveMainPage(user: FirebaseUser?){
        if(user != null){
            startActivity(Intent(getActivity(), MainActivity::class.java))
        }
    }

}