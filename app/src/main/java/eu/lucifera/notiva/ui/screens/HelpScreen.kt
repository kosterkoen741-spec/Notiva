package eu.lucifera.notiva.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import eu.lucifera.notiva.R

@Composable
fun HelpScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = stringResource(R.string.help_title), style = MaterialTheme.typography.headlineMedium)
        Text(text = stringResource(R.string.help_welcome))
        Text(text = stringResource(R.string.help_add_note))
        Text(text = stringResource(R.string.help_delete_note))
    }
}
