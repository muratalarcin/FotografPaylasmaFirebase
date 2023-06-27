package com.muratalarcin.fotografpaylasmafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.muratalarcin.fotografpaylasmafirebase.databinding.ActivityKullaniciBinding

class KullaniciActivity : AppCompatActivity() {
    //lateinit var binding: ActivityKullaniciBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityKullaniciBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() // firebaseyi başlatıyoruz

    //E MAİL VE ŞİFREYİ TIKLAMA ESNASINDA AL YOKSA , NULL SANIYO VE HATA VERİYO !!!!


        //giriş yaptıysam kullanıcıyı açık bırak
        val guncelKullanici = auth.currentUser
        if(guncelKullanici != null){
            intent = Intent(this, HaberlerActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.kayyitOlButton.setOnClickListener {
            val email = binding.girisEmail.text.toString()
            val parola = binding.girisParola.text.toString()
            if (email.isEmpty() || parola.isEmpty()) {
                Toast.makeText(applicationContext, "Email and password cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
                auth.createUserWithEmailAndPassword(email, parola).addOnCompleteListener{ task ->
                    //add complete falan ekleyince asenkron çalışıyo kod, misal e mail ve şifreyi sisteme internetle yolladı geçikme olucak illaki
                    // o gecikmeyi hesaba katmalıyız, bundan dolayı misal işlem bittiğinde kodlamaya devam etmeliyim gibi
                    if (task.isSuccessful){
                        val intent = Intent(applicationContext, HaberlerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener{exception -> // fail alırsak
                    Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }


        }

        binding.girisButton.setOnClickListener {
            val email = binding.girisEmail.text.toString()
            val parola = binding.girisParola.text.toString()
            if(email.isEmpty() || parola.isEmpty()){
                Toast.makeText(applicationContext, "Email and password cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
                auth.signInWithEmailAndPassword(email, parola).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val guncelkullanici = auth.currentUser?.email.toString()
                        Toast.makeText(applicationContext, " $guncelkullanici Hoş Geldin.. ", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, HaberlerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }

    }

}