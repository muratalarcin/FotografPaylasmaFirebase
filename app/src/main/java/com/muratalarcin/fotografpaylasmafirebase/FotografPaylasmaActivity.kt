package com.muratalarcin.fotografpaylasmafirebase

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.muratalarcin.fotografpaylasmafirebase.databinding.ActivityFotografPaylasmaBinding

class FotografPaylasmaActivity : AppCompatActivity() {
    var secilenGorsel : Uri? = null
    var secilenBitmap : Bitmap? = null

    lateinit var binding: ActivityFotografPaylasmaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFotografPaylasmaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setOnClickListener {

            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //yukarıdaki if blogu izin almadığımızı anlıyo
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }else {
                //izin zaten varsa yapılacaklar
                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent, 2)
            }

    }

}
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            //izin alınınca yapılacaklar
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data !=null){
            secilenGorsel = data.data

            if (secilenGorsel != null){

                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

                    val source = ImageDecoder.createSource(this.contentResolver, secilenGorsel!!)
                     secilenBitmap = ImageDecoder.decodeBitmap(source)
                     binding.imageView.setImageBitmap(secilenBitmap)
                } else {
                    secilenBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, secilenGorsel)
                     binding.imageView.setImageBitmap(secilenBitmap)
                }
            }

        }


        super.onActivityResult(requestCode, resultCode, data)
    }

}



