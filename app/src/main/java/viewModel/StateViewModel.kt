package viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateViewModel : ViewModel() {

    val addFragmentLive = MutableLiveData<Fragment>()
    val deleteFragmentLive = MutableLiveData<Boolean>()
}