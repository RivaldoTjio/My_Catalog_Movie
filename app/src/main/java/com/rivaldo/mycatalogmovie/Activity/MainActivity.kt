package com.rivaldo.mycatalogmovie.Activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import com.rivaldo.mycatalogmovie.Adapter.NewMoviesAdapter
import com.rivaldo.mycatalogmovie.Fragment.MovieFragment
import com.rivaldo.mycatalogmovie.R
import com.rivaldo.mycatalogmovie.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NewMoviesAdapter


    companion object;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)

//        val fm: FragmentManager = supportFragmentManager
//        //val f : MovieFragment = fm.findFragmentByTag("MovieFragment") as MovieFragment
//        val f: MovieFragment? =
//            fm.findFragmentByTag("MovieFragment") as MovieFragment?

//        val fm: FragmentManager = supportFragmentManager
//        val frag: MovieFragment? = fm.findFragmentById(R.id.Fragment_movie) as MovieFragment?
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu.findItem(R.id.search).actionView as SearchView
//
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = resources.getString(R.string.search_hint)
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//                frag?.setMode(true, query)
////                val currentFragment = supportFragmentManager.findFragmentByTag("Movie Fragment")
////                val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//////                fragmentTransaction.detach(currentFragment)
//////                fragmentTransaction.attach(currentFragment)
//////                fragmentTransaction.commit()
////                val manager = supportFragmentManager
////                val transaction = manager.beginTransaction()
////                transaction.add(R.layout.fragment_movie.toInt(), MovieFragment())
////                transaction.commit()
//
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//                return false
//            }
//        })


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        if (item.itemId == R.id.action_favorite_list) {
            val mIntent = Intent(this, FavoriteList::class.java)
            startActivity(mIntent)
        }
        if (item.itemId == R.id.reminder) {
            val mIntent = Intent(this,ReminderSettings::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun search(searchView: SearchView) {

    }

    interface IDataCallback


}






