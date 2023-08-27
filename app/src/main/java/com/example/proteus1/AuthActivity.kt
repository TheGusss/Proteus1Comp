package com.example.proteus1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(2000)
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //Analityc Event

        val analytics: FirebaseAnalytics=FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("Mesagge","Integracion de Firebase Completada")
        analytics.logEvent("InitScreen", bundle)


        //Setup
        setup()
    }
    private fun setup(){

        val signupButton = findViewById<Button>(R.id.saveButton)
        val logingButton = findViewById<Button>(R.id.logingbutton)
        val editEmail=findViewById<EditText>(R.id.editTextTextEmail)
        val editPass=findViewById<EditText>(R.id.editTextTextPassword)

        title="Autenticacion"
        signupButton.setOnClickListener {

            if (editEmail.text.isNotEmpty()  && editPass.text.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editEmail.text.toString(), editPass.text.toString()).addOnCompleteListener(){
                    if(it.isSuccessful){
                           showhome(it.result?.user?.email ?:"", ProviderType.BASIC)
                    } else{
                            showAlert()
                    }
                }


            }
        }

        logingButton.setOnClickListener {

            if (editEmail.text.isNotEmpty()  && editPass.text.isNotEmpty()){

                FirebaseAuth.getInstance().signInWithEmailAndPassword(editEmail.text.toString(),
                    editPass.text.toString()).addOnCompleteListener(){
                    if(it.isSuccessful){
                        showhome(it.result?.user?.email ?:"", ProviderType.BASIC)
                    } else{
                        showAlert()
                    }
                }


            }
        }

    }
    //funcion error
    private fun showAlert(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog = builder.create()
        dialog.show()
    }

    private fun showhome (email:String, provider:ProviderType){

    val homeIntent= Intent(this,HomeActivity::class.java).apply {
       putExtra("email",email)
        putExtra("provider", provider.name)
    }
        startActivity(homeIntent)
    }

}