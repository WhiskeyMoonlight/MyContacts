package com.dimas.mycontacts.di

import com.dimas.mycontacts.data.AndroidContactsRepository
import com.dimas.mycontacts.domain.ContactsRepository
import com.dimas.mycontacts.domain.usecases.GetContactsUseCase
import com.dimas.mycontacts.presentation.contacts.ContactsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val contactsModule = module {
//    singleOf(::AndroidContactsRepository).bind(ContactsRepository::class)
    single { AndroidContactsRepository(androidContext()) } bind ContactsRepository::class

    singleOf(::GetContactsUseCase)
    viewModelOf(::ContactsViewModel)
}