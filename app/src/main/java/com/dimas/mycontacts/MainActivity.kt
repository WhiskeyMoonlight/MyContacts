package com.dimas.mycontacts

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dimas.mycontacts.presentation.contacts.ContactsScreenRoot
import com.dimas.mycontacts.presentation.contacts.components.PermissionHandler
import com.dimas.mycontacts.presentation.ui.theme.MyContactsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyContactsTheme {
                val contactsPermissionState = rememberMultiplePermissionsState(
                    listOf(
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE
                    )
                )
                if (contactsPermissionState.allPermissionsGranted) {
                    ContactsScreenRoot()
                } else {
                    PermissionHandler(contactsPermissionState)
                }
            }
        }
    }
}
