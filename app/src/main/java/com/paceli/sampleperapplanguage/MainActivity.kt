package com.paceli.sampleperapplanguage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initLocalePicker()
        updateActivityTitle()
    }

    private fun updateActivityTitle() {
        val localeManager = getSystemService(LocaleManager::class.java)
        val appLocales = localeManager.applicationLocales
        title = if (appLocales.isEmpty) {
            getString(R.string.system_locale)
        } else {
            appLocales.get(0).displayName
        }
    }

    private fun updateAppLocales(vararg locales: Locale) {
        val localeManager = getSystemService(LocaleManager::class.java)
        localeManager.applicationLocales = LocaleList(*locales)
    }

    private fun initLocalePicker() {
        val systemLocale = getString(R.string.system_locale)
        val spinner: Spinner = findViewById(R.id.localePicker)
        val locales = listOf(systemLocale, "en-US", "es-ES", "iw-IL", "ja-JP", "uk-UA")
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locales)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLocale = spinner.adapter.getItem(position) as String
                if (selectedLocale != systemLocale) {
                    updateAppLocales(Locale.forLanguageTag(selectedLocale))
                } else {
                    updateAppLocales()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
