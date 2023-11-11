package com.jg.QrBusinessCard.CartListView

import android.content.Context
import androidx.lifecycle.ViewModel
import com.jg.QrBusinessCard.DbHelper.DatabaseBuilder
import com.jg.QrBusinessCard.Models.BusinessCard

class BusinessCardListViewModel  : ViewModel() {


   suspend fun getAllBussinesCards(context:Context):List<BusinessCard>{

        return  DatabaseBuilder.getInstance(context).businessCardDao().getAllBusinessCards()
    }
}