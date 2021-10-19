package fragments

import androidx.fragment.app.Fragment
import pizza_logic.Pizza

interface OnFragmentPass {
    fun onDataPass(fragment: Fragment)
    fun onDataDelete()
    fun onPassLiveData(item: Pizza)
}