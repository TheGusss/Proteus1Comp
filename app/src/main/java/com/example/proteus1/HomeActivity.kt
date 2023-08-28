package com.example.proteus1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

            //setup
        val bundle=intent.extras
        val email=bundle?.getString("email")
        val provider=bundle?.getString("provider")
        setup(email?:"", provider?:"")
    }

    private fun setup(email: String, provider:String){
        val Emailtxtv=findViewById<TextView>(R.id.Emailtxtv)
        val Protxtv=findViewById<TextView>(R.id.Providertxtv)
        val Usertxtv=findViewById<TextView>(R.id.UserTxtv)
        val teltxtv=findViewById<TextView>(R.id.telTxtv)
        val logoutButton=findViewById<Button>(R.id.logoutButton)
        val saveButton=findViewById<Button>(R.id.saveButton)
        val RecButton=findViewById<Button>(R.id.recButton)
        val DelButton=findViewById<Button>(R.id.DelButton)

        title="Inicio"

        Emailtxtv.text=email
        Protxtv.text=provider

        logoutButton.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        saveButton.setOnClickListener(){
      db.collection("usuarios").document(email).set(
          hashMapOf("proveedor" to provider, "nombre" to Usertxtv.text.toString(),"telefono" to teltxtv.text.toString())
      )
        }
        RecButton.setOnClickListener(){
               db.collection("usuarios").document(email).get().addOnSuccessListener {
                   Usertxtv.setText(it.get("nombre")as String?)
                   teltxtv.setText(it.get("telefono")as String?)
               }
        }
        DelButton.setOnClickListener(){
           db.collection("usuarios").document(email).delete()
        }
    }
}