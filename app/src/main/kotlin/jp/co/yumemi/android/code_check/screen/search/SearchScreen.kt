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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.http.SortType
import jp.co.yumemi.android.code_check.data.repository.Repository
import jp.co.yumemi.android.code_check.data.ui.AppTheme
import jp.co.yumemi.android.code_check.data.ui.ErrorDialog
import jp.co.yumemi.android.code_check.data.ui.LoadingDialog
import jp.co.yumemi.android.code_check.data.ui.OnBottomReached
import jp.co.yumemi.android.code_check.mock.searchViewModelMock
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onSelect: (Repository, lastUpdate: Long) -> Unit
) = Box(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
) {

    val eventListener = viewModel.eventListener

    val uiModel = viewModel.uiModel.collectAsState()

    val uiError = viewModel.error.collectAsState()

    Column {
        InputField(
            uiModel = uiModel.value,
            event = eventListener
        )
        Spacer(modifier = Modifier.height(5.dp))

        val items = uiModel.value.searchResults
        val expanded = remember { mutableStateOf(false) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 3.dp)
        ) {
            Button(
                onClick = {
                    expanded.value = true
                },
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "sort",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = uiModel.value.sortType.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (items.isNotEmpty()) {
                Text(
                    text = stringResource(id = R.string.search_result, items.size),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                if (items.size >= SearchUiModel.MAX_SEARCH_RESULT) {
                    Text(
                        text = "MAX",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(horizontal = 2.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                offset = DpOffset(0.dp, 0.dp),
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sort),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                SortType.entries.forEach { sortType ->
                    Text(
                        text = sortType.value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clickable {
                                eventListener.onSetSort(sortType)
                                expanded.value = false
                            }
                            .fillMaxWidth()
                            .padding(bottom = 2.dp, top = 15.dp)
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }


        SearchList(
            items = uiModel.value.searchResults,
            onFetchMore = {
                eventListener.onFetchMore()
            },
            onSelect = { repository ->
                onSelect(repository, uiModel.value.lastUpdated)
            }
        )

    }

    LoadingDialog(isVisible = uiModel.value.isLoading)
    ErrorDialog(
        error = uiError.value,
        onDismiss = eventListener.onErrorClose,
        onRetry = eventListener.onErrorRetry
    )
}


@Composable
private fun InputField(
    uiModel: SearchUiModel,
    event: SearchScreenUiEvent
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 20.dp)
    ) {
        TextField(
            value = uiModel.inputText,
            onValueChange = {
                event.onInputTextChanged(it)
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            singleLine = true,
            enabled = uiModel.isLoading.not(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.searchInputText_hint),
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    modifier = Modifier
                )
            },
            leadingIcon = {
                FilledTonalIconButton(
                    onClick = {
                        event.onSearchComplete()
                    },
                    enabled = uiModel.isLoading.not() && uiModel.inputText.isNotEmpty()
                ) {
                    Image(
                        Icons.Outlined.Search,
                        contentDescription = "search",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            },
            trailingIcon = {
                if (uiModel.inputText.isNotEmpty()) {
                    IconButton(
                        onClick = { event.onInputTextChanged("") }
                    ) {
                        Image(
                            Icons.Rounded.Clear,
                            contentDescription = "clear",
                            modifier = Modifier
                                .size(20.dp)
                        )
                    }
                }
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
        )
    }
}


@Composable
private fun ColumnScope.SearchList(
    items: List<Repository>,
    onFetchMore: () -> Unit,
    onSelect: (Repository) -> Unit
) = Column(
    modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
) {
    val listState = rememberLazyListState()
    if (items.isNotEmpty()) {
        listState.OnBottomReached(buffer = 3) {
            onFetchMore()
        }
    }

    Box(
        modifier = Modifier
            .weight(1f)
            .padding(bottom = 10.dp)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            items(
                count = items.size,
                key = { index -> items[index].id }
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
        // draw behind scrollable area to show gradient
        Column(
            modifier = Modifier
                .matchParentSize()
        ) {
            val height = 80.dp
            if (listState.canScrollBackward) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceContainer,
                                    Color.Transparent
                                )
                            )
                        )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (listState.canScrollForward) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.surfaceContainer
                                )
                            )
                        )
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

@Composable
@Preview
private fun Preview() {
    AppTheme {
        SearchScreen(
            viewModel = searchViewModelMock,
            onSelect = { _, _ -> }
        )
    }
}