package com.example.applicationperfilinvestidor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.applicationperfilinvestidor.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_simulador.*

lateinit var mDrawerLayout : DrawerLayout

class ActivitySimulador : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulador)
        onCreateFloatingMenu()
    }

    private fun onCreateFloatingMenu() {
        mDrawerLayout = drawer_layout
        val toolbar = toolbar as Toolbar;
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater =  menuInflater
        inflater.inflate(R.menu.menu_right, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var retorno  = false
        var mensagem =  ""

        when(item.itemId){
            android.R.id.home ->{
                mDrawerLayout.openDrawer(GravityCompat.START);
                retorno = true
            }
            R.id.menuItemConfiguracao ->{
                mensagem = "Outra opção foi selecionada"
                retorno = true
            }
            R.id.menuItemSair ->{
                mensagem = "Outra opção foi selecionada"
                retorno = true
            }
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
        return  retorno
    }

}

