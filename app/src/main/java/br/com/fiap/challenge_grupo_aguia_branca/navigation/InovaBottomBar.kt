package br.com.fiap.challenge_grupo_aguia_branca.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinza

data class InovaBottomItem(
    val key: String,
    val icon: String,
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun InovaBottomBar(
    items: List<InovaBottomItem>,
    selectedKey: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(InovaBranco)
            .border(
                width = 1.dp,
                color = InovaCinza
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            InovaBottomItemView(
                item = item,
                selected = item.key == selectedKey,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun InovaBottomItemView(
    item: InovaBottomItem,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val selectedColor = InovaAzulEscuro
    val inactiveColor = androidx.compose.ui.graphics.Color(0xFF7B8494)

    val currentColor = if (selected) selectedColor else inactiveColor

    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable { item.onClick() }
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(selectedColor)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 7.dp, bottom = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.icon,
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = item.label,
                color = currentColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
