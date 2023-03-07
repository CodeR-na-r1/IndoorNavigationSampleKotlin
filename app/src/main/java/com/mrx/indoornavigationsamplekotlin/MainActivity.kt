package com.mrx.indoornavigationsamplekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mrx.indoorservice.api.IndoorService
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser

class MainActivity : AppCompatActivity() {

    private lateinit var beaconManager: BeaconManager
    private lateinit var indoorService: IndoorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beaconManager = BeaconManager.getInstanceForApplication(this)
        indoorService = IndoorService.getInstance(this)

        // Настройка для поиска маячков iBeacon

        beaconManager.beaconParsers.clear()
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))

        /*
        * После первого запуска необходимо дать разрешение приложению на местоположение в настройках, иначе маяки не найдет
        */

        // todo здесь ваш далее код

        indoorService.BeaconsEnvironment.getRangingViewModel().observe(this) { beacons ->
            Log.d(TAG, "Ranged: ${beacons.count()} beacons")
        }

        indoorService.BeaconsEnvironment.startRanging()
    }

    override fun onPause() {
        super.onPause()

        indoorService.BeaconsEnvironment.stopRanging()
    }

    companion object {
        const val TAG = "MyTag"
    }
}