package com.example.applicationperfilinvestidor.ui_simulador

import android.content.Intent
import android.net.Uri
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
import com.example.applicationperfilinvestidor.ui_emprestimo.ActivityEmprestimo
import com.example.applicationperfilinvestidor.MainActivity
import com.example.applicationperfilinvestidor.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_simulador.*

lateinit var mDrawerLayout: DrawerLayout

class ActivitySimulador : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
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
        findViewById<NavigationView>(R.id.navigation_view_simulador)?.setNavigationItemSelectedListener(
            this
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_right, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var retorno = false
        var mensagem = ""

        when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START);
                mensagem = "Menu Aberto !"
                retorno = true
            }
            R.id.menuItemSair -> {

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                mensagem = "Voc?? Saiu !"
                retorno = true
            }
        }
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
        return retorno
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var retorno = false

        when (item.itemId) {
            R.id.navigation_item_emprestimo -> {
                val intent = Intent(this, ActivityEmprestimo::class.java)
                startActivity(intent)
                retorno = true
            }
            R.id.navigation_item_simulador -> {
                val intent = Intent(this, ActivitySimulador::class.java)
                startActivity(intent)
                retorno = true
            }
            R.id.navigation_item_Telefonar -> {
                val intent: Intent = Uri.parse("tel:(00)0000-0000").let { number ->
                    Intent(Intent.ACTION_CALL, number)
                }
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                retorno = true
                }
                else
                    retorno = false
            }
            R.id.navigation_item_calculadora -> {
                val intent =
                    intent.setClassName(
                        "com.android.calculator2",
                        "com.android.calculator2.Calculator"
                    )
                startActivity(intent)
                retorno = true
            }
        }

        return retorno
    }

}

