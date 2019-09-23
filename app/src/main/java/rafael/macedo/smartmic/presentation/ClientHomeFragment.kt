package rafael.macedo.smartmic.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rafael.macedo.smartmic.databinding.FragmentClientHomeBinding

class ClientHomeFragment:Fragment() {

    companion object{
        fun newInstance():ClientHomeFragment{
            return ClientHomeFragment()
        }
    }

    lateinit var binding:FragmentClientHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClientHomeBinding.inflate(inflater,container,false)
        return binding.root
    }


}