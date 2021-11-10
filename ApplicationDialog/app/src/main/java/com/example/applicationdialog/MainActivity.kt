package com.example.applicationdialog

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, DialogInterface.OnClickListener,
    DatePickerDialog.OnDateSetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAlertDialog = findViewById<Button>(R.id.btnAlertDialog)
        btnAlertDialog.setOnClickListener(this)

        val btnProgressBar = findViewById<Button>(R.id.btnProgressBar)
        btnProgressBar.setOnClickListener(this)

        val btnDatePicker = findViewById<Button>(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener(this)

        val btnSnackBar = findViewById<Button>(R.id.btnSnackBar)
        btnSnackBar.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        val button = view as Button
        when(button.text){
            "Alert Dialog" -> alertDialogTest()
            "ProgressBar" -> ProgressBarTest()
            "DatePicker" -> datePickerTeste()
            "SnackBar" -> SnackBarTeste()
            "Clique Aqui" -> Toast.makeText(this, "SnackBar Clicado", Toast.LENGTH_LONG).show()
        }
    }

    private fun SnackBarTeste() {
      var snackBar = Snackbar
          .make(findViewById(R.id.pnlMain), "Teste de SnackBar", Snackbar.LENGTH_LONG)
          .setActionTextColor(Color.RED)
          .setAction("Clique Aqui", this)
          .show()
    }

    private fun datePickerTeste() {
        val calendar = Calendar.getInstance()
        val dia = calendar.get(Calendar.DAY_OF_MONTH)
        val mes = calendar.get(Calendar.MONTH)
        val ano = calendar.get(Calendar.YEAR)

        val datePicker = DatePickerDialog(this, this, ano, mes, dia).show()

    }

    private fun ProgressBarTest() {
       val prgProgressBar = findViewById<ProgressBar>(R.id.prgProgressBar)
       val lblProgressBar = findViewById<TextView>(R.id.lblProgressBar)
        //lambda com thread
        Thread({
            for(i in 0..100){
                this@MainActivity.runOnUiThread{
                    prgProgressBar.progress = i
                    lblProgressBar.text = i.toString() + "%"
                }
                Thread.sleep(200)
            }
        }).start()

    }

    private fun alertDialogTest() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Alerta")
        alertDialog.setMessage("Essa é uma mensagem de alerta bem simples")
        alertDialog.setPositiveButton("Ok", this)
        alertDialog.setNegativeButton("Cancelar", this)
        alertDialog.setCancelable(false)//alerta modal
        alertDialog.show()
    }

    override fun onClick(dialog: DialogInterface?, id: Int) {
       val alertDialog = dialog as AlertDialog // Cast implicito
        val rotuloBotao = alertDialog.getButton(id).text

        Toast.makeText(this, "Botao " + rotuloBotao + " Foi clicado", Toast.LENGTH_SHORT).show()
    }

    override fun onDateSet(dialog: DatePicker?, ano: Int, mes: Int, dia: Int) {
        //trabalhando com expressão antes de converter em string
        Toast.makeText(this, "Data Selecionada $dia/${mes+1}/$ano", Toast.LENGTH_SHORT).show()
    }
}