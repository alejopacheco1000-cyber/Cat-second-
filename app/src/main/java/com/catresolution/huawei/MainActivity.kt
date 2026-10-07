package com.catresolution.huawei

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {
    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        status = TextView(this).apply { textSize = 16f; setPadding(32, 32, 32, 16) }
        val detect = Button(this).apply { text = "Detectar Huawei Y9 Prime"; setOnClickListener { updateDeviceInfo() } }
        val shizuku = Button(this).apply { text = "Comprobar Shizuku"; setOnClickListener { checkShizuku() } }
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; gravity = Gravity.CENTER_HORIZONTAL; setPadding(24, 40, 24, 24)
            addView(status); addView(detect); addView(shizuku)
        }
        setContentView(layout)
        updateDeviceInfo()
    }

    private fun updateDeviceInfo() {
        val d = resources.displayMetrics
        status.text = """
            Fabricante: ${Build.MANUFACTURER}
            Modelo: ${Build.MODEL}
            Android: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})
            Pantalla: ${d.widthPixels} x ${d.heightPixels}
            Densidad: ${d.densityDpi} dpi

            Ruta: Android 10 / Huawei
            Backend de resolución: preparado para Shizuku
        """.trimIndent()
    }

    private fun checkShizuku() {
        val installed = try {
            packageManager.getPackageInfo("moe.shizuku.privileged.api", 0); true
        } catch (_: Exception) { false }
        status.text = if (installed) {
            "Shizuku detectado. Autoriza esta aplicación desde Shizuku antes de ejecutar operaciones privilegiadas."
        } else {
            "Shizuku no está instalado. Instala Shizuku y autoriza la aplicación."
        }
        Toast.makeText(this, status.text, Toast.LENGTH_LONG).show()
    }
}
