package fragments

import androidx.fragment.app.Fragment

interface OnFragmentPass {
    fun onDataPass(fragment: Fragment)
    fun onDataDelete()
}