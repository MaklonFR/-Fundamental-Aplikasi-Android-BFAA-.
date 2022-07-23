package com.maklon.fr.mybackgroundthread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btn_start)
        val tvStatus = findViewById<TextView>(R.id.tv_status)

        //Menjalankan background process dengan menggunakan Executor//
        /*
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        btnStart.setOnClickListener{
            executor.execute {
                try {
                    //simulate process in background thread
                    for (i in 0..10) {
                        Thread.sleep(500)
                        val percentage = i * 10

                        handler.post {
                            if (percentage == 100) {
                                tvStatus.setText(R.string.task_completed)
                            } else {
                                tvStatus.text =
                                    String.format(getString(R.string.compressing), percentage)

                            }
                        }

                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
         */

        //Menjalankan background process dengan menggunakan Coroutine using dependency
        /* implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
           implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
           implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
         */

        btnStart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                //Simulate Proses in Background
                for (i in 0..10) {
                    delay(500)
                    val percentage = i * 10
                    withContext(Dispatchers.Main) {
                        //update ui in main thread
                        if (percentage == 100 ) {
                            tvStatus.setText(R.string.task_completed)
                        } else {
                            tvStatus.text= String.format(getString(R.string.compressing), percentage)
                        }
                    }
                }
            }
        }
    }
}