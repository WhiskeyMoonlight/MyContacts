package com.dimas.mycontacts.presentation.contacts

sealed interface ContactsSideEffect {
    data class CallNumber(val number: String) : ContactsSideEffect
    data object ShowHelloMessage : ContactsSideEffect
}