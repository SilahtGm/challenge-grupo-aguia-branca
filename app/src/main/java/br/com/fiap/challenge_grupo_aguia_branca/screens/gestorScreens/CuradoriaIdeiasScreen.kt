package br.com.fiap.challenge_grupo_aguia_branca.screens.gestorScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.data.model.IdeiaResponse
import br.com.fiap.challenge_grupo_aguia_branca.data.model.STATUS_IDEIA_APROVADA
import br.com.fiap.challenge_grupo_aguia_branca.data.model.STATUS_IDEIA_PENDENTE
import br.com.fiap.challenge_grupo_aguia_branca.data.model.STATUS_IDEIA_REJEITADA
import br.com.fiap.challenge_grupo_aguia_branca.data.model.statusIdeiaLabel
import br.com.fiap.challenge_grupo_aguia_branca.navigation.GestorBottomBar
import br.com.fiap.challenge_grupo_aguia_branca.navigation.GestorDestination
import br.com.fiap.challenge_grupo_aguia_branca.navigation.InovaTopBar
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulClaro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaVerde
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel

@Composable
fun CuradoriaIdeiasScreen(
    viewModel: ApiViewModel = viewModel(),
    onNavigate: (GestorDestination) -> Unit = {},
    onVoltarClick: () -> Unit = {}
) {
    val ideias by viewModel.ideias.collectAsState()
    var ideiaParaReprovar by remember { mutableStateOf<IdeiaResponse?>(null) }

    LaunchedEffect(Unit) {
        viewModel.listarIdeias()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InovaCinzaFundo)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            InovaTopBar(
                titulo = "Curadoria de Ideias",
                mostrarVoltar = true,
                onVoltarClick = onVoltarClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 14.dp)
                    .padding(top = 14.dp, bottom = 80.dp)
            ) {
                if (ideias.isEmpty()) {
                    Text(
                        text = "Sem ideias para análise.",
                        color = InovaCinzaTexto,
                        fontSize = 12.sp
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(ideias) { ideia ->
                            CuradoriaIdeiaCard(
                                ideia = ideia,
                                onAprovar = { viewModel.aprovarIdeia(ideia.id) },
                                onReprovar = { ideiaParaReprovar = ideia }
                            )
                        }
                    }
                }
            }
        }

        GestorBottomBar(
            currentScreen = GestorDestination.IDEIAS,
            onNavigate = onNavigate,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        ideiaParaReprovar?.let { ideia ->
            ReprovarIdeiaDialog(
                onConfirmar = { motivo ->
                    viewModel.rejeitarIdeia(ideia.id, motivo)
                    ideiaParaReprovar = null
                },
                onDismiss = { ideiaParaReprovar = null }
            )
        }
    }
}

@Composable
fun ReprovarIdeiaDialog(
    onConfirmar: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var motivo by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Motivo da reprovação",
                    color = InovaAzulEscuro,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = motivo,
                    onValueChange = { motivo = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Explique por que a ideia foi reprovada") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    CuradoriaButton(
                        label = "Cancelar",
                        backgroundColor = InovaCinzaTexto,
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    CuradoriaButton(
                        label = "Confirmar",
                        backgroundColor = InovaAzulEscuro,
                        onClick = { if (motivo.isNotBlank()) onConfirmar(motivo.trim()) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CuradoriaIdeiaCard(
    ideia: IdeiaResponse,
    onAprovar: () -> Unit,
    onReprovar: () -> Unit
) {
    val statusColor = when (ideia.status) {
        STATUS_IDEIA_APROVADA -> InovaVerde
        STATUS_IDEIA_REJEITADA -> InovaAzulClaro
        else -> InovaCinzaTexto
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = InovaPreto.copy(alpha = 0.08f),
                spotColor = InovaPreto.copy(alpha = 0.08f)
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = InovaBranco)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = ideia.titulo,
                    color = InovaAzulEscuro,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = statusIdeiaLabel(ideia.status),
                        color = InovaBranco,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = ideia.descricao,
                color = InovaCinzaTexto,
                fontSize = 11.sp
            )

            if (ideia.status == STATUS_IDEIA_PENDENTE) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CuradoriaButton(
                        label = "Aprovar",
                        backgroundColor = InovaVerde,
                        onClick = onAprovar,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    CuradoriaButton(
                        label = "Reprovar",
                        backgroundColor = InovaAzulEscuro,
                        onClick = onReprovar,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun CuradoriaButton(
    label: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(36.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = InovaBranco,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CuradoriaIdeiasScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        CuradoriaIdeiasScreen()
    }
}
