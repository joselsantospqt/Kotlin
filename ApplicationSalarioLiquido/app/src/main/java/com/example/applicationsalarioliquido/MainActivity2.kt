package com.example.applicationsalarioliquido

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val file = File(this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "teste.txt")
        val load = FileInputStream(file)
        val bytes = String(load.readBytes())
        load.close()


        val textInput = findViewById<TextView>(R.id.textFileInput)
        textInput.setText(bytes);
    }
}