package com.example.proteus1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth


class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1000) //Retardo de splash(No recomendado)
        setTheme(R.style.AppTheme)//Se utiliza splash al momento de cargar la app y Apptheme cuando carga

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //Analityc Event

        val analytics: FirebaseAnalytics=FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("Mesagge","Integracion de Firebase Completada")
        analytics.logEvent("InitScreen", bundle)


        //Setup
        setup()//Llama a la función setup
    }

    //Función que se encarga de configurar la lógica
    // se incluye la inicialización de los botones y la lógica para el registro de usuarios.
    private fun setup(){

        val signupButton = findViewById<Button>(R.id.saveButton)
        val editEmail=findViewById<EditText>(R.id.editTextTextEmail2)
        val editPass=findViewById<EditText>(R.id.editTextTextPassword2)


        signupButton.setOnClickListener {

            if (editEmail.text.isNotEmpty()  && editPass.text.isNotEmpty()){

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(editEmail.text.toString(), editPass.text.toString()).addOnCompleteListener(){
                    if(it.isSuccessful){
                           limpiaraut()
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

    private fun limpiaraut() {
        val Useremail = findViewById<EditText>(R.id.editTextTextEmail2)
        val Uderpass = findViewById<EditText>(R.id.editTextTextPassword2)
        Useremail.setText("")
        Uderpass.setText("")
    }

    fun irANuevaVista(view: View) {
        val intent = Intent(this, accessActivity::class.java)
        startActivity(intent)
    }
}

