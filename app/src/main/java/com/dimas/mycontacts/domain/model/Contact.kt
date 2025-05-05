package com.dimas.mycontacts.domain.model

data class Contact(
    val id: Long,
    val name: String,
    val number: String,
    val image: ContactImage
) {
    companion object {
        const val EMPTY_CONTACT_NAME = "Unknown name"
        const val EMPTY_CONTACT_NUMBER = "No phone number"
    }
}
