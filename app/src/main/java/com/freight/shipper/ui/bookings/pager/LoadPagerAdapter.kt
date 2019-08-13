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

    override fun saveState(): Parcelable? {
        return null
    }
}