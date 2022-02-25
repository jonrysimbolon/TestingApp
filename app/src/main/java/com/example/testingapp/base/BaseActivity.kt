package com.example.testingapp.base

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.hardware.display.DisplayManagerCompat
import com.example.testingapp.BuildConfig
import com.example.testingapp.R
import retrofit.RestAdapter
import java.math.BigInteger
import java.net.NetworkInterface
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*


open class BaseActivity : AppCompatActivity() {

    /**
     * ShowLogLifeCycler must be set onStart BaseActivity...
     * ...testfiilBox must be set onStart BaseActivity
     */

    // Don't set this !!!! ...
    // ...very dangerous to set this !!!!
    var showLogLifeCycle = false
    var testFiilBox = false
    // ...don't set above !!!!

    /**
     * End Warning !!!!
     */

    var ip = BuildConfig.ip

    lateinit var datapersonal: SharedPreferences
    lateinit var datasementara: SharedPreferences
    lateinit var sizeAndroid: SharedPreferences
    lateinit var editDatapersonal: SharedPreferences.Editor
    lateinit var dataSementaraEdit: SharedPreferences.Editor
    var rand = Random()
    lateinit var currentTime: String

    lateinit var progress: Dialog
    
    private var logLC = "BaseActivity-LogLifecycle"

    override fun onDestroy() {
        super.onDestroy()
        Log.e(logLC, "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.e(logLC, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e(logLC, "onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.e(logLC, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(logLC, "onResume")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e(logLC, "onCreate")
        //modeNightNo()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        prepareAll()

        // set in this...
        // ...this area is safe for setting
        if (BuildConfig.DEBUG) {



            // set on this
            testFiilBox = true
            showLogLifeCycle = true



        } else {

            // DONT SET ON THIS !!!!
            testFiilBox = false
            showLogLifeCycle = false
        }
    }

    protected fun modeNightNo() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun prepareAll() {
        datapersonal = getSharedPreferences("datapersonal", Context.MODE_PRIVATE)
        datasementara = getSharedPreferences("datasementara", Context.MODE_PRIVATE)
        sizeAndroid = getSharedPreferences("sizeAndroid", Context.MODE_PRIVATE)

        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        currentTime = df.format(Calendar.getInstance().time)
    }

    fun checkLifeCycle(lifeCycleStr: String, lifeStr: String) {
        Log.e(lifeCycleStr, lifeStr)
    }

    fun loading(){
        progress = Dialog(this)
        progress.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progress.setContentView(R.layout.layout_loading)
        progress.setCancelable(false)
        progress.show()
    }

    fun goMap(lat: String, longg: String) {
        val uri = "http://maps.google.com/maps?q=loc:$lat,$longg"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setPackage("com.google.android.apps.maps")
        startActivity(intent)
    }

    fun getMac(): String {
        try {
            val all: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equals("wlan0")) continue
                val macBytes: ByteArray = nif.hardwareAddress ?: return ""
                val res1 = StringBuilder()
                for (b in macBytes) {
                    // res1.append(Integer.toHexString(b & 0xFF) + ":");
                    res1.append(String.format("%02X:", b))
                }
                if (res1.isNotEmpty()) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: Exception) {
            //handle exception
        }
        return ""
    }

    fun cleanDate(date: String): String {
        //val myFormat = "EEEE, dd-MM-yyyy"
        val dateKom = date.split(", ")
        val dayStr = dateKom[0]
        val dateStr = dateKom[1]
        var showDayDate = ""
        when (dayStr) {
            "Monday" -> {
                showDayDate = "Senin, $dateStr"
            }
            "Tuesday" -> {
                showDayDate = "Selasa, $dateStr"
            }
            "Wednesday" -> {
                showDayDate = "Rabu, $dateStr"
            }
            "Thursday" -> {
                showDayDate = "Kamis, $dateStr"
            }
            "Friday" -> {
                showDayDate = "Jumat, $dateStr"
            }
            "Saturday" -> {
                showDayDate = "Sabtu, $dateStr"
            }
            "Sunday" -> {
                showDayDate = "Minggu, $dateStr"
            }
        }
        return showDayDate
    }

    fun goBrowser(url2: String) {
        val url = url2
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    fun createDatabase(): SQLiteDatabase {
        val makeDataBaseDataAC = MakeDataBaseDataAC(this)
        return makeDataBaseDataAC.writableDatabase
    }

    protected fun acakNumber(a: Int, b: Int): Int {
        return rand.nextInt(a - b + 1) + b
    }

    fun toastC(data: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Toast.makeText(this, data, Toast.LENGTH_LONG).show()
        } else {
            val toast: Toast = Toast.makeText(this, data, Toast.LENGTH_LONG)
            val view = toast.view
            view!!.background.colorFilter =
                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    ContextCompat.getColor(this, R.color.red), BlendModeCompat.SRC_ATOP
                )
            val text = view.findViewById<TextView>(android.R.id.message)
            text.setTextColor(ContextCompat.getColor(this, R.color.white))
            toast.show()
        }
    }

    fun go(clas: Class<*>) {
        val intent = Intent(this, clas)
        startActivity(intent)
    }

    fun goBasic(clas: Class<*>) {
        val intent = Intent(this, clas)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    fun goBasicSpecial(clas: Class<*>) {
        val intent = Intent(this, clas)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

    fun PopupWindow.dimBehind() {
        val container = contentView.rootView
        val context = contentView.context
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = p.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.3f
        wm.updateViewLayout(container, p)
    }

    fun putData(data: String): String? {
        return datapersonal.getString(data, "")
    }

    fun putDataSementara(data: String): String? {
        return datasementara.getString(data, "")
    }

    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    fun putDataSize(data: String): String? {
        return sizeAndroid.getString(data, "")
    }

    fun editDataPerson(initial: String, data: String) {
        editDatapersonal = datapersonal.edit()
        editDatapersonal.putString(initial, data)
        editDatapersonal.apply()
    }

    fun delDataPerson(initial: String) {
        editDatapersonal = datapersonal.edit()
        editDatapersonal.remove(initial)
        editDatapersonal.apply()
    }

    fun editDataPersonSementara(initial: String, data: String) {
        dataSementaraEdit = datasementara.edit()
        dataSementaraEdit.putString(initial, data)
        dataSementaraEdit.apply()
    }

    fun editDataSize(initial: String, data: String) {
        editDatapersonal = sizeAndroid.edit()
        editDatapersonal.putString(initial, data)
        editDatapersonal.apply()
    }

    fun logoutDataPerson() {
        editDatapersonal = datapersonal.edit()
        editDatapersonal.clear()
        editDatapersonal.apply()
    }


    fun logoutDataPersonCustom(text: String) {
        editDatapersonal = datapersonal.edit()
        editDatapersonal.remove(text)
        editDatapersonal.apply()
    }

    fun SendRetro(): InterfaceRetro {
        val restAdapter = RestAdapter.Builder()
            .setEndpoint(ip)
            .build()

        return restAdapter.create(InterfaceRetro::class.java)
    }

    fun getMD5(input: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            val messageDigest = md.digest(input.toByteArray())
            val number = BigInteger(1, messageDigest)
            var hashtext = number.toString(16)
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            return hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }

    }

    fun showMessageOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setCancelable(false)
            .create()
            .show()
    }

    fun showMessageOKCustom(
        message: String,
        yes: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton(yes, okListener)
            .setCancelable(false)
            .create()
            .show()
    }

    fun showMessageOKTitleCustom(
        title: String,
        message: String,
        yes: String,
        okListener: DialogInterface.OnClickListener
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(yes, okListener)
            .setCancelable(false)
            .create()
            .show()
    }

    private fun screenValue() {
        val width = getWidthCustom()
        val height = getHeightCustom()
        Log.e("BaseActivity", "height : $height")
        Log.e("BaseActivity", "width : $width")
        editDataSize("height", height.toString())
        editDataSize("width", width.toString())
    }

    private fun getHeightCustom(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val defaultDisplay =
                DisplayManagerCompat.getInstance(this).getDisplay(Display.DEFAULT_DISPLAY)
            val displayContext = defaultDisplay?.let { createDisplayContext(it) }
            displayContext!!.resources.displayMetrics.heightPixels
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

    private fun getWidthCustom(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val defaultDisplay =
                DisplayManagerCompat.getInstance(this).getDisplay(Display.DEFAULT_DISPLAY)
            val displayContext = defaultDisplay?.let { createDisplayContext(it) }
            displayContext!!.resources.displayMetrics.widthPixels
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    companion object {

        fun insertAtt(target: String, position: Int, insert: String): String {
            val targetLen = target.length
            if (position < 0 || position > targetLen) {
                throw IllegalArgumentException("position=$position")
            }
            if (insert.isEmpty()) {
                return target
            }
            if (position == 0) {
                return insert + target
            } else if (position == targetLen) {
                return target + insert
            }
            val insertLen = insert.length
            val buffer = CharArray(targetLen + insertLen)
            target.toCharArray(buffer, 0, 0, position)
            insert.toCharArray(buffer, position, 0, insertLen)
            target.toCharArray(buffer, position + insertLen, position, targetLen)
            return String(buffer)
        }
    }


    // exception class
    class ValidationException(message: String) : Throwable(message)


    // class makedatabase
    class MakeDataBaseDataAC(context: Context) : SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {

        override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
            /*val sql1 = "create table otr_tbl(id integer primary key autoincrement, " +
                    "idp_pricelist integer null, " +
                    "tipe_motor text null, " +
                    "warna text null, " +
                    "area text null," +
                    "tgl_update text null, " +
                    "otr_motor text null, " +
                    "staff_input text null" +
                    ");"
            Log.d("Data", "sql: $sql1")
            sqLiteDatabase.execSQL(sql1)

            val sql2 = "create table customer_tbl(id integer primary key autoincrement, " +
                    "uuid_new_pooling text null, " +
                    "unit text null, " +
                    "warna text null, " +
                    "otr text null," +
                    "frame_thn text null, " +
                    "dp_gross text null, " +
                    "cicilan text null, " +
                    "tenor text null, " +
                    "tgl_survey text null, " +
                    "waktu_survey text null, " +
                    "promo text null, " +
                    "almt_survey text null, " +
                    "kota_survey text null, " +
                    "nama_stnk text null, " +
                    "leasing text null, " +
                    "catatan text null, " +
                    "uuid_staff_sales text null, " +
                    "staff_sales text null, " +
                    "nama_pmhn text null, " +
                    "nik_pmhn text null, " +
                    "tmpt_lahir_pmhn text null, " +
                    "tgl_lahir_pmhn text null, " +
                    "ponsel_pmhn text null, " +
                    "wa_pmhn text null, " +
                    "ibu_kandung text null, " +
                    "gol_peker_pmhn text null, " +
                    "peker_pmhn text null, " +
                    "lama_kerja text null, " +
                    "penghasilan text null, " +
                    "status_rumah text null, " +
                    "nama_pnj text null, " +
                    "nik_pnj text null, " +
                    "tmpt_lahir_pnj text null, " +
                    "tgl_lahir_pnj text null, " +
                    "ponsel_pnj text null, " +
                    "wa_pnj text null, " +
                    "gol_peker_pnj text null, " +
                    "peker_pnj text null, " +
                    "hub_kerabat text null," +
                    "date_time_create text null," +
                    "uuid_staff text null" +
                    ");"
            Log.d("Data", "sql2: $sql2")
            sqLiteDatabase.execSQL(sql2)*/

        }

        override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {

        }

        companion object {
            private val DATABASE_NAME = "Leasing.db"
            private val DATABASE_VERSION = 1
        }
    }

    /*class LottieDialogFragment(context: Context) : Dialog(context) {
        init {
            val wlmp = window!!.attributes
            wlmp.gravity = Gravity.CENTER_HORIZONTAL
            window!!.attributes = wlmp
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setTitle(null)
            setCancelable(false)
            setOnCancelListener(null)
            val view = LayoutInflater.from(context).inflate(
                R.layout.layout_loading, null
            )
            setContentView(view)
        }
    }*/

}