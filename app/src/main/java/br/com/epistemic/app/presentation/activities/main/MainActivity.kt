package br.com.epistemic.app.presentation.activities.main

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.fragment.NavHostFragment
import br.com.epistemic.app.R
import br.com.epistemic.app.data.repository.UserPreferencesRepositoryImpl
import br.com.epistemic.app.databinding.ActivityMainBinding
import br.com.epistemic.app.domain.repository.LANGUAGES
import br.com.epistemic.app.commom.helper.ContextUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.util.Calendar
import java.util.Locale

private val USER_PREFERENCES = "user_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupNavHost()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()


        calendar.timeInMillis
    }

    private fun setupNavHost() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            var tag: String = LANGUAGES.PT_BR.tag

            try {
                val store = newBase.dataStore
                val repo = UserPreferencesRepositoryImpl(store)
                tag = runBlocking { repo.userPrefs.first().languageTag }
            } catch (e: IOException) {
                // TODO Show Message : Error to Load Language
            }

            val localeToSwitchTo = Locale(tag)
            val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, localeToSwitchTo)
            super.attachBaseContext(localeUpdatedContext)
        } else {
            super.attachBaseContext(newBase)
        }
    }
}