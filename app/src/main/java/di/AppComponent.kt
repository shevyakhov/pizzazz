package di

import android.app.Activity
import dagger.Component
import fragments.DetailsFragment
import fragments.HomeFragment
import fragments.PreviewFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomDatabaseModule::class, AppModule::class])
interface AppComponent {
    fun inject(activity: Activity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: PreviewFragment)
    fun inject(fragment: DetailsFragment)
}