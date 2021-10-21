package pizza_logic

import androidx.fragment.app.Fragment

interface OnFragmentPass {
    fun onDataPass(fragment: Fragment)
    fun onDataDelete()
    fun onPassLiveData(item: Pizza)
    fun onPopBackStack()
}