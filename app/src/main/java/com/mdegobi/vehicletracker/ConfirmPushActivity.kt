package com.mdegobi.vehicletracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.mdegobi.vehicletracker.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.options
import java.util.Date

class ConfirmPushActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_push)

        // hidden button
        val buttonConfirm = findViewById<Button>(R.id.button2)
        val textConfirm = findViewById<TextView>(R.id.textView3)
        buttonConfirm.visibility = View.INVISIBLE
        buttonConfirm.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
        }

        // get value km
        val km = intent.getStringExtra("value")
        val type = intent.getStringExtra("type")
        Log.d("Km", km.toString())

        data class Logger(val km: Int, val type: String, val timestamp: Long)


        val timestamp = System.currentTimeMillis() / 100
        val log = Logger(km.toString().toInt(), type.toString(), timestamp)

        val db = Firebase.database
        db.getReference("km").setValue(log.km).addOnCompleteListener{task ->
            if (task.isSuccessful) {
                db.getReference("log").push().setValue(log).addOnCompleteListener { taskLog ->
                    if (taskLog.isSuccessful) {
                        buttonConfirm.visibility = View.VISIBLE
                        textConfirm.text = "Enviado com sucesso!"
                    }
                }

            }
        }
    }
}