package com.cleartab.cleartab

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.cleartab.cleartab.databinding.ActivityMainBinding
import com.cleartab.cleartab.retrofit.SupabaseService
import com.cleartab.cleartab.retrofit.tables.Projeto
import com.cleartab.cleartab.utils.SharedPreferencesUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tv_titulo_do_projeto: TextView
    private lateinit var tv_descricao_do_projeto: TextView
    private val db = SupabaseService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tv_titulo_do_projeto = findViewById(R.id.tv_titulo_do_projeto)
        tv_descricao_do_projeto = findViewById(R.id.tv_descricao_do_projeto)

        val navView: BottomNavigationView = binding.navView

        val idProjeto = SharedPreferencesUtil.getIDs(this@MainActivity, "ID_PROJETO")

        var ProjetoDados: Projeto

        lifecycleScope.launch {
            ProjetoDados = db.fetchProject(idProjeto = idProjeto)!!
            tv_titulo_do_projeto.text = ProjetoDados.nome
            tv_descricao_do_projeto.text = ProjetoDados.descricao
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_tasks, R.id.navigation_home, R.id.navigation_history
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
    }
}