package com.dimas.mycontacts.domain

import com.dimas.mycontacts.domain.model.Contact

interface ContactsRepository {
    suspend fun getContactsList() : List<Contact>
}