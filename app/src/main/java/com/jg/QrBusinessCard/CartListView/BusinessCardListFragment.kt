package com.jg.QrBusinessCard.CartListView

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jg.QrBusinessCard.Models.BusinessCard
import com.jg.QrBusinessCard.R
import com.jg.QrBusinessCard.databinding.FragmentBusinessCardListBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BusinessCardListFragment : Fragment() {

    private lateinit var adapter: BusinessCardListAdapter
    private lateinit var binding: FragmentBusinessCardListBinding
    private val viewModel: BusinessCardListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBusinessCardListBinding.inflate(layoutInflater)
        val view = binding.root
        binding.businessCardRV.layoutManager = LinearLayoutManager(context)
        adapter = BusinessCardListAdapter(requireContext(), mutableListOf()) {
            showSingleCard(it.name+" "+it.surname,it.email,it.number)

        }
        GlobalScope.launch {
            // Operacje w tle
            val businessCards = viewModel.getAllBussinesCards(requireContext())


            withContext(Dispatchers.Main) {
                adapter.updateBusinessCardsList(businessCards.toMutableList())
            }
        }
        binding.businessCardRV.adapter = adapter
        // Inflate the layout for this fragment

        return view
    }




    @SuppressLint("SuspiciousIndentation")
    private fun showSingleCard(name:String, email:String, phone:Int) {
      val dialog=Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_business_card)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val tvName=dialog.findViewById<TextView>(R.id.name)
        val tVemail=dialog.findViewById<TextView>(R.id.email)
        val tVPhone=dialog.findViewById<TextView>(R.id.phone)
        val exitButton=dialog.findViewById<Button>(R.id.noButton)
        tvName.text=name
        tVemail.text=email
        tVPhone.text= "+48 $phone"
        exitButton.setOnClickListener { v -> dialog.dismiss() }
        dialog.show()

    }









}

