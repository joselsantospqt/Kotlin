package com.example.applicationmenucontextual

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lblTitulo = findViewById<TextView>(R.id.lblTitulo)
        registerForContextMenu(lblTitulo)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.menu_contextual, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var retorno = false
        var mensagem = ""
        when(item.itemId){
            R.id.mnuEditar ->{
                mensagem = "Estou Editando o item Clicado"
                retorno = true
            }
            R.id.mneuDeletar -> {
                mensagem = "Estou Deletando o item Clicado"
                retorno = true
            }
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
        return retorno
    }
}