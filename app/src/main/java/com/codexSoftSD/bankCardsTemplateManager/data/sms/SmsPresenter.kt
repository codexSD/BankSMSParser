package com.codexSoftSD.bankCardsTemplateManager.data.sms

import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.codexSoftSD.bankCardsTemplateManager.MainApp
import com.codexSoftSD.bankCardsTemplateManager.utils.Utils
import kotlinx.coroutines.delay
import java.lang.Exception
import java.util.concurrent.atomic.AtomicBoolean

class SmsPresenter {
    companion object{
        private const val tag = "SmsPresenter";
        public val isLoaded = AtomicBoolean(false);
        public val hashMap :HashMap<String,ArrayList<Long>> = hashMapOf()
        private val smsMessages = arrayListOf<Sms>()
        private suspend fun getAllSms():List<Sms>{
            val listSms: ArrayList<Sms> = ArrayList()
            try{
                val cursor: Cursor? = MainApp.context.contentResolver.query(Uri.parse("content://sms/inbox"),null,null,null,null)
                if(cursor != null){
                    if(cursor.moveToFirst()){
                        do {
                            try{
                                val sms = Sms().apply {
                                    id = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
                                    address = cursor.getString(cursor.getColumnIndexOrThrow("address"))
                                    msg = cursor.getString(cursor.getColumnIndexOrThrow("body"))
                                    date = cursor.getLong(cursor.getColumnIndexOrThrow("date"))

                                }
                                Log.i(tag, "sms : $sms");
                                listSms.add(sms)
                            }
                            catch (e:Exception){
                                Utils.handleException(e);
                            }
                        } while (cursor.moveToNext())
                    }
                    cursor.close()
                }
            }
            catch(e: Exception){
                Utils.handleException(e)
            }

            return listSms
        }
        suspend fun loadAllSmsMessages(){
            try{
                isLoaded.set(false)
                hashMap.clear()
                smsMessages.clear()
                smsMessages.addAll(getAllSms())
                for (i in smsMessages.indices){
                    val sms = smsMessages[i]
                    val ids = hashMap[sms.address]?: arrayListOf()
                    ids.add(i.toLong())
//                    ids.add(sms.id.toLong())
                    hashMap[sms.address] = ids
                }
            }
            catch(e: Exception){
                Utils.handleException(e)
            }
            finally {
                isLoaded.set(true)
            }
        }
        suspend fun getSmsMessages():ArrayList<Sms>{
            while (!isLoaded.get())
                delay(100)
            return smsMessages
        }
    }

}