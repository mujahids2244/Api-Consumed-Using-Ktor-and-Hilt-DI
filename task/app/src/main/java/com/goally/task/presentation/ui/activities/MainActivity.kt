package com.goally.task.presentation.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.goally.task.databinding.ActivityMainBinding
import com.goally.task.domain.models.ApiState
import com.goally.task.domain.models.FilmDataModelResult
import com.goally.task.presentation.ui.adapter.FilmAdapter
import com.goally.task.presentation.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: FilmAdapter
    private lateinit var mainViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        setAdapters(this)
        getFilmDataApi("5d967c7c335764f39b1efbe9c5de9760", 4)
    }


    private fun getFilmDataApi(key: String, page: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.VISIBLE
            }
            setApiListner(key, page)
            responseApiFlowsCollect()
        }
    }


    private fun setApiListner(key: String, page: Int) {
        mainViewModel.getFilmData(key, page)
    }


    private suspend fun responseApiFlowsCollect() {
        mainViewModel.data.collect {
            when (it) {
                is ApiState.Success -> {
                    try {
                        runOnUiThread {

                            rvAdapter.addData(it.data.results as ArrayList<FilmDataModelResult>)
                            binding.progressBar.visibility = View.GONE
                        }
                    } catch (e: Exception) {

                        Log.e("Exception", "responseApiFlowsCollect:$e")
                    }

                }

                is ApiState.Error -> {
                    runOnUiThread {
                        Toast.makeText(
                            this,
                            "Server Error. ${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                ApiState.Empty ->{}
            }
        }
    }


    private fun setAdapters(context: Context) {
        rvAdapter = FilmAdapter(context)
        binding.filmRV.adapter = rvAdapter
    }

}