package di

import android.app.Activity
import dagger.Component
import fragments.HomeFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDatabaseModule::class, AppModule::class])
interface AppComponent {
    fun inject(activity: Activity)
    fun inject(fragment: HomeFragment)
}