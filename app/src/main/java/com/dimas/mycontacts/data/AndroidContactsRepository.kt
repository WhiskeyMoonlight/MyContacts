package com.dimas.mycontacts.data

import android.content.Context
import android.provider.ContactsContract
import com.dimas.mycontacts.domain.model.Contact
import com.dimas.mycontacts.domain.ContactsRepository
import com.dimas.mycontacts.domain.model.ContactImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AndroidContactsRepository(
    private val context: Context
) : ContactsRepository {
    override suspend fun getContactsList(): List<Contact> {
        return withContext(Dispatchers.IO) {
            val contacts: MutableList<Contact> = mutableListOf()

            val cr = context.contentResolver
            cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PROJECTION,
                SELECTION,
                null,
                SORT_ORDER
            )?.use { cursor ->
                val idInd = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                val nameInd = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val phoneInd = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val imageInd = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(idInd)
                    val name = cursor.getString(nameInd) ?: Contact.EMPTY_CONTACT_NAME
                    val phone = cursor.getString(phoneInd) ?: Contact.EMPTY_CONTACT_NUMBER
                    val imageUri = cursor.getString(imageInd)

                    val avatar = if (imageUri != null) {
                        ContactImage.UriAvatar(imageUri)
                    } else {
                        ContactImage.EmptyAvatar
                    }

                    contacts.add(
                        Contact(
                            id = id,
                            name = name,
                            number = phone,
                            image = avatar
                        )
                    )
                }
            }
            contacts.distinctBy { it.name }
        }
    }

    companion object {
        private val PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )

        private val SELECTION = """
             ${ContactsContract.CommonDataKinds.Phone.TYPE} = ${ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE}
             AND ${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} IS NOT NULL
             AND ${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} != ''
            """.trimIndent()

        private const val SORT_ORDER = "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
    }
}