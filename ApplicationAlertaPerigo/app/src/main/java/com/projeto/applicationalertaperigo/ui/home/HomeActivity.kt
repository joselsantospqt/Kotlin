package com.projeto.applicationalertaperigo.ui.home

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.projeto.applicationalertaperigo.R
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.projeto.applicationalertaperigo.databinding.ActivityHomeBinding
import com.projeto.applicationalertaperigo.ui.login.LoginActivity
import com.projeto.applicationalertaperigo.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*

lateinit var mDrawerLayout: DrawerLayout

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    private val viewModel: HomeViewModel by viewModels()
    private val REQUEST_CODE_CALL = 2
    val call_permission_code = 800


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setup()
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
    }

    private fun setup() {
        onCreateFloatingMenu()
    }

    private fun onCreateFloatingMenu() {
        mDrawerLayout = binding.drawerLayout
        val toolbar = toolbar as Toolbar;
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        binding.navigationMenu.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
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
            R.id.menuItemPerfil -> {
                viewModel.NavegaFragment(2)
                mensagem = "Menu Pefil !"
                retorno = true
            }
            R.id.menuItemSair -> {
                Firebase.auth.signOut()
                val intent = Intent(this, LoginActivity::class.java)
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
            R.id.navigation_item_Home -> {
                viewModel.NavegaFragment(1)
                retorno = true
            }
            R.id.navigation_item_Cadastrar -> {
                viewModel.NavegaFragment(3)
                retorno = true
            }
            R.id.navigation_item_Listar -> {
                viewModel.NavegaFragment(4)
                retorno = true
            }
            R.id.navigation_item_Telefonar -> {

                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(Manifest.permission.CALL_PHONE),
                        call_permission_code
                    )
                    retorno = false
                } else {

                    val intent: Intent = Uri.parse("tel: 190").let { number ->
                        Intent(Intent.ACTION_CALL, number)
                    }
                    startActivityForResult(intent, REQUEST_CODE_CALL)
                    retorno = true
                }

            }
//            R.id.navigation_item_calculadora -> {
//                val intent =
//                    intent.setClassName(
//                        "com.android.calculator2",
//                        "com.android.calculator2.Calculator"
//                    )
//                startActivity(intent)
//                retorno = true
//            }
        }

        return retorno
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == call_permission_code)
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "A permiss??o foi concedida"   , Toast.LENGTH_LONG).show()
                val intent: Intent = Uri.parse("tel:(00)0000-0000").let { number ->
                    Intent(Intent.ACTION_CALL, number)}
                startActivityForResult(intent, REQUEST_CODE_CALL)
            }else
                Toast.makeText(this, "A permiss??o foi negada"   , Toast.LENGTH_LONG).show()

    }
}