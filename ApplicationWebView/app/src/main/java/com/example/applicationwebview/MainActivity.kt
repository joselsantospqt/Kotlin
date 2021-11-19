package com.example.applicationwebview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var retorno = false
        var url = ""
        when(item.itemId){
            R.id.mnuGoogle ->{
                url = "http://google.com"
                retorno = true
            }
            R.id.mnuGoogle ->{
                url = "http://yahoo.com"
                retorno = true
            }
        }

        var wvMain = findViewById<WebView>(R.id.wvMain)
        wvMain.webViewClient = WebViewClient()
        wvMain.loadUrl(url)
        Toast.makeText(this, url, Toast.LENGTH_LONG).show()
        return retorno
    }
}