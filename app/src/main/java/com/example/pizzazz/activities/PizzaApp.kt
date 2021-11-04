package com.example.pizzazz.activities

import android.app.Application
import di.RoomDatabaseModule
import di.AppComponent
import di.DaggerAppComponent

class PizzaApp:Application() {

    companion object {
        lateinit var instance: PizzaApp
    }
    lateinit var pizzaComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        pizzaComponent = DaggerAppComponent
            .builder()
            .roomDatabaseModule(RoomDatabaseModule(this))
            .build()
    }
}