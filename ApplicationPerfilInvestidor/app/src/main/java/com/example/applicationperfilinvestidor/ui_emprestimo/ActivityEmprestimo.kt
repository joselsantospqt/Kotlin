package com.example.applicationperfilinvestidor.ui_emprestimo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.applicationperfilinvestidor.MainActivity
import com.example.applicationperfilinvestidor.R
import com.example.applicationperfilinvestidor.ui_simulador.ActivitySimulador
import com.example.applicationperfilinvestidor.ui_simulador.mDrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_emprestimo.*
import kotlinx.android.synthetic.main.activity_emprestimo.toolbar
import kotlinx.android.synthetic.main.fragment_emprestimo0.*

class ActivityEmprestimo : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emprestimo)

        val nome = intent.getStringExtra("nome")
        val pontos = intent.getStringExtra("pontos")
        ListaEmprestimos.newInstance(nome.toString(), pontos.toString())

        onCreateFloatingMenu()
        findViewById<ViewPager2>(R.id.pagina_base).adapter = ScreenSlidePagerAdapter(this)
    }


    private fun onCreateFloatingMenu() {
        mDrawerLayout = drawer_layout_emprestimo
        val toolbar = toolbar as Toolbar;
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        //AQUI ESTOU DEIXANDO MARCADO E CRAVADO EM QUAL ITEM DO MENU ESTOU NAVEGANDO.
        navigation_view_emprestimo.getMenu().getItem(1).setChecked(true)
        //AQUI EU ESTOU PASSANDO PARA A FUNÇÃO onNavigationItemSelected O NAVIGATION VIEW
        findViewById<NavigationView>(R.id.navigation_view_emprestimo)?.setNavigationItemSelectedListener(
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
                mensagem = "Você Saiu !"
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
            R.id.navigation_item_calculadora -> {
                val intent =
                    intent.setAction(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_CALCULATOR)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
                retorno = true
            }
        }

        return retorno
    }

    class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 1
        override fun createFragment(position: Int): Fragment = ListaEmprestimos()
    }
}