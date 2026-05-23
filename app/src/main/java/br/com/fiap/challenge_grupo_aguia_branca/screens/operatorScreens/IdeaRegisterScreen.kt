package br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun IdeaRegisterScreen(
    viewModel: ApiViewModel = viewModel(),
    onVoltarClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    onEnviarClick: () -> Unit = {}
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Selecione uma categoria") }
    var menuAberto by remember { mutableStateOf(false) }

    val usuario by viewModel.usuarioLogado.collectAsState()
    val erro by viewModel.erro.collectAsState()

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
                titulo = "Registrar Ideia",
                mostrarVoltar = true,
                onVoltarClick = onVoltarClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .padding(top = 14.dp, bottom = 14.dp)
            ) {
                FormCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Título da Ideia *",
                        color = InovaCinzaTexto,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    IdeaInput(
                        value = titulo,
                        onValueChange = { titulo = it },
                        placeholder = "Descreva sua ideia em poucas palavras",
                        height = 42.dp,
                        borderColor = InovaCinzaBorda,
                        placeholderColor = InovaCinzaPlaceholder
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                FormCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    Text(
                        text = "Descrição Detalhada *",
                        color = InovaCinzaTexto,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    IdeaInput(
                        value = descricao,
                        onValueChange = { descricao = it },
                        placeholder = "Explique o problema ou\noportunidade...",
                        height = 120.dp,
                        borderColor = InovaCinzaBorda,
                        placeholderColor = InovaCinzaPlaceholder,
                        singleLine = false
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                FormCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                ) {
                    Text(
                        text = "Categoria *",
                        color = InovaCinzaTexto,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Box {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp)
                                .border(
                                    width = 1.dp,
                                    color = InovaCinzaBorda,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { menuAberto = true }
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = categoriaSelecionada,
                                color = InovaPreto,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "⌄",
                                color = InovaPreto,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        DropdownMenu(
                            expanded = menuAberto,
                            onDismissRequest = { menuAberto = false }
                        ) {
                            listOf(
                                "Redução de Custos",
                                "Melhoria de Processo",
                                "Sustentabilidade",
                                "Tecnologia",
                                "Segurança",
                                "Produtividade"
                            ).forEach { categoria ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = categoria,
                                            fontSize = 13.sp
                                        )
                                    },
                                    onClick = {
                                        categoriaSelecionada = categoria
                                        menuAberto = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                erro?.let {
                    Text(
                        text = it,
                        color = InovaAzulClaro,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ActionButton(
                        text = "Cancelar",
                        backgroundColor = InovaCinza,
                        textColor = InovaAzulEscuro,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        onClick = onCancelarClick
                    )

                    Spacer(modifier = Modifier.width(11.dp))

                    ActionButton(
                        text = "Enviar",
                        backgroundColor = InovaAzulClaro,
                        textColor = InovaBranco,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        onClick = {
                            val usuarioAtual = usuario
                            if (usuarioAtual == null ||
                                titulo.isBlank() ||
                                descricao.isBlank() ||
                                categoriaSelecionada == "Selecione uma categoria"
                            ) {
                                return@ActionButton
                            }

                            viewModel.cadastrarRegistro(
                                RegistroRequest(
                                    dataCriado = Instant.now().toString(),
                                    tipo = "IDEIA",
                                    titulo = titulo.trim(),
                                    descricao = descricao.trim(),
                                    categoria = categoriaSelecionada,
                                    status = "PENDENTE",
                                    prioridade = "MEDIA",
                                    operadorId = usuarioAtual.id,
                                    orientacaoId = "",
                                    nome = "",
                                    etapa = "",
                                    investimento = 0.0,
                                    retornoFinanceiro = 0.0,
                                    reducaoCustos = 0.0,
                                    ganhoProdutividade = 0.0,
                                    prazo = "",
                                    ideiaId = "",
                                    gestorId = "",
                                    ativo = true
                                ),
                                aoConcluir = onEnviarClick
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FormCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(8.dp),
                ambientColor = InovaPreto.copy(alpha = 0.06f),
                spotColor = InovaPreto.copy(alpha = 0.06f)
            )
            .background(
                color = InovaBranco,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 14.dp, vertical = 14.dp),
        content = content
    )
}

@Composable
fun IdeaInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    height: Dp,
    borderColor: Color,
    placeholderColor: Color,
    singleLine: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 13.dp, vertical = 10.dp)
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
                            color = placeholderColor,
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
fun ActionButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IdeaRegisterPreview() {
    ChallengegrupoaguiabrancaTheme {
        IdeaRegisterScreen()
    }
}
