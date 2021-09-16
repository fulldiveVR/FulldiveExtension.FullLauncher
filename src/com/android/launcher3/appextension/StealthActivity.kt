package com.android.launcher3.appextension

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class StealthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()
    }
}
