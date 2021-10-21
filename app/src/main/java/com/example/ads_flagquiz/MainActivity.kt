package com.example.ads_flagquiz

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var paises : ArrayList<Locale>
    private lateinit var botoes : Array<Button>
    private var botaoRespostaCerta = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //carrega lista de paises
        paises = ArrayList()
        for(locale in Locale.getAvailableLocales()){
            if(locale.country.length == 2){
                paises.add(locale)
            }
        }
        //monta os botões para as perguntas
        botoes = arrayOf(findViewById<Button>(R.id.btn1), findViewById<Button>(R.id.btn2), findViewById<Button>(R.id.btn3))
        for(i in 0..2){
            botoes[i].setText("")
            botoes[i].setOnClickListener(this)
        }
        sorteiaPais()
    }

    override fun onClick(view: View?) {
        val botao : Button = view as Button
        val lblResposta = findViewById<TextView>(R.id.lblResposta)
        if(botoes[botaoRespostaCerta] == botao){
            lblResposta.setTextColor(Color.GREEN)
            lblResposta.setText(botao.text.toString() + " - CORRETO!")
        }else{
            lblResposta.setTextColor(Color.RED)
            lblResposta.setText(botao.text.toString() + " - ERRADO!")
        }
        sorteiaPais()
    }
    //Sorteio de 3 inteiros correspondentes aos indices dos paises do array de locales
    private fun sorteiaPais(){
        var rodada = IntArray(3)
        for(i in 0..2){
            var numero = 0
            var ok = false

            while(!ok){
                numero = (Math.random() * paises.size - 1).roundToInt()
                ok = true

                for(j in 0..2){
                    if(rodada[j] == numero){
                        ok = false
                        break
                    }
                }

                if(ok){
                    val uri = "@drawable/" + paises.get(numero).country.lowercase()
                    val imageResourceId = this.resources.getIdentifier(uri, "drawable", packageName)
                    if(imageResourceId == 0){
                        ok = false
                    }
                }
            }
            rodada[i] = numero
            botaoRespostaCerta = (Math.random() * 2).roundToInt()
            val uri = "@drawable/" + paises.get(numero).country.lowercase()
            val imageResourceId = this.resources.getIdentifier(uri, "drawable", packageName)
            val imgBandeira = findViewById<ImageView>(R.id.imageView)
            imgBandeira.setImageDrawable(resources.getDrawable(imageResourceId))
            for(i in 0..2){
                botoes[i].setText(paises[rodada[i]].displayCountry)
            }
        }
    }
}