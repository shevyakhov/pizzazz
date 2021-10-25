package pizza_logic

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface OnFragmentPass {
    fun onDataPass(fragment: Fragment)
    fun onDataDelete()
    fun onPassLiveData(item: Pizza)
    fun onPassLiveDataRx(item: Pizza)
    fun onDialog(fragment: BottomSheetDialogFragment)
    fun onPopBackStack()
}