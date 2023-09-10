package com.example.proteus1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

enum class ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    //Referencia como constante privada a la base de datos
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)//Establece el tema
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



        Emailtxtv.text=email
        Protxtv.text=provider

        logoutButton.setOnClickListener(){
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }


        saveButton.setOnClickListener(){
            if (Usertxtv.text.isNotEmpty()  && teltxtv.text.isNotEmpty()){
      db.collection("usuarios").document(email).set(
          hashMapOf("proveedor" to provider, "nombre" to Usertxtv.text.toString(),"telefono" to teltxtv.text.toString())
      )
            guardar()
            limpiar()

        }  else {

            showAlert()
        }
        }


        RecButton.setOnClickListener(){
               db.collection("usuarios").document(email).get().addOnSuccessListener {
                   Usertxtv.setText(it.get("nombre")as String?)
                   teltxtv.setText(it.get("telefono")as String?)
               }
        }
        DelButton.setOnClickListener(){
           db.collection("usuarios").document(email).delete()
            eliminar()
            limpiar()

        }


    }

    private fun guardar(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Guardado")
        builder.setMessage("Se han guardado correctamente los datos")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun eliminar(){

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminado")
        builder.setMessage("Se han eliminado correctamente los datos")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
   private fun limpiar() {
        val Usertxtv=findViewById<TextView>(R.id.UserTxtv)
        val teltxtv=findViewById<TextView>(R.id.telTxtv)
        Usertxtv.text = ""
        teltxtv.text=""
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("No se permiten registros nulos")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}