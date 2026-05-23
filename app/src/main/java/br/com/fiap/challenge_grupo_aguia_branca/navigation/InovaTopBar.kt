package br.com.fiap.challenge_grupo_aguia_branca.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto

@Composable
fun InovaTopBar(
    titulo: String,
    mostrarVoltar: Boolean = false,
    onVoltarClick: () -> Unit = {},
    mostrarLogout: Boolean = false,
    onLogoutClick: () -> Unit = {}
) {
    var menuAberto by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .background(InovaAzulEscuro)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (mostrarVoltar) {
            Text(
                text = "←",
                color = InovaBranco,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onVoltarClick() }
            )

            Spacer(modifier = Modifier.width(16.dp))
        }

        Text(
            text = titulo,
            color = InovaBranco,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.weight(1f))

        if (mostrarLogout) {
            Box {
                Text(
                    text = "➜]",
                    fontSize = 18.sp,
                    color = InovaBranco,
                    modifier = Modifier.clickable {
                        menuAberto = true
                    }
                )

                DropdownMenu(
                    expanded = menuAberto,
                    onDismissRequest = { menuAberto = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = "Deslogar",
                                color = InovaPreto,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        },
                        onClick = {
                            menuAberto = false
                            onLogoutClick()
                        }
                    )
                }
            }
        }
    }
}
