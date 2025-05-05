package com.dimas.mycontacts.domain.usecases

import com.dimas.mycontacts.domain.ContactsRepository
import com.dimas.mycontacts.domain.model.Contact

class GetContactsUseCase(
    private val repository: ContactsRepository
) : suspend () -> List<Contact> {

    override suspend fun invoke(): List<Contact> {
        return repository.getContactsList()
    }
}