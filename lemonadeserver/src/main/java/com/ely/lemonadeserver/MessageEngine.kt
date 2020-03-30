package com.ely.lemonadeserver

import com.google.gson.Gson
import java.util.*

class MessageEngine() {
    companion object {
        fun getCurrentMessage(requestStep: String, messageContent: String): String {
            var nextStep: Step = Step.STEP1_1
            var message: String = MessageTypes.STEP1_OPEN_MESSAGE.type
            var id: String = Calendar.getInstance().timeInMillis.toString()
            var waitingForResponse = false
            when (requestStep) {
                Step.STEP1_1.toString() -> {
                    nextStep = Step.STEP1_2
                    message = MessageTypes.STEP1_WHAT_IS_YOUR_NAME.type
                    waitingForResponse = true
                }

                Step.STEP1_2.toString() -> {
                    nextStep = Step.STEP2_1
                    message = "${MessageTypes.STEP2_NICE_TO_MEET_YOU.type} $messageContent"
                    waitingForResponse = false
                }

                Step.STEP2_1.toString() -> {
                    nextStep = Step.STEP2_2
                    message = MessageTypes.STEP2_YOUR_PHONE.type
                    waitingForResponse = true
                }

                Step.STEP2_2.toString() -> {
                    nextStep = Step.STEP3
                    message = MessageTypes.STEP3_AGREE_MESSAGE.type
                    waitingForResponse = true
                }

                Step.STEP3.toString() -> {
                    if (messageContent == MessageTypes.YES.toString()) {
                        nextStep = Step.STEP4_1
                        message = MessageTypes.STEP4_THANKS_MESSAGE.type
                    } else {
                        nextStep = Step.STEP6
                        message = MessageTypes.STEP5_BYE.type
                    }
                    waitingForResponse = false

                }

                Step.STEP4_1.toString() -> {
                    nextStep = Step.STEP4_2
                    message = MessageTypes.STEP4_LAST_STEP.type
                    waitingForResponse = false
                }

                Step.STEP4_2.toString() -> {
                    nextStep = Step.STEP5
                    message = MessageTypes.STEP4_WHAT_DO_YOU_WANT.type
                    waitingForResponse = true
                }

                Step.STEP5.toString() -> {
                    if (messageContent == MessageTypes.EXIT.toString()) {
                        nextStep = Step.STEP6
                        message = MessageTypes.STEP5_BYE.type
                    } else {
                        nextStep = Step.STEP1_1
                    }
                    waitingForResponse = false
                }
            }
            return Gson().toJson(
                Message(
                    id,
                    message,
                    Message.TYPE_BOT_MESSAGE,
                    nextStep.toString(),
                    waitingForResponse
                )
            )
        }
    }
}