package com.example.olavogascanner

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val scraperService = ScraperService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById<Button>(R.id.btnStartScan)
        val tvResults = findViewById<TextView>(R.id.tvResults)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        tvResults.movementMethod = ScrollingMovementMethod()

        btnStart.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            tvResults.text = "Skanowanie w toku...\n"

            lifecycleScope.launch {
                val olxResults = scraperService.searchOLX("olavoga")
                val metaResults = scraperService.searchMetaAds("olavoga")

                progressBar.visibility = View.GONE

                val builder = StringBuilder()
                builder.append("=== WYNIKI OLX ===\n")
                olxResults.forEach { builder.append("$it\n") }

                builder.append("\n=== WYNIKI META ADS ===\n")
                metaResults.forEach { builder.append("$it\n") }

                tvResults.text = builder.toString()
            }
        }
    }
}
