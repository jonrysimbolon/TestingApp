package com.example.testingapp

import android.os.Bundle
import android.util.Log
import com.example.testingapp.base.BaseActivity
import com.example.testingapp.databinding.ActivityTestPageLoginBinding

class TestPageLogin : BaseActivity() {

    private val llog = "TestPageLogin-act"
    lateinit var binding: ActivityTestPageLoginBinding
    private var tag = "TestPageLogin"

    override fun onRestart() {
        super.onRestart()
        Log.e(llog, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.e(llog, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(llog, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(llog, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(llog, "onDestroy")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //modeNightNo()
        binding = ActivityTestPageLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //testing
        binding.usernameBox.setText(BuildConfig.username)
        binding.passwordBox.setText(BuildConfig.password)

        binding.loginBtn.setOnClickListener {
            goBasic(TestBeranda::class.java)
        }

        /*binding.loginBtn.setOnClickListener {
            if (binding.usernameBox.text.toString().isNotEmpty() && binding.passwordBox.text.toString()
                    .isNotEmpty()
            ) {
                val ke = BuildConfig.key + binding.usernameBox.text.toString() + binding.passwordBox.text.toString()
                val key = getMD5(
                    ke
                )
                Log.e(tag, "ke : $ke")
                Log.e(tag, "key : $key")
                loading()
                SendRetro().leasinglogin(
                    key,
                    binding.usernameBox.text.toString(),
                    binding.passwordBox.text.toString(),
                    object : Callback<Response> {
                        override fun success(t: Response?, response: Response?) {
                            if (t?.status == 200) {
                                progress.dismiss()
                                try {
                                    var readerr: BufferedReader? = null
                                    var output = ""
                                    readerr =
                                        BufferedReader(InputStreamReader(response?.body?.`in`()!!))
                                    output = readerr.readLine()
                                    Log.e("pllogin", "hasil nya : $output")
                                    val data = JSONArray(output)
                                    val dataInti = data.getJSONObject(0)
                                    val status = dataInti.getString("status")

                                    when {
                                        status.equals("sukses") -> {
                                            val subject = dataInti.getString("subject")
                                            val result = dataInti.getJSONObject("result")
                                            val uuid_staff = result.getString("uuid_staff")
                                            val mds = result.getString("mds")

                                            showMessageOK(
                                                subject
                                            ) { dialogInterface, i ->

                                                Log.e("MainActivity","mds : $mds")

                                                editDataPerson("uuid_staff", uuid_staff)
                                                editDataPerson("mds_arr", mds)

                                                //goBasicSpecial(PoolingLeasingPage::class.java)
                                            }
                                        }
                                        status.equals("gagal") -> {
                                            val subject = dataInti.getString("subject")
                                            toastC(subject)
                                        }
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    Log.e(tag, "error : " + e.message)
                                }

                            }
                        }

                        override fun failure(error: RetrofitError?) {
                            progress.dismiss()
                            toastC(resources.getString(R.string.koneksibermasalah))
                            Log.e(tag, "url : " + error?.url)
                            Log.e(tag, "error : " + error?.message)
                        }
                    }
                )
            } else {
                toastC("Silahkan isi username dan password terlebih dahulu")
            }
        }*/
    }
}