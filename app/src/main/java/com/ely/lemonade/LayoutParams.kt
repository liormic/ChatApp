package com.ely.lemonade

import android.view.View
import com.ely.lemonadeserver.MessageTypes
import com.ely.lemonadeserver.Step

class LayoutParams() {
    var chatBoxVisiblity: Int = 0
    var buttonsContainerVisiblty: Int = 0
    var onlyDigits: Boolean = false
    var leftButtonText: String = MessageTypes.NO.toString()
    var rightButtonText: String = MessageTypes.YES.toString()

    fun setParamByStep(step: String) {
        when (step) {

            Step.STEP2_2.name -> onlyDigits = true
            Step.STEP3.name, Step.STEP5.name -> {
                chatBoxVisiblity = View.GONE
                buttonsContainerVisiblty = View.VISIBLE
                if(step == Step.STEP5.name){
                    leftButtonText = MessageTypes.RESTART.toString()
                    rightButtonText= MessageTypes.EXIT.toString()
                }  else {
                     leftButtonText = MessageTypes.NO.toString()
                     rightButtonText = MessageTypes.YES.toString()
                }
            } else -> {
                onlyDigits = false
                chatBoxVisiblity =  View.VISIBLE
                buttonsContainerVisiblty = View.GONE
            }

        }
    }
}