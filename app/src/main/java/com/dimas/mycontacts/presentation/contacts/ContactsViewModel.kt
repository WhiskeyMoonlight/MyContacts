package com.dimas.mycontacts.presentation.contacts

import androidx.lifecycle.ViewModel
import com.dimas.mycontacts.domain.model.Contact
import com.dimas.mycontacts.domain.usecases.GetContactsUseCase
import com.dimas.mycontacts.presentation.mappers.toUiItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class ContactsViewModel(
    private val getContactsUseCase: GetContactsUseCase
) : ViewModel(), ContainerHost<ContactsState, ContactsSideEffect> {

    override val container = container<ContactsState, ContactsSideEffect>(ContactsState()) {
        fetchContacts()
    }

    fun onAction(action: ContactsAction) {
        when (action) {
            is ContactsAction.OnContactClick -> handleOnContactClick(action.contact)
            ContactsAction.OnTitleClick -> handleOnTitleClick()
        }
    }

    private fun fetchContacts(): Job = intent {
        container.scope.launch {
            reduce { state.copy(isLoading = true) }

            val contactsResult = getContactsUseCase()

            val contactsUi = createUiModelList(contactsResult)

            reduce {
                state.copy(
                    isLoading = false,
                    contacts = contactsUi
                )
            }
        }
    }

    private fun handleOnContactClick(contact: ContactUiModel.ContactUiItem): Job = intent {
        postSideEffect(ContactsSideEffect.CallNumber(contact.number))
    }

    private fun handleOnTitleClick(): Job = intent {
        postSideEffect(ContactsSideEffect.ShowHelloMessage)
    }

    private suspend fun createUiModelList(domainList: List<Contact>): List<ContactUiModel> {
        return withContext(Dispatchers.IO) {
            val uiModelList = mutableListOf<ContactUiModel>()
            var currentLetter: Char? = null

            domainList.forEach { contact ->
                val firstLetter = contact.name.first().uppercaseChar()
                if (firstLetter != currentLetter) {
                    uiModelList.add(ContactUiModel.ContactLetter(firstLetter))
                    currentLetter = firstLetter
                }
                uiModelList.add(contact.toUiItem())
            }
            uiModelList
        }
    }
}