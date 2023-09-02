package com.example.proteus1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class accessActivity : AppCompatActivity() {

    lateinit var logingButton: Button
    lateinit var editEmail1: EditText
    lateinit var editPass1: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_access)

        // Inicializa las propiedades después de llamar a setContentView
        logingButton = findViewById(R.id.accederbtn)
        editEmail1 = findViewById(R.id.editTextTextEmail)
        editPass1 = findViewById(R.id.editTextTextPassword)

        // Configura los clics de los botones
        setup()
    }

    private fun setup() {


        logingButton.setOnClickListener {
            if (editEmail1.text.isNotEmpty() && editPass1.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    editEmail1.text.toString(),
                    editPass1.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        limpiaraut()
                        showhome(it.result?.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    private fun limpiaraut() {
        editEmail1.setText("")
        editPass1.setText("")
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showhome(email: String, provider: ProviderType) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
        }
        startActivity(homeIntent)
    }
}