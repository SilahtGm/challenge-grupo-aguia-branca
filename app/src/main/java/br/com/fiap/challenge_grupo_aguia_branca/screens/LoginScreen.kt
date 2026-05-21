package br.com.fiap.challenge_grupo_aguia_branca.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.challenge_grupo_aguia_branca.ui.theme.ChallengegrupoaguiabrancaTheme

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var perfilSelecionado by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFF29476F)
    val labelColor = Color(0xFF4B5563)
    val borderColor = Color(0xFFD8DEE8)
    val inputTextColor = Color(0xFF6B7280)
    val placeholderColor = Color(0xFF9CA3AF)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(horizontal = 13.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Águia Branca Colab",
                color = Color.White,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Plataforma de Inovação",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(29.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 60.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 20.dp, vertical = 21.dp)
            ) {
                Text(
                    text = "E-mail ou Matricula",
                    color = labelColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(9.dp))

                LoginInput(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "seu.email@aguiabranca.com",
                    keyboardType = KeyboardType.Email,
                    borderColor = borderColor,
                    textColor = inputTextColor,
                    placeholderColor = placeholderColor
                )

                Spacer(modifier = Modifier.height(17.dp))

                Text(
                    text = "Senha",
                    color = labelColor,
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
                    borderColor = borderColor,
                    textColor = inputTextColor,
                    placeholderColor = placeholderColor
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Selecione seu Perfil",
                    color = labelColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                PerfilCard(
                    icon = "👨‍💼",
                    title = "Operador",
                    subtitle = "Registrar ideias",
                    selected = perfilSelecionado == "Operador",
                    onClick = { perfilSelecionado = "Operador" }
                )

                Spacer(modifier = Modifier.height(20.dp))

                PerfilCard(
                    icon = "📊",
                    title = "Gestor",
                    subtitle = "Gerenciar ideias",
                    selected = perfilSelecionado == "Gestor",
                    onClick = { perfilSelecionado = "Gestor" }
                )

                Spacer(modifier = Modifier.height(20.dp))

                PerfilCard(
                    icon = "🎯",
                    title = "Líder",
                    subtitle = "Dashboard estratégico",
                    selected = perfilSelecionado == "Líder",
                    onClick = { perfilSelecionado = "Líder" }
                )

                Spacer(modifier = Modifier.height(20.dp))

                PerfilCard(
                    icon = "⚙️",
                    title = "Admin",
                    subtitle = "Gerenciar sistema",
                    selected = perfilSelecionado == "Admin",
                    onClick = { perfilSelecionado = "Admin" }
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFFE335A5),
                                    Color(0xFFEB3C99)
                                )
                            )
                        )
                        .clickable {
                            // Aqui você pode chamar sua lógica de login
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Entrar",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
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

@Composable
fun PerfilCard(
    icon: String,
    title: String,
    subtitle: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (selected) Color(0xFFE335A5) else Color(0xFFE2E8F0)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(Color(0xFFF9FAFB))
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(9.dp)
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = icon,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.width(17.dp))

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = Color(0xFF374151),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = subtitle,
                color = Color(0xFF6B7280),
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ChallengegrupoaguiabrancaTheme {
        LoginScreen()
    }
}