package com.dimas.mycontacts.presentation.contacts

import androidx.compose.runtime.Immutable

@Immutable
data class ContactsState(
    val isLoading: Boolean = true,
    val contacts: List<ContactUiModel> = emptyList()
)