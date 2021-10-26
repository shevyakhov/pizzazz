package pizza_logic

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface OnFragmentPass {
    fun onFragmentPass(fragment: Fragment)
    fun onDataDelete()
    fun onDataPass(item: PizzaEntity)
    fun onDialog(fragment: BottomSheetDialogFragment)
    fun onPopBackStack()
}