package di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import database.AppDatabase
import javax.inject.Singleton

@Module
class RoomDatabaseModule(application: Application) {
    private val pizzaApp = application
    lateinit var db: AppDatabase
    @Singleton
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        db = Room.databaseBuilder(pizzaApp, AppDatabase::class.java, "library_database")
            .fallbackToDestructiveMigration()
            .build()
        return db
    }

    @Singleton
    @Provides
    fun providesDAO(db: AppDatabase) = db.getDao()


}
