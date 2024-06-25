package jp.co.yumemi.android.code_check.screen.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.data.ui.AppTheme
import jp.co.yumemi.android.code_check.data.ui.replaceIfEmpty
import jp.co.yumemi.android.code_check.mock.mockResultScreenArgsModel
import jp.co.yumemi.android.code_check.screen.ResultScreenArgsModel
import jp.co.yumemi.android.code_check.screen.TestTags

@Composable
fun ResultScreen(
    model: ResultScreenArgsModel
) = Box(
    modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()
        .systemBarsPadding(),
    contentAlignment = Alignment.Center
) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 50.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        )
    ) {
        val uriHandler = LocalUriHandler.current
        IconButton(
            onClick = {
                uriHandler.openUri(model.url)
            },
            modifier = Modifier
                .testTag(TestTags.ResultScreenUrlOpenButton.name)
                .align(Alignment.End),
            enabled = model.url.isNotEmpty()
        ) {
            Icon(
                painterResource(id = R.drawable.ic_open_in_new),
                contentDescription = stringResource(id = R.string.open_in_new),
                modifier = Modifier
                    .size(24.dp)
            )
        }
        Text(
            text = stringResource(
                id = R.string.search_update_at,
                model.searchUpdatedAt.replaceIfEmpty()
            ),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AsyncImage(
                model = model.avatarUrl,
                contentDescription = model.login,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(40.dp))
                    .background(MaterialTheme.colorScheme.surfaceBright)
            )
            Text(
                text = model.fullName,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
            )
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.written_owner, model.login),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    model.licenceName.takeIf { it.isNotEmpty() }?.let {
                        Text(
                            text = stringResource(id = R.string.licence, model.licenceName),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(id = R.string.created_at, model.createdAt),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = stringResource(id = R.string.updated_at, model.updatedAt),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.language, model.language),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = stringResource(
                            id = R.string.star_count,
                            model.starCount.formatDigits()
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = stringResource(
                            id = R.string.watchers_count,
                            model.watchersCount.formatDigits()
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = stringResource(
                            id = R.string.forks_count,
                            model.forksCount.formatDigits()
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = stringResource(
                            id = R.string.open_issues_count,
                            model.openIssuesCount.formatDigits()
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }

}

@Composable
@Preview
private fun PreviewResultScreen() {
    AppTheme {
        ResultScreen(
            model = mockResultScreenArgsModel,
        )
    }
}