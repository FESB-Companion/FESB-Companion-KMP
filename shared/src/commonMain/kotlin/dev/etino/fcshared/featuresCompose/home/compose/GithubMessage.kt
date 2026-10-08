package dev.etino.fcshared.featuresCompose.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import dev.etino.fcshared.compose.AppTheme
import dev.etino.fcshared.compose.notesContainer
import dev.etino.fcshared.openUrl
import fesb_companion_shared.shared.generated.resources.Res
import fesb_companion_shared.shared.generated.resources.close
import fesb_companion_shared.shared.generated.resources.close_x
import fesb_companion_shared.shared.generated.resources.fesb_companion_je_sada_na_githubu
import fesb_companion_shared.shared.generated.resources.github
import fesb_companion_shared.shared.generated.resources.istra_i_projekt_ili_doprinesi_njegovom_razvoju_na_githubu
import fesb_companion_shared.shared.generated.resources.linkNaApp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GithubMessage(hideGithubMessage: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(notesContainer)
    ) {
        val link = stringResource(Res.string.linkNaApp)
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    openUrl(link)
                }
                .padding(start = 16.dp, top = 12.dp, end = 8.dp, bottom = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.github),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(Res.string.fesb_companion_je_sada_na_githubu),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = stringResource(Res.string.istra_i_projekt_ili_doprinesi_njegovom_razvoju_na_githubu),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        IconButton(
            onClick = hideGithubMessage,
            modifier = Modifier.padding(end = 4.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.close_x),
                contentDescription = stringResource(Res.string.close),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun GithubPreview() {
    AppTheme {
        Scaffold {
            Column(Modifier.padding(it)) { GithubMessage() }
        }
    }
}
