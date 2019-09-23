package rafael.macedo.smartmic.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import rafael.macedo.smartmic.databinding.FragmentHomeViewPagerBinding

class FragmentHomeViewPager:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeViewPagerBinding.inflate(inflater, container, false)
        val viewPager = binding.viewPager
        viewPager.adapter = HomePageAdapter(this)

        return binding.root

    }
}