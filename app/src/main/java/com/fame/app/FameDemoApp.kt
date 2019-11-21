package com.fame.app

import android.app.Application
import com.fame.db.DataBase
import com.fame.factory.MainViewModelFactory
import com.fame.repository.MainRepository
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FameDemoApp : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@FameDemoApp))
        bind() from singleton { DataBase(instance()).dao() }
        bind() from singleton { MainRepository(instance()) }
        bind() from provider {MainViewModelFactory(instance())}
    }


}