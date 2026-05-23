package br.com.fiap.challenge_grupo_aguia_branca.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulClaro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaAzulEscuro
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaBranco
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaBorda
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaFundo
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaPlaceholder
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaCinzaTexto
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.InovaLilas
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel

private data class AcessoDemo(
    val papel: String,
    val email: String,
    val senha: String,
    val perfil: String,
    val icone: String
)

private val acessosDemo = listOf(
    AcessoDemo(
        papel = "Operador",
        email = "operador@teste.com",
        senha = "123456",
        perfil = "OPERADOR",
        icone = "👨‍💼"
    ),
    AcessoDemo(
        papel = "Gestor",
        email = "gestor@teste.com",
        senha = "123456",
        perfil = "GESTOR",
        icone = "📊"
    ),
    AcessoDemo(
        papel = "Liderança",
        email = "lider@teste.com",
        senha = "123456",
        perfil = "LIDERANCA",
        icone = "🎯"
    )
)

@Composable
fun LoginScreen(
    viewModel: ApiViewModel = viewModel(),
    onLoginSucesso: (perfil: String) -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mostrarAcessos by remember { mutableStateOf(false) }

    val usuarioLogado by viewModel.usuarioLogado.collectAsState()
    val erro by viewModel.erro.collectAsState()

    LaunchedEffect(usuarioLogado) {
        usuarioLogado?.let {
            onLoginSucesso(it.perfil.uppercase())
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(InovaAzulEscuro)
            .padding(horizontal = 13.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Águia Branca Colab",
                color = InovaBranco,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Plataforma de Inovação",
                color = InovaBranco.copy(alpha = 0.9f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(InovaBranco)
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Text(
                    text = "E-mail",
                    color = InovaCinzaTexto,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(9.dp))

                LoginInput(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "seu.email@aguiabranca.com",
                    keyboardType = KeyboardType.Email,
                    borderColor = InovaCinzaBorda,
                    textColor = InovaCinzaTexto,
                    placeholderColor = InovaCinzaPlaceholder
                )

                Spacer(modifier = Modifier.height(17.dp))

                Text(
                    text = "Senha",
                    color = InovaCinzaTexto,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(9.dp))

                LoginInput(
                    value = senha,
                    onValueChange = { senha = it },
                    placeholder = "••••••••",
                    isPassword = true,
                    keyboardType = KeyboardType.Password,
                    borderColor = InovaCinzaBorda,
                    textColor = InovaCinzaTexto,
                    placeholderColor = InovaCinzaPlaceholder
                )

                Spacer(modifier = Modifier.height(22.dp))

                erro?.let {
                    Text(
                        text = it,
                        color = InovaAzulClaro,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .background(InovaAzulClaro)
                        .clickable {
                            if (email.isBlank() || senha.isBlank()) {
                                return@clickable
                            }
                            viewModel.login(email.trim(), senha.trim())
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Entrar",
                        color = InovaBranco,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .border(
                            width = 1.dp,
                            color = InovaAzulEscuro,
                            shape = RoundedCornerShape(9.dp)
                        )
                        .clickable { mostrarAcessos = true },
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🔑", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Ver acessos de teste",
                            color = InovaAzulEscuro,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        if (mostrarAcessos) {
            AcessosDemoDialog(
                acessos = acessosDemo,
                onUsarAcesso = { acesso ->
                    email = acesso.email
                    senha = acesso.senha
                    mostrarAcessos = false
                },
                onDismiss = { mostrarAcessos = false }
            )
        }
    }
}

@Composable
private fun AcessosDemoDialog(
    acessos: List<AcessoDemo>,
    onUsarAcesso: (AcessoDemo) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(InovaBranco)
                .padding(horizontal = 18.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🔑",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Acessos de Teste",
                    color = InovaAzulEscuro,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "✕",
                    color = InovaCinzaTexto,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onDismiss() }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Toque em \"Usar\" para preencher o login automaticamente.",
                color = InovaCinzaTexto,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            acessos.forEachIndexed { index, acesso ->
                AcessoCard(
                    acesso = acesso,
                    onUsar = { onUsarAcesso(acesso) }
                )

                if (index < acessos.lastIndex) {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
private fun AcessoCard(
    acesso: AcessoDemo,
    onUsar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(InovaCinzaFundo)
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = acesso.icone, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = acesso.papel,
                color = InovaAzulEscuro,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(InovaLilas)
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            ) {
                Text(
                    text = acesso.perfil,
                    color = InovaAzulEscuro,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        AcessoLinhaInfo(label = "E-mail", valor = acesso.email)
        Spacer(modifier = Modifier.height(4.dp))
        AcessoLinhaInfo(label = "Senha", valor = acesso.senha)

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(InovaAzulClaro)
                .clickable { onUsar() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Usar este acesso",
                color = InovaBranco,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun AcessoLinhaInfo(label: String, valor: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label: ",
            color = InovaCinzaTexto,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = valor,
            color = InovaAzulEscuro,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun LoginInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    keyboardType: KeyboardType,
    borderColor: Color,
    textColor: Color,
    placeholderColor: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(42.dp)
            .clip(RoundedCornerShape(9.dp))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(9.dp)
            )
            .padding(horizontal = 15.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = if (isPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            ),
            textStyle = TextStyle(
                color = textColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = placeholderColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                innerTextField()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        LoginScreen()
    }
}
