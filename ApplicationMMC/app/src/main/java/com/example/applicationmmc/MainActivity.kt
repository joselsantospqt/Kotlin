package com.example.applicationmmc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var btn_calcular: Button
    private lateinit var IMV: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, MainActivity2::class.java)
        btn_calcular = findViewById<Button>(R.id.buttonCalcular)
        btn_calcular.setOnClickListener {
            val peso = findViewById<EditText>(R.id.editTextPeso).text.toString()
            val altura = findViewById<EditText>(R.id.editTextAltura).text.toString()
            if (peso == "" || altura == "") {
                Toast.makeText(
                    applicationContext,
                    "OS CAMPOS SÃO OBRIGATORIOS",
                    Toast.LENGTH_LONG
                ).show()
            } else if (peso.toInt() <= 0 || altura.toFloat() <= 0) {
                Toast.makeText(
                    applicationContext,
                    "OS CAMPOS NÃO PODEM POSSUIR VALORES IGUAIS A ZERO",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val imc = (peso.toInt() / (altura.toDouble() * altura.toDouble()))
                if (imc < 18.5) {
                    IMV = "Peso abaixo do normal : IMC = $imc"
                } else if ((imc > 18.5) && (imc < 25.0)) {
                    IMV = "Peso normal : IMC = $imc"
                }
                if ((imc > 25) || (imc == 30.0)) {
                    IMV = "Sobre o Peso : IMC = $imc"
                } else if ((imc > 30) || (imc == 35.0)) {
                    IMV = "Grau de Obesidade I : IMC = $imc"
                }
                if ((imc > 35) || (imc == 40.0)) {
                    IMV = "Grau de Obesidade II : IMC = $imc"
                } else if (imc > 40) {
                    IMV = "Obesidade Grau III : IMC = $imc"
                }
                intent.putExtra("IMC", IMV.toString())
                startActivity(intent)
            }

        }


    }
}