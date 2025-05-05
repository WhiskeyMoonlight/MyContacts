package com.dimas.mycontacts.presentation.contacts.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.dimas.mycontacts.R
import com.dimas.mycontacts.domain.model.ContactImage
import com.dimas.mycontacts.presentation.contacts.ContactUiModel
import com.dimas.mycontacts.presentation.ui.theme.Shapes

@Composable
fun ContactCard(
    contact: ContactUiModel.ContactUiItem,
    onContactClick: (ContactUiModel.ContactUiItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_small))
            .clickable { onContactClick(contact) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
        ) {
            ContactAvatar(
                avatar = contact.avatar,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.image_size))
                    .padding(dimensionResource(R.dimen.padding_small))
                    .aspectRatio(1f)
                    .clip(Shapes.medium)
            )
            Column(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
                )
                Text(
                    text = contact.number,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
private fun ContactAvatar(
    avatar: ContactImage,
    modifier: Modifier = Modifier
) {
    when (avatar) {
        ContactImage.EmptyAvatar -> {
            Image(
                painter = painterResource(R.drawable.default_avatar),
                contentDescription = stringResource(R.string.avatar_cd),
                modifier = modifier
            )
        }

        is ContactImage.UriAvatar -> {
            val uri = avatar.uri.toUri()

            val context = LocalContext.current
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(uri)
                    .crossfade(true)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = stringResource(R.string.avatar_cd),
                modifier = modifier
            )
        }
    }
}