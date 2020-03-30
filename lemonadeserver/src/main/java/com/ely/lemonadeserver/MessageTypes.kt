package com.ely.lemonadeserver

enum class MessageTypes(val type:String) {
    STEP1_OPEN_MESSAGE("Hello, I am Lior"),
    STEP1_WHAT_IS_YOUR_NAME("What is your name?"),
    STEP2_NICE_TO_MEET_YOU("Nice to meet you"),
    STEP2_YOUR_PHONE("What is your phone number?"),
    STEP3_AGREE_MESSAGE("Do you agree to our terms of service?"),
    STEP4_THANKS_MESSAGE("Thanks!"),
    STEP4_LAST_STEP("This is the last step!"),
    STEP4_WHAT_DO_YOU_WANT("What do you want to do now?"),
    STEP5_BYE("Bye Bye!"),
    YES("YES"),
    NO("NO"),
    EXIT("EXIT"),
    RESTART("RESTART")
}

