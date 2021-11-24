package com.example.applicationquemvotei

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPageTela)
        viewPager.adapter = TabAdapter(this)
        TabLayoutMediator(tabLayout, viewPager){
            tab, position ->
                viewPager.setCurrentItem(tab.position, true)
            if (position == 2)
                tab.setText("Perfil")
            if (position == 1)
                tab.setText("Dados")
            if (position == 0)
                tab.setText("Home")
        }

    }
}