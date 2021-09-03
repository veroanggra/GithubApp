package com.veronica.idn.githubapp.activity

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.veronica.idn.githubapp.R
import com.veronica.idn.githubapp.adapter.UserAdapter
import com.veronica.idn.githubapp.model.Users
import com.veronica.idn.githubapp.databinding.ActivityMainBinding
import com.veronica.idn.githubapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var listUser: ArrayList<Users>
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setUserAdapter()
        showProgressBar(true)
        setViewModel()
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )
        if (mainViewModel.getListUser().value == null) {
            mainViewModel.setListUser(this, "")
        }
        mainViewModel.getListUser().observe(this, { users ->
            if (users != null) {
                listUser = users
                userAdapter.setData(listUser)
                showProgressBar(false)

            }
        })
    }

    private fun showProgressBar(b: Boolean) {
        if (b) {
            mainBinding.pbMain.visibility = View.VISIBLE
        } else {
            mainBinding.pbMain.visibility = View.GONE
        }
    }

    private fun setUserAdapter() {
        userAdapter = UserAdapter()
        userAdapter.notifyDataSetChanged()

        mainBinding.rvMain.apply {
            setHasFixedSize(true)
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val search = menu?.findItem(R.id.ic_search)
        search?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                showProgressBar(true)
                mainViewModel.setListUser(this@MainActivity, "")
                return true
            }
        })
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            menu?.findItem(R.id.ic_search)?.actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "User Name"
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showProgressBar(true)
                mainViewModel.setListUser(this@MainActivity, query)
                closeKeyboard()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun closeKeyboard() {
        val view: View? = this.currentFocus

        if (view != null) {
            val iMM: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            iMM.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}