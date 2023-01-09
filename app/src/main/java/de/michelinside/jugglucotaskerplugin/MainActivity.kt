package de.michelinside.jugglucotaskerplugin

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var txtLastValue: TextView
    private lateinit var txtVersion: TextView
    private lateinit var btnButton: Button
    private val LOG_ID = "JugglucoTaskerPlugin.Main"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(LOG_ID, "onCreate called")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent()
            val packageName = packageName
            val pm = getSystemService(POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
        }

        txtVersion = findViewById(R.id.txtVersion)
        txtVersion.text = BuildConfig.VERSION_NAME
        btnButton = findViewById(R.id.btnUpdate)
        btnButton.setOnClickListener {
            update()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_ID, "onPause called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_ID, "onResume called")
        update()
    }

    private fun update() {
        txtLastValue = findViewById(R.id.txtLastValue)
        txtLastValue.text = ReceiveData.getAsString(this)
    }
}