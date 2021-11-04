package di

import dagger.Module
import dagger.Provides
import database.AppDatabase
import repository.PizzaRepository
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun providesPizzaRepository(db: AppDatabase): PizzaRepository {
        return PizzaRepository(db)
    }

}