/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 *
 * after: edit by matuyuhi
 */
package jp.co.yumemi.android.code_check.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.co.yumemi.android.code_check.data.repository.Repository
import jp.co.yumemi.android.code_check.data.ui.Loading
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    onSelect: (Repository, lastUpdate: Long) -> Unit
) = Box(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
) {
    val viewModel: SearchViewModel = koinViewModel()

    val uiModel = viewModel.uiModel.collectAsState()

    Column {
        InputField(
            uiModel = uiModel.value,
            event = viewModel.eventListener
        )
        Spacer(modifier = Modifier.height(30.dp))

        SearchList(
            items = uiModel.value.searchResults,
            onSelect = { repository ->
                onSelect(repository, uiModel.value.lastUpdated)
            }
        )

    }

    Loading(isVisible = uiModel.value.isLoading)
}


@Composable
private fun InputField(
    uiModel: SearchUiModel,
    event: SearchScreenUiEvent
) {
    TextField(
        value = uiModel.inputText,
        onValueChange = {
            event.onInputTextChanged(it)
        },
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        singleLine = true,
        enabled = uiModel.isLoading.not(),
        placeholder = {
            Text(
                text = "Search",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
            )
        },
        trailingIcon = {
            Image(
                Icons.Outlined.Search,
                contentDescription = "search",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(45.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .clickable { event.onSearchComplete() }
                    .padding(5.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { event.onSearchComplete() }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .border(1.dp, MaterialTheme.colorScheme.primary)
    )
}


@Composable
private fun ColumnScope.SearchList(
    items: List<Repository>,
    onSelect: (Repository) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 20.dp)
    ) {
        items(
            count = items.size,
        ) { index ->
            Column {
                val item = items[index]
                RepositoryItem(
                    repository = item,
                    onClick = { onSelect(item) }
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun RepositoryItem(
    repository: Repository,
    onClick: () -> Unit
) {
    val owner = repository.owner
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = owner.avatarUrl,
            contentDescription = owner.login,
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp))
        )
        Text(
            text = repository.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f),
        )

        Image(
            Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = "info",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
            modifier = Modifier
                .size(20.dp)
        )
    }
}