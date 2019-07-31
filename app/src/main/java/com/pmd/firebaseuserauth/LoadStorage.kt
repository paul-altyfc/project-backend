package com.pmd.firebaseuserauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dmax.dialog.SpotsDialog

class LoadStorage : AppCompatActivity() {

    // declare the storage field
    lateinit var storage: FirebaseStorage
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_storage)

        // initialise the storage
        storage = FirebaseStorage.getInstance()
//        alertDialog = SpotsDialog.Builder().setContext(this).build()

        storageCreateReference()
    }

    private fun storageCreateReference(){

        // create a storage reference for the application
        var storageRef = storage.reference

        // create a child reference for images
        var imageRef: StorageReference? = storageRef.child("images")



    }
}
