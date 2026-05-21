package br.com.fiap.challenge_grupo_aguia_branca.screens.operatorScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme

@Composable
fun IdeaRegisterScreen(
    onVoltarClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    onEnviarClick: () -> Unit = {}
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Selecione uma categoria") }
    var menuAberto by remember { mutableStateOf(false) }

    val backgroundColor = Color(0xFFF5F6F8)
    val topBarColor = Color(0xFF29476F)
    val labelColor = Color(0xFF4B5563)
    val inputBorderColor = Color(0xFFD8DEE8)
    val placeholderColor = Color(0xFFA8B0BE)
    val pinkColor = Color(0xFFE936A4)
    val cancelColor = Color(0xFFD4D9E0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(57.dp)
                    .background(topBarColor)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "←",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onVoltarClick() }
                )

                Spacer(modifier = Modifier.width(18.dp))

                Text(
                    text = "Registrar Ideia",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 9.dp)
                    .padding(top = 14.dp)
            ) {
                FormCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Título da Ideia *",
                        color = labelColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    IdeaInput(
                        value = titulo,
                        onValueChange = { titulo = it },
                        placeholder = "Descreva sua ideia em poucas palavras",
                        height = 40.dp,
                        borderColor = inputBorderColor,
                        placeholderColor = placeholderColor
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                FormCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(172.dp)
                ) {
                    Text(
                        text = "Descrição Detalhada *",
                        color = labelColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    IdeaInput(
                        value = descricao,
                        onValueChange = { descricao = it },
                        placeholder = "Explique o problema ou\noportunidade...",
                        height = 116.dp,
                        borderColor = inputBorderColor,
                        placeholderColor = placeholderColor,
                        singleLine = false
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                FormCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                ) {
                    Text(
                        text = "Categoria *",
                        color = labelColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(9.dp))

                    Box {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .border(
                                    width = 1.dp,
                                    color = inputBorderColor,
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .clickable { menuAberto = true }
                                .padding(horizontal = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = categoriaSelecionada,
                                color = Color(0xFF111827),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = "⌄",
                                color = Color.Black,
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

                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ActionButton(
                        text = "Cancelar",
                        backgroundColor = cancelColor,
                        textColor = Color(0xFF1F2937),
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp),
                        onClick = onCancelarClick
                    )

                    Spacer(modifier = Modifier.width(11.dp))

                    ActionButton(
                        text = "Enviar",
                        backgroundColor = pinkColor,
                        textColor = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp),
                        onClick = onEnviarClick
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
                ambientColor = Color.Black.copy(alpha = 0.06f),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 13.dp, vertical = 14.dp),
        content = content
    )
}

@Composable
fun IdeaInput(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    height: androidx.compose.ui.unit.Dp,
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
                color = Color(0xFF111827),
                fontSize = 13.sp,
                lineHeight = 18.sp
            ),
            modifier = Modifier.fillMaxSize(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = if (singleLine) {
                        Alignment.CenterStart
                    } else {
                        Alignment.TopStart
                    }
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