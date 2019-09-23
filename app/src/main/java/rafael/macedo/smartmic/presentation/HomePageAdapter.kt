package rafael.macedo.smartmic.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class HomePageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    companion object{
        const val CLIENT_HOME_PAGE_INDEX = 0
    }


    private fun tabFragmentCreator(): Map<Int, () -> Fragment> =
        mapOf(CLIENT_HOME_PAGE_INDEX to { ClientHomeFragment.newInstance() })


    override fun getItemCount(): Int =
        tabFragmentCreator().size


    override fun createFragment(position: Int): Fragment {
        return tabFragmentCreator()[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}