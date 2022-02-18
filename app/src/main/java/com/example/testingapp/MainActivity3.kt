package com.example.testingapp

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.testingapp.databinding.ActivityMain2Binding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.M)
class MainActivity3 : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private val requestCodePermission = 124
    private lateinit var relativePath: String
    private var message = ""
    private lateinit var stream: OutputStream
    private lateinit var photoURI: Uri
    private var tag = "MainActivity3"

    override fun onClick(v: View?) {
        when (v) {
            binding.camBtn -> {
                camAct()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()
        binding.camBtn.setOnClickListener(this)

        // Nanti coba hapus strict mode, apakah bisa ?
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        permissionCameraAndFile()
        proCam()
    }

    private fun save() {
        stream = ByteArrayOutputStream()
        val bitmap = BitmapFactory.decodeFile(photoURI.path)
        val saved = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        if (!saved) {
            Log.e("error", "Failed to save bitmap.")
        }
        binding.imageShow.setImageBitmap(bitmap)
    }

    private fun camAct() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val filePhoto = createImageFile()
            photoURI = FileProvider.getUriForFile(
                this,
                "com.example.testingapp.fileprovider",
                filePhoto
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(tag, "message : ${e.message}")
        }
        if (intent.resolveActivity(packageManager) != null) {
            activityResultLauncher.launch(intent)
        } else {
            Toast.makeText(this, "There is no app that support this action", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun proCam() {
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            when (it.resultCode) {
                RESULT_OK -> {
                    save()
                }
                Activity.RESULT_CANCELED -> {
                    Log.e(tag, "User Cancelled Image Capture")
                }
                else -> {
                    Log.e(tag, "Failed to Capture Image")
                }
            }
        }
    }


    /**
     * Permission Area and createImage for android R
     */


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val df =
            SimpleDateFormat(resources.getString(R.string.formatDateName), Locale.getDefault())
        val fileName = df.format(Calendar.getInstance().time)
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            fileName, /* prefix */
            ".JPEG", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            relativePath = absolutePath
        }
    }


    private fun addPermission(permissionsList: MutableList<String>, permission: String): Boolean {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission)
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false
        }
        return true
    }

    private fun permissionCameraAndFile() {
        val permissionsNeeded = ArrayList<String>()

        val permissionsList = ArrayList<String>()
        if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
            permissionsNeeded.add("CAMERA")
        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE")
        if (!addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("READ_EXTERNAL_STORAGE")
        if (permissionsList.size > 0) {
            if (permissionsNeeded.size > 0) {
                message = resources.getString(R.string.alert_permission)
                showMessageOKCancel(message,
                    { _, _ ->
                        requestPermissions(
                            permissionsList.toTypedArray(),
                            requestCodePermission
                        )
                    }, { _, _ -> finish() })
                return
            }
            requestPermissions(
                permissionsList.toTypedArray(),
                requestCodePermission
            )
            return
        }
    }

    private fun showMessageOKCancel(
        message: String,
        okListener: DialogInterface.OnClickListener,
        cancel: DialogInterface.OnClickListener
    ) {
        val adb = MaterialAlertDialogBuilder(this, R.style.alertDialog)
        with(adb) {
            setMessage(message)
            setPositiveButton(resources.getString(R.string.setuju), okListener)
            setNegativeButton(resources.getString(R.string.batal), cancel)
        }
        val dialog = adb.create()
        dialog.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            requestCodePermission -> {
                val perms = HashMap<String, Int>()
                // Initial

                perms[android.Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                perms[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] =
                    PackageManager.PERMISSION_GRANTED
                perms[android.Manifest.permission.READ_EXTERNAL_STORAGE] =
                    PackageManager.PERMISSION_GRANTED
                if (perms[android.Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                    && perms[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                    && perms[android.Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                ) {
                    Log.e(tag, "onRequestPermissionsResult OK")
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}