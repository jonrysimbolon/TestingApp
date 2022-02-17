package com.example.testingapp

import android.app.Activity
import android.content.ContentValues
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
    private lateinit var relativePathQ: String
    private lateinit var fileRP: File
    private lateinit var fileRPQ: File
    private lateinit var oldLocatePhoto: String
    private lateinit var oldLocatePhotoQ: String
    private val mimeType = "image/*"
    private lateinit var bitmap: Bitmap
    private var message = ""
    private lateinit var stream: OutputStream
    private lateinit var uri: Uri
    private var camera = 1
    private lateinit var mediaFile: File
    private var TAG = "Mainactivity3"

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

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        relativePath = "${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/Testing2"
        relativePathQ = "${Environment.DIRECTORY_PICTURES}/Testing2"
        fileRP = File(relativePath)
        fileRPQ = File(relativePathQ)
        oldLocatePhoto = "${fileRP.path}${File.separator}myPhoto.JPEG"
        oldLocatePhotoQ = "${fileRPQ.path}${File.separator}myPhoto.JPEG"

        permissionCameraAndFile()
        proCam()
    }

    private fun save() {
        try {
            val df =
                SimpleDateFormat(resources.getString(R.string.formatDateName), Locale.getDefault())
            val fileName = df.format(Calendar.getInstance().time)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                setImageQ(fileName)
            } else {
                setImage(fileName)
            }

        } catch (e: IOException) {
            Log.e("Error", "message : ${e.message}")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentResolver.delete(uri, null, null)
            }
        }
    }


    private fun setImage(fileName: String) {
        val photo = BitmapFactory.decodeFile(oldLocatePhoto)
        val file = File(fileRP, "$fileName.JPEG")
        val bytes = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        file.createNewFile()
        val fo = FileOutputStream(file)
        fo.write(bytes.toByteArray())
        fo.close()

        val fileOld = File(oldLocatePhoto)
        fileOld.delete()

        binding.imageShow.setImageBitmap(photo)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setImageQ(fileName: String) {
        val photo = BitmapFactory.decodeFile(oldLocatePhotoQ)
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePathQ)

        val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        uri = contentResolver.insert(contentUri, contentValues)!!
        stream = contentResolver.openOutputStream(uri)!!

        val saved = photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        if (!saved) {
            Log.e("error", "Failed to save bitmap.")
        }
        binding.imageShow.setImageBitmap(photo)
    }

    private fun camAct() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutPutMediaFileUri(camera))
        if (intent.resolveActivity(packageManager) != null) {
            activityResultLauncher.launch(intent)
        } else {
            Toast.makeText(this, "There is no app that support this action", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getOutPutMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }

    private fun getOutputMediaFile(type: Int): File? {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            if (!fileRPQ.exists()) {
                if (!fileRPQ.mkdirs()) {
                    Log.e(
                        relativePathQ, "Oops! Failed create "
                                + relativePathQ + " directory"
                    )
                    return null
                }
            }
            if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) {
                mediaFile = File(oldLocatePhotoQ)
            }
        }else {
            if (!fileRP.exists()) {
                if (!fileRP.mkdirs()) {
                    Log.e(
                        relativePath, "Oops! Failed create "
                                + relativePath + " directory"
                    )
                    return null
                }
            }
            if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) {
                mediaFile = File(oldLocatePhoto)
            }
        }
        return mediaFile
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
                    Log.e(TAG, "User Cancelled Image Capture")
                }
                else -> {
                    Log.e(TAG, "Failed to Capture Image")
                }
            }
        }
    }


    /**
     * Permission Area
     */


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
                    Log.e(TAG, "onRequestPermissionsResult OK")
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}