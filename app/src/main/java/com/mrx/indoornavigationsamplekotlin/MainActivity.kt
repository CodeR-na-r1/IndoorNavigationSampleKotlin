package com.mrx.indoornavigationsamplekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.mrx.indoorservice.api.IndoorService
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser

class MainActivity : AppCompatActivity() {

    private lateinit var beaconManager: BeaconManager
    private lateinit var indoorService: IndoorService

    lateinit var buttonStart: Button
    lateinit var textViewBeacons: TextView
    lateinit var textViewAzimuth: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        beaconManager = BeaconManager.getInstanceForApplication(this)
        indoorService = IndoorService.getInstance(this)

        buttonStart = findViewById(R.id.btn_control)
        textViewBeacons = findViewById(R.id.textView_beacons)
        textViewAzimuth = findViewById(R.id.textView_azimuth)

        // Настройка для поиска маячков iBeacon

        beaconManager.beaconParsers.clear()
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))

        /*
        * После первого запуска необходимо дать разрешение приложению на местоположение в настройках, иначе маяки не найдет
        */

        buttonStart.setOnClickListener {
            // Маячки: Подписались на обновление данных и выводим на текстовое поле
            indoorService.BeaconsEnvironment.getRangingViewModel().observe(this) { beacons ->
                textViewBeacons.text = "Ranged: ${beacons.count()} beacons"
            }

            // Азимут: Подписались на обновление данных и выводим на текстовое поле
            indoorService.AzimuthManager.getAzimuthViewModel().observe(this) {
                textViewAzimuth.text = "Azimuth: ${it.toInt()}"
            }

            // Запустили обнаружение маячков и прослушивание сенсора
            indoorService.BeaconsEnvironment.startRanging()
            indoorService.AzimuthManager.startListen()
        }
    }

    override fun onPause() {
        super.onPause()

        // Останавливаем обнаружение маячков и прослушивание сенсора
        indoorService.BeaconsEnvironment.stopRanging()
        indoorService.AzimuthManager.stopListen()
    }

    companion object {
        const val TAG = "MyTag"
    }
}