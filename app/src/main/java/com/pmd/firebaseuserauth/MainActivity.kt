package com.pmd.firebaseuserauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
// import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            registerUser(email, password)
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            signIn(email, password)

        }

        btnLogout.setOnClickListener {
            logout()
        }
        updateCurrentUser()
        fetchUsers()
    }

    private fun registerUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    Toast.makeText(this, "User Registered", Toast.LENGTH_LONG).show()
                    createUser(email)
                    clearInputs()
                }
                else -> {
                    Toast.makeText(this, "User Registration failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    Toast.makeText(this, "User Login Successful", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "User Login failed", Toast.LENGTH_LONG).show()
                }
            }
            updateCurrentUser()
            clearInputs()
        }
    }

    private fun insertUser(user: User) {
        val userRef = dbFirestore.collection("users")

        userRef.document().set(user).addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    Toast.makeText(this, "User Post Successful", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "User Post failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun fetchUsers() {
        val userRef = dbFirestore.collection("users")

        userRef.get().addOnCompleteListener {
            when {
                it.isSuccessful -> {
                    Toast.makeText(this, "User Fetch Successful", Toast.LENGTH_LONG).show()
                    val users: ArrayList<User> = ArrayList()

                    for(document in it.result!!.documents) {
                        val user = document.toObject(User::class.java)!!
                        users.add(user)
                        println("Email: " + user.email)
                    }
                }
                else -> {
                    Toast.makeText(this, "User Fetch failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun createUser(email: String) {
        val user = User()
        user.email = email

        insertUser(user)

    }



    private fun updateCurrentUser() {
        val currentUser = mAuth.currentUser

        tvCurrentUser.text = String.format("Current User: %s", currentUser?.email)
    }

    private fun logout() {
        mAuth.signOut()
        updateCurrentUser()
    }

    private fun clearInputs() {
        etEmail.text.clear()
        etPassword.text.clear()
    }

}
