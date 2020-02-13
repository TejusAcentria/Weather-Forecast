package com.weatherforecast.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.weatherforecast.R

class ErrorActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){

            imageView3->{
                onBackPressed()
            }

        }
    }

    var imageView3:ImageView?=null
    var refresh:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)
        initView()
        refresh=true
    }

    private fun initView() {
        imageView3=findViewById(R.id.imageView3)
        imageView3!!.setOnClickListener(this)
    }


    override fun onBackPressed() {
        val result = Intent()
        result.putExtra(getString(R.string.error_refresh), refresh)
        this.setResult(Activity.RESULT_OK, result)
        this.finish()
        super.onBackPressed()
    }
}
