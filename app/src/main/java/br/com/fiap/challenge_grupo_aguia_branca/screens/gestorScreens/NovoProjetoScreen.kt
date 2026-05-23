package br.com.fiap.challenge_grupo_aguia_branca.screens.gestorScreens

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
fun NovoProjetoScreen(
    viewModel: ApiViewModel = viewModel(),
    onVoltarClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    onCriarClick: () -> Unit = {}
) {
    var nomeProjeto by remember { mutableStateOf("") }
    var responsavel by remember { mutableStateOf("") }
    var dataTermino by remember { mutableStateOf("") }
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
                titulo = "Novo Projeto",
                mostrarVoltar = true,
                onVoltarClick = onVoltarClick
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 18.dp, bottom = 18.dp)
            ) {
                FormFieldLabel(text = "Nome do Projeto *")

                Spacer(modifier = Modifier.height(8.dp))

                NovoProjetoInput(
                    value = nomeProjeto,
                    onValueChange = { nomeProjeto = it },
                    placeholder = "Ex: Redução de Custos"
                )

                Spacer(modifier = Modifier.height(16.dp))

                FormFieldLabel(text = "Responsável *")

                Spacer(modifier = Modifier.height(8.dp))

                NovoProjetoInput(
                    value = responsavel,
                    onValueChange = { responsavel = it },
                    placeholder = "Nome do responsável"
                )

                Spacer(modifier = Modifier.height(16.dp))

                FormFieldLabel(text = "Data de Término *")

                Spacer(modifier = Modifier.height(8.dp))

                NovoProjetoInput(
                    value = dataTermino,
                    onValueChange = { dataTermino = it },
                    placeholder = "dd/mm/aaaa"
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
                    BotaoAcao(
                        label = "Cancelar",
                        background = InovaCinza,
                        textColor = InovaAzulEscuro,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        onClick = onCancelarClick
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    BotaoAcao(
                        label = "Criar",
                        background = InovaAzulClaro,
                        textColor = InovaBranco,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        onClick = {
                            val usuarioAtual = usuario
                            if (usuarioAtual == null ||
                                nomeProjeto.isBlank() ||
                                responsavel.isBlank() ||
                                dataTermino.isBlank()
                            ) {
                                return@BotaoAcao
                            }

                            viewModel.cadastrarRegistro(
                                RegistroRequest(
                                    dataCriado = Instant.now().toString(),
                                    tipo = "PROJETO",
                                    titulo = "",
                                    descricao = "Projeto: $nomeProjeto. Responsável: $responsavel.",
                                    categoria = "",
                                    status = "PLANEJAMENTO",
                                    prioridade = "",
                                    operadorId = "",
                                    orientacaoId = "",
                                    nome = nomeProjeto.trim(),
                                    etapa = "Planejamento",
                                    investimento = 0.0,
                                    retornoFinanceiro = 0.0,
                                    reducaoCustos = 0.0,
                                    ganhoProdutividade = 0.0,
                                    prazo = dataTermino.trim(),
                                    ideiaId = "",
                                    gestorId = usuarioAtual.id,
                                    ativo = true
                                ),
                                aoConcluir = onCriarClick
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun FormFieldLabel(text: String) {
    Text(
        text = text,
        color = InovaAzulEscuro,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun NovoProjetoInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
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
            .padding(horizontal = 14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = InovaPreto,
                fontSize = 13.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = InovaCinzaPlaceholder,
                        fontSize = 13.sp
                    )
                }
                innerTextField()
            }
        )
    }
}

@Composable
fun BotaoAcao(
    label: String,
    background: androidx.compose.ui.graphics.Color,
    textColor: androidx.compose.ui.graphics.Color,
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
fun NovoProjetoScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        NovoProjetoScreen()
    }
}
