package com.dimas.mycontacts.presentation.mappers

import com.dimas.mycontacts.domain.model.Contact
import com.dimas.mycontacts.presentation.contacts.ContactUiModel

fun Contact.toUiItem(): ContactUiModel.ContactUiItem {
    return ContactUiModel.ContactUiItem(
        name = this.name,
        number = this.number,
        avatar = this.image
    )
}