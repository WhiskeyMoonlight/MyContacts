package com.dimas.mycontacts.presentation.contacts

import com.dimas.mycontacts.domain.model.ContactImage

sealed interface ContactUiModel {
    data class ContactLetter(val letter: Char) : ContactUiModel
    data class ContactUiItem(
        val name: String,
        val number: String,
        val avatar: ContactImage
    ) : ContactUiModel
}