package com.example.applicationmenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    val inflater : MenuInflater =  menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var retorno  = false
        var mensagem =  ""
        when(item.itemId){
            R.id.mnuMinhaopcao ->{
                mensagem = "Outra opção foi selecionada"
                retorno = true
            }
            R.id.mneuOutraOpcao ->{
                mensagem = "Outra opção foi selecionada"
                retorno = true
            }
            R.id.mneuUltimaOpcao ->{
                mensagem = "Outra opção foi selecionada"
                retorno = true
            }
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
        return  retorno
    }
}