package br.com.fiap.challenge_grupo_aguia_branca.screens.liderScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroRequest
import br.com.fiap.challenge_grupo_aguia_branca.navigation.InovaTopBar
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulClaro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinza
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaBorda
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaPlaceholder
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaPreto
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel
import java.time.Instant

@Composable
fun NovaDiretrizScreen(
    viewModel: ApiViewModel = viewModel(),
    diretrizId: String? = null,
    onVoltarClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    onSalvarClick: () -> Unit = {}
) {
    val diretrizes by viewModel.registros.collectAsState()
    val erro by viewModel.erro.collectAsState()
    val usuario by viewModel.usuarioLogado.collectAsState()

    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    LaunchedEffect(diretrizId, diretrizes) {
        if (diretrizId != null) {
            val atual = diretrizes.firstOrNull { it.id == diretrizId }
            if (atual != null) {
                titulo = atual.nome ?: atual.titulo ?: ""
                descricao = atual.descricao ?: ""
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InovaCinzaFundo)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            InovaTopBar(
                titulo = if (diretrizId == null) "Nova Diretriz" else "Editar Diretriz",
                mostrarVoltar = true,
                onVoltarClick = onVoltarClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 18.dp, bottom = 18.dp)
            ) {
                LabelDiretriz(text = "Título da Diretriz *")
                Spacer(modifier = Modifier.height(8.dp))
                CampoTexto(
                    value = titulo,
                    onValueChange = { titulo = it },
                    placeholder = "Ex: Redução de Custos",
                    height = 46.dp
                )

                Spacer(modifier = Modifier.height(16.dp))

                LabelDiretriz(text = "Descrição *")
                Spacer(modifier = Modifier.height(8.dp))
                CampoTexto(
                    value = descricao,
                    onValueChange = { descricao = it },
                    placeholder = "Reduzir custos operacionais em 15% até 2026",
                    height = 130.dp,
                    singleLine = false
                )

                Spacer(modifier = Modifier.height(24.dp))

                erro?.let {
                    Text(
                        text = it,
                        color = InovaAzulClaro,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BotaoDiretriz(
                        label = "Cancelar",
                        background = InovaCinza,
                        textColor = InovaAzulEscuro,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        onClick = onCancelarClick
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    BotaoDiretriz(
                        label = if (diretrizId == null) "Criar" else "Salvar",
                        background = InovaAzulClaro,
                        textColor = InovaBranco,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        onClick = {
                            if (titulo.isBlank() || descricao.isBlank()) {
                                return@BotaoDiretriz
                            }
                            if (diretrizId == null) {
                                viewModel.cadastrarRegistro(
                                    RegistroRequest(
                                        dataCriado = Instant.now().toString(),
                                        tipo = "ORIENTACAO",
                                        titulo = titulo.trim(),
                                        descricao = descricao.trim(),
                                        categoria = "ESTRATEGICA",
                                        status = "ATIVA",
                                        prioridade = "ALTA",
                                        operadorId = "",
                                        orientacaoId = "",
                                        nome = titulo.trim(),
                                        etapa = "",
                                        investimento = 0.0,
                                        retornoFinanceiro = 0.0,
                                        reducaoCustos = 0.0,
                                        ganhoProdutividade = 0.0,
                                        prazo = "",
                                        ideiaId = "",
                                        gestorId = usuario?.id ?: "",
                                        ativo = true
                                    ),
                                    aoConcluir = onSalvarClick
                                )
                            } else {
                                viewModel.atualizarRegistroParcial(
                                    id = diretrizId,
                                    campos = mapOf(
                                        "nome" to titulo.trim(),
                                        "titulo" to titulo.trim(),
                                        "descricao" to descricao.trim()
                                    ),
                                    tipo = "ORIENTACAO"
                                )
                                onSalvarClick()
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LabelDiretriz(text: String) {
    Text(
        text = text,
        color = InovaAzulEscuro,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CampoTexto(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    height: Dp,
    singleLine: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = InovaPreto.copy(alpha = 0.05f),
                spotColor = InovaPreto.copy(alpha = 0.05f)
            )
            .background(
                color = InovaBranco,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = InovaCinzaBorda,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            textStyle = TextStyle(
                color = InovaPreto,
                fontSize = 13.sp,
                lineHeight = 18.sp
            ),
            modifier = Modifier.fillMaxSize(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = if (singleLine) Alignment.CenterStart else Alignment.TopStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = InovaCinzaPlaceholder,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun BotaoDiretriz(
    label: String,
    background: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = background,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NovaDiretrizScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        NovaDiretrizScreen()
    }
}
