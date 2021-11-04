package di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pizza_logic.PizzaModel
import repository.PizzaRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PizzaViewModelFactory @Inject constructor(private var repository: PizzaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PizzaModel(repository) as T
    }
}