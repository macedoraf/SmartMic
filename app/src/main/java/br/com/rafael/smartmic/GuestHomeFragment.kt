package br.com.rafael.smartmic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class GuestHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.guest_home_fragment,container,false)
    }
    companion object{
        fun newInstance():GuestHomeFragment{
            return GuestHomeFragment()
        }
    }
}