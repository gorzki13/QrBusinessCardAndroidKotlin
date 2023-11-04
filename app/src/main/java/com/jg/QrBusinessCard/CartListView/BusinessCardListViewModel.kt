package com.jg.QrBusinessCard.CartListView

import androidx.lifecycle.ViewModel
import com.jg.QrBusinessCard.Models.BusinessCard

class BusinessCardListViewModel  : ViewModel() {


    fun getAllBussinesCards():MutableList<BusinessCard>{

        return  mutableListOf<BusinessCard>(
            BusinessCard(1, "Adam", "Nowak", 123456789, "adam.nowak@example.com"),
            BusinessCard(2, "Barbara", "Kowalska", 234567890, "barbara.kowalska@example.com"),
            BusinessCard(3, "Cezary", "Wiśniewski", 345678901, "cezary.wisniewski@example.com"),
            BusinessCard(4, "Dorota", "Lis", 456789012, "dorota.lis@example.com"),
            BusinessCard(5, "Edward", "Zając", 567890123, "edward.zajac@example.com"),
            BusinessCard(6, "Filip", "Kowalczyk", 678901234, "filip.kowalczyk@example.com"),
            BusinessCard(7, "Grażyna", "Wójcik", 789012345, "grazyna.wojcik@example.com"),
            BusinessCard(8, "Henryk", "Dąbrowski", 890123456, "henryk.dabrowski@example.com"),
            BusinessCard(9, "Izabela", "Nowakowska", 901234567, "izabela.nowakowska@example.com"),
            BusinessCard(10, "Jan", "Kowal", 112233445, "jan.kowal@example.com"),
            BusinessCard(11, "Karolina", "Lewandowska", 223344556, "karolina.lewandowska@example.com"),
            BusinessCard(12, "Łukasz", "Olejnik", 334455667, "lukasz.olejnik@example.com"),
            BusinessCard(13, "Monika", "Jasińska", 445566778, "monika.jasinska@example.com"),
            BusinessCard(14, "Norbert", "Wiśniewski", 556677889, "norbert.wisniewski@example.com"),
            BusinessCard(15, "Oliwia", "Kwiatkowska", 667788990, "oliwia.kwiatkowska@example.com"),
            BusinessCard(16, "Piotr", "Kaczmarek", 778899001, "piotr.kaczmarek@example.com"),
            BusinessCard(17, "Renata", "Zawadzka", 889900112, "renata.zawadzka@example.com"),
            BusinessCard(18, "Szymon", "Adamski", 990011223, "szymon.adamski@example.com"),
            BusinessCard(19, "Teresa", "Mazurek", 100112334, "teresa.mazurek@example.com"),
            BusinessCard(20, "Urszula", "Witkowska", 111223445, "urszula.witkowska@example.com")
        )
    }
}