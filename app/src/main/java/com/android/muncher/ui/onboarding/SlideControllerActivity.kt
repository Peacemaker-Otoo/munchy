package com.android.muncher.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.android.muncher.databinding.ActivitySlideControllerBinding
import com.android.muncher.ui.adapter.ScreenSlidePagerAdapter
import com.android.muncher.ui.adapter.ZoomOutPageTransformer

class SlideControllerActivity : FragmentActivity() {

    private var _binding: ActivitySlideControllerBinding? = null
    private val binding get() = _binding!!


    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySlideControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.pager
        val zoomOutPageTransformer = ZoomOutPageTransformer()
        viewPager.setPageTransformer{page, position ->
        zoomOutPageTransformer.transformPage(page,position)
        }

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        viewPager.adapter = pagerAdapter

        //setting the pager indicator
       binding.dotsIndicator.attachTo(viewPager)
    }

    override fun onBackPressed() {
        if (viewPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}