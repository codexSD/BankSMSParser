package com.codexSoftSD.bankCardsTemplateManager

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.codexSoftSD.bankCardsTemplateManager.data.db.AppDatabase
import com.codexSoftSD.bankCardsTemplateManager.data.template.TemplateManager
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainApp: Application() {
    companion object{
        lateinit var db: AppDatabase
        lateinit var fireDatabase: FirebaseDatabase
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        private val threadLocker = Any()
        fun synchronized(toRun: Runnable) {
            synchronized(threadLocker) { toRun.run() }
        }
    }
    override fun onCreate() {
        super.onCreate()
        context = baseContext
        db = Room.databaseBuilder(this, AppDatabase::class.java, "main_database").build()
        fireDatabase = Firebase.database
        CoroutineScope(Dispatchers.IO).launch {
            TemplateManager.getAllTemplates()
//            PurchasesRepository.load()
        }
    }

}