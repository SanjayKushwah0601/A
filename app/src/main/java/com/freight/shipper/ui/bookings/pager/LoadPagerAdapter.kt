package com.freight.shipper.ui.bookings.pager

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter


class LoadPagerAdapter(
    val fragments: List<LoadListFragment<*>>,
    fragmentManager: FragmentManager
) :
    FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments[position].getTabTitle()
    }

    /**
     * FragmentPagerAdapter doesn't use saveState() because the entire fragment is retained
     * in memory. If you want to avoid that behavior you use FragmentStatePagerAdapter which
     * only saves state. If you don't want to save anything at all, then you use
     * FragmentStatepagerAdapter without saving state; it automatically does not save the
     * Fragment in memory
     */
    override fun saveState(): Parcelable? {
        return null
    }
}