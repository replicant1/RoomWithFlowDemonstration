package com.example.roomwithflowdemonstration

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.example.roomwithflowdemonstration.databinding.ActivityMainBinding
import com.example.roomwithflowdemonstration.db.AppDatabase
import com.example.roomwithflowdemonstration.db.DatabaseBuilder
import com.example.roomwithflowdemonstration.db.RegionDao
import com.example.roomwithflowdemonstration.db.RegionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MainActivity : AppCompatActivity() {

    lateinit var db: AppDatabase
    lateinit var regionDao: RegionDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDb()

        setContent {
            MainScreen(
                ::insertRegion,
                ::getAllRegionsAsStream,
                ::getAllRegionsAsStreamWithCancel,
                ::getAllRegionsAsList,
                ::deleteAllRegions,
                ::listAllRegions,
            )
        }
    }

    private fun initDb() {
        Timber.i("Into MainActivity.initDb")

        lifecycleScope.launch(Dispatchers.IO) {
            Timber.d("Building database")

            db = DatabaseBuilder.build(this@MainActivity)
            regionDao = db.regionDao()

            Timber.d("Deleting all regions.")
            regionDao.deleteAll()
        }
    }

    private fun deleteAllRegions() {
        Timber.i("Into MainActivity.deleteAllRegions")

        lifecycleScope.launch(Dispatchers.IO) {
            regionDao.deleteAll()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun insertRegion() {
        Timber.i("Into MainActivity.insertRegion")

        lifecycleScope.launch(Dispatchers.IO) {
            Timber.d("Inserting new region")
            val newRegion = RegionEntity(name = "Region ${Uuid.random()}")
            regionDao.insert(newRegion)
        }
    }

    private fun getAllRegionsAsStream() {
        Timber.i("Into getAllRegionsAsStream")
        lifecycleScope.launch(Dispatchers.IO) {
            val allRegionsFlow = regionDao.getAllRegionsStream()

            allRegionsFlow.collect { regionList ->
                Timber.d("a) From flow ${allRegionsFlow} new emission contains ${regionList.size} regions")
            }
        }
    }

    private fun getAllRegionsAsStreamWithCancel() {
        Timber.i("Into getAllRegionsAsStreamWithCancel")
        val job: Job = lifecycleScope.launch(Dispatchers.IO) {
            val allRegionsFlow = regionDao.getAllRegionsStream()
            allRegionsFlow.collect { regionList ->
                Timber.d("b) From flow ${allRegionsFlow} new emission contains ${regionList.size} regions.")
                coroutineContext.cancel()
            }
        }
    }

    private fun getAllRegionsAsList() {
        Timber.i("Into getAllRegionsAsList")
        lifecycleScope.launch(Dispatchers.IO) {
            val allRegions = regionDao.getAllRegions()
            Timber.d("Number of regions in returned List<Region> = ${allRegions.size}")
        }
    }

    private fun listAllRegions() {
        Timber.i("Into listAllRegions")
        lifecycleScope.launch(Dispatchers.IO) {
            regionDao.getAllRegions().forEach {
                Timber.d("id = ${it.id}, name = ${it.name}")
            }
        }
    }

}