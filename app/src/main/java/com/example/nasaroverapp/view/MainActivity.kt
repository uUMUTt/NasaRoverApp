package com.example.nasaroverapp.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.nasaroverapp.R
import com.example.nasaroverapp.adapter.ViewPagerAdapter
import com.example.nasaroverapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.plugins.RxJavaPlugins


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ViewPagerAdapter

    private lateinit var fragmentCuriosity: RoverFragment
    private lateinit var fragmentOpportunity: RoverFragment
    private lateinit var fragmentSpirit: RoverFragment

    private lateinit var selectedTabFragment: RoverFragment

    //first values
    private var solarDay = "10"
    private var cameraName = "all cameras"

    private lateinit var spinnerAdapter1: ArrayAdapter<Any>
    private lateinit var spinnerAdapter2: ArrayAdapter<Any>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxJavaPlugins.setErrorHandler {
            it.printStackTrace()
        }
        spinnerAdapter1 = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.curiosity_cameras)
        )
        spinnerAdapter2 = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.opportunity_spirit_cameras)
        )
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)

        setupTabs()

        setTabListener()

        filterListener()

        setSearchSolarDay()


    }

    private fun setupTabs() {
        binding.viewPager.adapter = getViewPagerAdapter()
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 ->
                    tab.text = "Curiosity"
                1 ->
                    tab.text = "Opportunity"
                2 ->
                    tab.text = "Spirit"
            }
        }.attach()
    }

    private fun setTabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                solarDay = "10"
                when (tab?.text.toString()) {
                    "Curiosity" -> {
                        selectedTabFragment = fragmentCuriosity
                        binding.spCamerasInTabbar.adapter = spinnerAdapter1
                    }
                    "Opportunity" -> {
                        binding.spCamerasInTabbar.adapter = spinnerAdapter2
                        selectedTabFragment = fragmentOpportunity
                    }
                    "Spirit" -> {
                        binding.spCamerasInTabbar.adapter = spinnerAdapter2
                        selectedTabFragment = fragmentSpirit
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
    }

    private fun getViewPagerAdapter(): ViewPagerAdapter {
        adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        fragmentCuriosity = RoverFragment("Curiosity")
        fragmentOpportunity = RoverFragment("Opportunity")
        fragmentSpirit = RoverFragment("Spirit")

        selectedTabFragment = fragmentCuriosity

        adapter.addFragment(fragmentCuriosity)
        adapter.addFragment(fragmentOpportunity)
        adapter.addFragment(fragmentSpirit)
        return adapter
    }

    private fun filterListener() {
        binding.spCamerasInTabbar.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    cameraName = adapterView?.getItemAtPosition(position).toString()
                    getDataByName()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
    }

    private fun getDataByName() {
        if (cameraName != "all cameras") {
            selectedTabFragment.viewModel.loadPhotosByCameraName(
                selectedTabFragment.roverName,
                cameraName,
                solarDay
            )
        } else {
            selectedTabFragment.viewModel.loadPhotosByRoverName(
                selectedTabFragment.roverName,
                solarDay
            )
        }
    }

    private fun setSearchSolarDay() {
        binding.ivSearchBtn.setOnClickListener {
            val tmpDay = binding.etSolarDay
            if (tmpDay.text.toString() != "") {
                solarDay = tmpDay.text.toString()
            }
            closeKeyboard()
            binding.etSolarDay.clearFocus()
            getDataByName()
            tmpDay.setText("")
        }
    }

    private fun closeKeyboard() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.llSearch.windowToken, 0)
    }

}