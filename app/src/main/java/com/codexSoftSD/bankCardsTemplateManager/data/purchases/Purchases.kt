package com.codexSoftSD.bankCardsTemplateManager.data.purchases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Purchases{
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
    @ColumnInfo(name = "smsId") var smsId: Long = 0
    @ColumnInfo(name = "smsTitle") var smsTitle = ""
    @ColumnInfo(name = "amount") var amount: Double = 0.0
    @ColumnInfo(name = "currency") var currency: String = "SAR"
    @ColumnInfo(name = "card") var card: String = ""
    @ColumnInfo(name = "account") var account: String = ""
    @ColumnInfo(name = "from") var from: String = ""
    @ColumnInfo(name = "date") var date: String = ""
    @ColumnInfo(name = "isEnabled") var enabled: Boolean = true
//    fun getDate():Date{
//        var date : Date? = null
//
//        try{
//            date = SimpleDateFormat(ConstantsOfApp.GeneralDateTimeFormat,Locale.ENGLISH).parse(this.date)
//        }
//        catch (e: Exception){
//            Utils.handleException(e)
//        }
//        return date ?: Date()
//    }
    override fun toString(): String {
        return "smsId: $smsId smsTitle: $smsTitle \n amount: $amount \n currency: $currency \n card: $card \n account: $account \n from: $from date: $date"
    }
}