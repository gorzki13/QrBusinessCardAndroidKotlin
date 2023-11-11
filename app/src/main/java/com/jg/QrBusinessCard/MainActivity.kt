package com.jg.QrBusinessCard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.jg.QrBusinessCard.DbHelper.DatabaseBuilder
import com.jg.QrBusinessCard.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        setNavigation()
        DatabaseBuilder.getInstance(this)


    }


    private fun setNavigation(){

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.baseFragment) as NavHostFragment
        val fragments = mapOf(
            R.id.businessCardListFragment to "businessCardListFragment",
            R.id.qrCodeFragment to "qrCodeFragment",
        )

        binding.bottomAppBar.setOnItemSelectedListener{
                item-> fragments[item.itemId]?.let{
            navHostFragment.navController.navigate(item.itemId)
            true
        }?:false
        }

    }



}
