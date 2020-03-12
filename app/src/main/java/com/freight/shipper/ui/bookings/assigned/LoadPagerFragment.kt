package com.freight.shipper.ui.bookings.assigned

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.freight.shipper.R
import com.freight.shipper.core.platform.BaseFragment
import com.freight.shipper.ui.bookings.assigned.frgments.ActiveLoadFragment
import com.freight.shipper.ui.bookings.assigned.frgments.PastLoadFragment
import com.freight.shipper.ui.bookings.assigned.pager.LoadListFragment
import com.freight.shipper.ui.bookings.assigned.pager.LoadPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_load_pager.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadPagerFragment : BaseFragment(), ViewPager.OnPageChangeListener {

    companion object {
        const val ACTIVE_LOAD = 0
        const val PAST_LOAD = 1
        fun newInstance() = LoadPagerFragment()
    }

    private var currentPage: Int = ACTIVE_LOAD
    private var pageToBeShown = ACTIVE_LOAD
    private lateinit var pagerAdapter: LoadPagerAdapter

    private val fragments by lazy {
        listOf(
            ActiveLoadFragment.newInstance(getString(R.string.active)),
            PastLoadFragment.newInstance(getString(R.string.past))
        )
    }


    //    private lateinit var viewModel: LoadPagerViewModel
//
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_load_pager, container, false)
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(LoadPagerViewModel::class.java)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPager()
    }

    fun setPage(int: Int) {
        pageToBeShown = when (int) {
            ACTIVE_LOAD, PAST_LOAD -> int
            else -> currentPage
        }
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
        GlobalScope.launch(Dispatchers.Main) {
            delay(300)
            viewPager?.currentItem = pageToBeShown
        }
    }

    override fun onPageScrollStateChanged(p0: Int) {}

    override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {}

    override fun onPageSelected(position: Int) {
//        editing = false
        (pagerAdapter.getItem(currentPage) as LoadListFragment<*>).setEditMode(false)
        currentPage = position
        pageToBeShown = position
//        invalidateOptionsMenu()
    }
}
