package com.dimas.mycontacts.domain.model

sealed interface ContactImage {
    data class UriAvatar(val uri: String) : ContactImage
    data object EmptyAvatar : ContactImage
}