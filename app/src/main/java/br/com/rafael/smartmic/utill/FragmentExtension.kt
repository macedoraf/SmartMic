package br.com.rafael.smartmic.utill

import androidx.fragment.app.Fragment
import br.com.rafael.smartmic.presentation.MainActivity

/**
 * Created by Santander on 14/10/2019
 */


fun Fragment.showActivityLoading() {
    (this.activity as MainActivity).showLoading()

}

fun Fragment.hideActivityLoading() {
    (this.activity as MainActivity).hideLoading()
}