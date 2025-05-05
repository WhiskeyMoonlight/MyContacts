package com.dimas.mycontacts.presentation.contacts

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.dimas.mycontacts.R
import com.dimas.mycontacts.presentation.contacts.components.ContactCard
import com.dimas.mycontacts.presentation.contacts.components.Letter
import com.dimas.mycontacts.presentation.contacts.components.LoadingScreen
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreenRoot(
    viewModel: ContactsViewModel = koinViewModel()
) {
    val state = viewModel.collectAsState().value

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ContactsTopAppBar(
                scrollBehavior = scrollBehavior,
                modifier = Modifier.clickable {
                    viewModel.onAction(ContactsAction.OnTitleClick)
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            ContactsScreen(
                state = state,
                onAction = viewModel::onAction,
            )
        }
    }


    val context = LocalContext.current
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ContactsSideEffect.CallNumber -> {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = "tel:${sideEffect.number}".toUri()
                context.startActivity(callIntent)
            }

            ContactsSideEffect.ShowHelloMessage -> {
                val text = context.getString(R.string.hello_message)
                Toast
                    .makeText(context, text, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.primary
            )
        },
        modifier = modifier
    )
}

@Composable
fun ContactsScreen(
    state: ContactsState,
    onAction: (ContactsAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        LoadingScreen(modifier = modifier)
    } else {
        ContactsList(
            contacts = state.contacts,
            onContactClick = { contactUi ->
                onAction(ContactsAction.OnContactClick(contactUi))
            }
        )
    }

}

@Composable
fun ContactsList(
    contacts: List<ContactUiModel>,
    onContactClick: (ContactUiModel.ContactUiItem) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(dimensionResource(R.dimen.padding_small))
) {
    if (contacts.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = dimensionResource(R.dimen.padding_small)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.empty_contacts_list_message))
        }
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = contentPadding
        ) {
            items(items = contacts) { contactUi ->
                when (contactUi) {
                    is ContactUiModel.ContactLetter -> {
                        Letter(
                            contactUi.letter,
                            modifier = Modifier
                                .padding(horizontal = dimensionResource(R.dimen.padding_small))
                        )
                    }

                    is ContactUiModel.ContactUiItem -> {
                        ContactCard(
                            contact = contactUi,
                            onContactClick = onContactClick,
                        )
                    }
                }
            }
        }
    }
}

