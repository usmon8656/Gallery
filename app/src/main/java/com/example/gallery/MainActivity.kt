package com.example.gallery

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.gallery.databinding.ActivityMainBinding
import com.example.gallery.databinding.ItemDialogBinding
import com.example.gallery.models.MyImage
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var myDbHelper:MyDbHelper
    private lateinit var myViewPagerAdapter: MyViewPagerAdapter
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myDbHelper= MyDbHelper(this)
        myViewPagerAdapter=MyViewPagerAdapter()
        binding.viewPager.adapter=myViewPagerAdapter
        myViewPagerAdapter.list.addAll(myDbHelper.getImage())
        binding.floatBtn.setOnClickListener {
            showDialog()
        }



    }
private  lateinit var  itemDialog:ItemDialogBinding
    fun showDialog(){
        val dialog = BottomSheetDialog(this)
        itemDialog = ItemDialogBinding.inflate(layoutInflater)
        dialog.setContentView(itemDialog.root)
        dialog.show()

        itemDialog.imageDialog.setOnClickListener {
            myImageContent.launch("video/*")
        }

        itemDialog.btnDialog.setOnClickListener {
            if (filePath!=""  && itemDialog.edtDialog.text.isNotBlank()){
                val myImage = MyImage(itemDialog.edtDialog.text.toString().trim(), filePath)
                myDbHelper.addImage(myImage)
                dialog.cancel()
                myViewPagerAdapter.list.add(myImage)
                myViewPagerAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(this, "Avval malumot kiriting", Toast.LENGTH_SHORT).show()
            }
        }

    }

    var filePath:String=""
    private val myImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()){
            if (it==null){
                return@registerForActivityResult
            }
            //binding.img.setImageURI(it)
            val inputStream = contentResolver.openInputStream(it)
            val title = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val file = File(filesDir , "$title.jpg")
            val outPutStream = FileOutputStream(file)
            inputStream?.copyTo(outPutStream)
            inputStream?.close()
            outPutStream.close()
            filePath=file.absolutePath
            itemDialog.imageDialog.setVideoURI(it)
            itemDialog.imageDialog.start()
        }
}