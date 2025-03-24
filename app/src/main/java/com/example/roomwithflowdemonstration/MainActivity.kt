package com.example.roomwithflowdemonstration

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
                modifier = Modifier.padding(8.dp),
                ::insertRegion,
                ::getAllRegionsAsStream,
                ::getAllRegionsAsStreamWithCancel,
                ::getAllRegionsAsList,
                ::deleteAllRegions,
                ::listAllRegions,
            )
        }
    }

    private fun trace(str: String) {
        Timber.d(str)
    }

    private fun initDb() {
        lifecycleScope.launch(Dispatchers.IO) {
            trace("Building database")

            db = DatabaseBuilder.build(this@MainActivity)
            regionDao = db.regionDao()

            trace("Deleting all regions.")
            regionDao.deleteAll()
        }
    }

    private fun deleteAllRegions() {
        lifecycleScope.launch(Dispatchers.IO) {
            regionDao.deleteAll()
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun insertRegion() {
        lifecycleScope.launch(Dispatchers.IO) {
            val newRegion = RegionEntity(name = "Region ${Uuid.random()}")
            trace("Inserting new region $newRegion")
            regionDao.insert(newRegion)
        }
    }

    private fun getAllRegionsAsStream() {
        lifecycleScope.launch(Dispatchers.IO) {
            val allRegionsFlow = regionDao.getAllRegionsStream()
            allRegionsFlow.collect { regionList ->
                trace("a) From flow ${allRegionsFlow} new emission contains ${regionList.size} regions")
            }
        }
    }

    private fun getAllRegionsAsStreamWithCancel() {
        trace("List of regions:")
        val job: Job = lifecycleScope.launch(Dispatchers.IO) {
            val allRegionsFlow = regionDao.getAllRegionsStream()
            allRegionsFlow.collect { regionList ->
                trace("b) From flow ${allRegionsFlow} new emission contains ${regionList.size} regions.")
                coroutineContext.cancel()
            }
        }
    }

    private fun getAllRegionsAsList() {
        trace("List of regions:")
        lifecycleScope.launch(Dispatchers.IO) {
            val allRegions = regionDao.getAllRegions()
            trace("Number of regions in returned List<Region> = ${allRegions.size}")
        }
    }

    private fun listAllRegions() {
        trace("List of regions:")
        lifecycleScope.launch(Dispatchers.IO) {
            regionDao.getAllRegions().forEach {
                trace("id = ${it.id}, name = ${it.name}")
            }
        }
    }

}