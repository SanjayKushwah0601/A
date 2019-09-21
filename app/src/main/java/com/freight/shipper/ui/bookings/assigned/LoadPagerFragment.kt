package com.freight.shipper.ui.bookings.assigned

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseFragment
import com.freight.shipper.ui.bookings.assigned.frgments.ActiveLoadFragment
import com.freight.shipper.ui.bookings.assigned.frgments.PastLoadFragment
import com.freight.shipper.ui.bookings.assigned.pager.LoadListFragment
import com.freight.shipper.ui.bookings.assigned.pager.LoadPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_load_pager.*

class LoadPagerFragment : BaseFragment(), ViewPager.OnPageChangeListener {

    companion object {
        fun newInstance() = LoadPagerFragment()
    }

    var currentPage: Int = 1
    lateinit var pagerAdapter: LoadPagerAdapter

    val fragments by lazy {
        listOf(
            ActiveLoadFragment.newInstance(getString(R.string.active)),
            PastLoadFragment.newInstance(getString(R.string.past))
        )
    }


    //    private lateinit var viewModel: LoadPagerViewModel
//
    override fun getLayoutId(): Int = R.layout.fragment_load_pager


//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(LoadPagerViewModel::class.java)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()
    }

    fun setActiveLoadPage() {
        viewPager?.currentItem = 1
    }

    private fun initPager() {
        pagerAdapter = LoadPagerAdapter(
            fragments,
            childFragmentManager
        )
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(this)
//            viewPager.offscreenPageLimit = 3
//        viewPager.currentItem = currentPage
        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = TabLayout.MODE_FIXED
    }

    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(position: Int) {
//        editing = false
        (pagerAdapter.getItem(currentPage) as LoadListFragment<*>).setEditMode(false)
        currentPage = position
//        invalidateOptionsMenu()
    }
}
