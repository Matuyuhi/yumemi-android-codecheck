package jp.co.yumemi.android.code_check.screen.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jp.co.yumemi.android.code_check.screen.ResultScreenArgsModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResultScreen(
    model: ResultScreenArgsModel
) = Box(
    modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    val viewModel: ResultViewModel = koinViewModel()
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 50.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = model.avatarUrl,
                contentDescription = model.login,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 20.dp)
                    .clip(RoundedCornerShape(40.dp))
            )
            Text(
                text = model.fullName,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
            )
        }
    }

}