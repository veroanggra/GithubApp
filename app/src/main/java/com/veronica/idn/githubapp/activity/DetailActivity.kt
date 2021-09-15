package com.veronica.idn.githubapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.veronica.idn.githubapp.R
import com.veronica.idn.githubapp.adapter.ViewPagerAdapter
import com.veronica.idn.githubapp.databinding.ActivityDetailBinding
import com.veronica.idn.githubapp.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var sectionPagerAdapter: ViewPagerAdapter
    private lateinit var tvFollowers: TextView
    private lateinit var viewPager: ViewPager2
    private lateinit var tvFollowing: TextView

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        setViewPager()
        setViewModel(username = )

    }

    private fun setViewModel(username: String) {
        detailViewModel = ViewModelProvider(this,
            ViewModelProvider
            .NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(username, this)
        detailViewModel.getDetailUser().observe(this, {user ->
            if (user != null){

            }
        })
    }

    private fun setViewPager() {
        sectionPagerAdapter = ViewPagerAdapter(this)
        viewPager = detailBinding.vpDetail
        viewPager.adapter = sectionPagerAdapter
    }
}