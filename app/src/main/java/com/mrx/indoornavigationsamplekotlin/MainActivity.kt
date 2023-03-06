package com.mrx.indoornavigationsamplekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mrx.indoorservice.api.IndoorService

class MainActivity : AppCompatActivity() {

    lateinit var beaconReferenceApplication: BeaconReferenceApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beaconReferenceApplication = application as BeaconReferenceApplication

        val indoorService = IndoorService.getInstance(this)

        /*
        * После первого запуска необходимо дать разрешение приложению на местоположение в настройках, иначе маяки не найдет
        */

        // todo здесь ваш далее код

        indoorService.BeaconsEnvironment.getRangingViewModel().observe(this) { beacons ->
            Log.d(TAG, "Ranged: ${beacons.count()} beacons")
        }

        indoorService.BeaconsEnvironment.startRanging()
    }

    companion object {
        const val TAG = "MyTag"
    }
}