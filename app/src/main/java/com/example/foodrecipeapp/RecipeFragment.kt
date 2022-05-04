package com.example.foodrecipeapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.fragment_recipe.*
import java.lang.Exception


class RecipeFragment : Fragment() {
    var selectedPicture:Uri?=null
    var selectedBitmap:Bitmap?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button.setOnClickListener {
            save()
        }

        imageView.setOnClickListener {
            pictureSelect()
        }
    }

        fun save()
        {
            val foodname=foodNameText.text.toString()
            val foodIngredient=foodIngredientText.text.toString()
            if(selectedBitmap!=null)
            {
                val copressedBitmap=compressBitmap(selectedBitmap!!,300,)
            }
        }
        fun pictureSelect()
        {
                        activity?.let {
                            if (ContextCompat.checkSelfPermission(it.applicationContext,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
                            {
                                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
                            }
                            else{
                                var galleryIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                                startActivityForResult(galleryIntent,2)
                            }
                        }


        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==1)
        {
            if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED )
            {
                var galleryIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galleryIntent,2)
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==2&&resultCode==Activity.RESULT_OK&&data!=null){
            selectedPicture=data.data
            try{
                context?.let {
                    if(selectedPicture!=null)
                    {
                        if(Build.VERSION.SDK_INT>=28)
                        {
                            val source=ImageDecoder.createSource(it.contentResolver,selectedPicture!!)
                            selectedBitmap=ImageDecoder.decodeBitmap(source)
                            imageView.setImageBitmap(selectedBitmap)
                        }
                        else{
                            selectedBitmap=MediaStore.Images.Media.getBitmap(it.contentResolver,selectedPicture)
                            imageView.setImageBitmap(selectedBitmap)
                        }

                    }
                }


            }
            catch (e:Exception){
                e.printStackTrace()
            }



        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun compressBitmap(userSelectedBitmap:Bitmap,maxSize:Int):Bitmap
    {    var width=userSelectedBitmap.width
         var height=userSelectedBitmap.height
         val bitmapRatio:Double=width.toDouble()/height.toDouble()
        if(bitmapRatio>1)

        {
            width=maxSize
            val compressHeight=width/bitmapRatio
            height=compressHeight.toInt()

        }
        else
        {
            height=maxSize
            val compressWidth=height*bitmapRatio
            width=compressWidth.toInt()

        }
            return Bitmap.createScaledBitmap(userSelectedBitmap,width,height,true)


    }
}