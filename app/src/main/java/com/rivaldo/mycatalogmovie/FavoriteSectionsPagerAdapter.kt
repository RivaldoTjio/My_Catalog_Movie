package com.rivaldo.mycatalogmovie

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rivaldo.mycatalogmovie.Fragment.FavoriteMovie
import com.rivaldo.mycatalogmovie.Fragment.FavoriteTv

class FavoriteSectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
){

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_favorite_movie, R.string.tab_favorite_tv)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
                0 -> fragment = FavoriteMovie()
                1 -> fragment = FavoriteTv()
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }


    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}