/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.mdegobi.vehicletracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContentView(R.layout.activity_main)

        val inputValueKMButton = findViewById<EditText>(R.id.inputValueKM)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonSub = findViewById<Button>(R.id.buttonSubtract)
        val buttonConfirm = findViewById<Button>(R.id.buttonConfirm)

        val db = FirebaseDatabase.getInstance()
        val myRef = db.getReference("km")

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Long::class.java)
                val value1 = value?.toInt() ?: 0
                Log.d("MainActivity", "Value is: $value1")

                inputValueKMButton.setText(value1.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("MainActivity", "loadPost:onCancelled", databaseError.toException())
            }
        })

        buttonAdd.setOnClickListener {
            val value = inputValueKMButton.text.toString().toInt() + 1
            inputValueKMButton.setText(value.toString())
        }
        buttonSub.setOnClickListener {
            val value = inputValueKMButton.text.toString().toInt() - 1
            inputValueKMButton.setText(value.toString())
        }

        buttonConfirm.setOnClickListener {
            val value = inputValueKMButton.text.toString().toInt()
            /*
            myRef.setValue(value)

            data class LogData(val km: Int)

            val logRef = db.getReference("log")
            val currentKM = LogData(value)
            logRef.push().setValue(currentKM)
            */
            val intent = Intent(this, SelectOptionActivity::class.java)
            intent.putExtra("value", value.toString())
            startActivity(intent)
        }

/*
        //myRef.setValue("Successfully")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                Log.d("MainActivity", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("MainActivity", "Failed to read value.", error.toException())
            }
        })

*/
    }
}
