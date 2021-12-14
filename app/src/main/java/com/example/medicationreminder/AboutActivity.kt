package com.example.medicationreminder

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class AboutActivity : AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var githubButton: MaterialButton
    private lateinit var appDescriptionView: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        toolbar = findViewById(R.id.toolbar)
        githubButton = findViewById(R.id.view_github_button)
        appDescriptionView = findViewById(R.id.app_description_view)
        setSupportActionBar(toolbar)
        toolbar.background = ColorDrawable(ResourcesCompat.getColor(resources, R.color.sub_green, null))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        githubButton.setOnClickListener {
            val webpage = Uri.parse(getString(R.string.github_link))
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(intent)
        }

        appDescriptionView.text = getString(R.string.app_description)
    }

}