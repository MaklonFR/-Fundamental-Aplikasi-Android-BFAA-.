package com.maklon.fr.myintentapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvResult: TextView
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null) {
            val selectedValue =
                result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
            tvResult.text = "Hasil : $selectedValue"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMoveActivity: Button = findViewById(R.id.btn_move_activity)
        btnMoveActivity.setOnClickListener(this)

        val btnMoveWithDataActivity: Button = findViewById(R.id.btn_move_activity_data)
        btnMoveWithDataActivity.setOnClickListener(this)

        val btnMoveWithObject:Button = findViewById(R.id.btn_move_activity_object)
        btnMoveWithObject.setOnClickListener(this)

        val btnDialPhone:Button = findViewById(R.id.btn_dial_number)
        btnDialPhone.setOnClickListener(this)

        val btnMoveForResult:Button = findViewById(R.id.btn_move_for_result)
        btnMoveForResult.setOnClickListener(this)
        tvResult = findViewById(R.id.tv_result)

    }
        override fun onClick (v: View?) {
            when (v?.id) {

                //OnClick Move Activity
                R.id.btn_move_activity -> {
                    val moveIntent = Intent (this@MainActivity, MoveActivity::class.java)
                    startActivity(moveIntent)
                }

                //OnClick Move With Data
                R.id.btn_move_activity_data -> {
                    val moveWithDataIntent = Intent(this@MainActivity, MoveWithDataActivity::class.java)
                    moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Maklon Jacob Frare, S.Kom")
                    moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 5)
                    startActivity(moveWithDataIntent)
                }

                //OnClick Move With Object
                R.id.btn_move_activity_object -> {
                    val person = Person(
                        "Maklon Jacob Frare, S.Kom",
                        31,
                        "maklonjacob.frare@gmail.com",
                        "Kuwus"
                    )
                    val moveWithObjectIntent = Intent(this@MainActivity, MoveWithObjectActivity::class.java)
                    moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person)
                    startActivity(moveWithObjectIntent)
                }

                //Implicit Intent using phone number application
                R.id.btn_dial_number -> {
                    val phoneNumber = "085253366477"
                    val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
                    startActivity(dialPhoneIntent)
                }

                //Resilt Activity
                R.id.btn_move_for_result -> {
                    val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                    resultLauncher.launch(moveForResultIntent)
                }

            }
        }

}


