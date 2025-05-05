package com.dimas.mycontacts.presentation.contacts

sealed interface ContactsAction {
    data class OnContactClick(val contact: ContactUiModel.ContactUiItem) : ContactsAction
    data object OnTitleClick : ContactsAction
}