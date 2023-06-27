package com.muratalarcin.fotografpaylasmafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.muratalarcin.fotografpaylasmafirebase.databinding.ActivityHaberlerBinding

class HaberlerActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    // lateinit var binding: ActivityHaberlerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHaberlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() // firebaseyi başlatıyoruz


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.secenekler_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.fotografPaylas){
            val intent = Intent(this, FotografPaylasmaActivity::class.java)
            startActivity(intent)
            finish()
        }else if(item.itemId == R.id.cıkısYap){
           auth.signOut()
            val intent = Intent(this, KullaniciActivity::class.java)
            startActivity(intent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

}
