package br.com.fiap.challenge_grupo_aguia_branca.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = InovaAzulClaro,
    onPrimary = InovaBranco,
    secondary = InovaLilas,
    onSecondary = InovaPreto,
    tertiary = InovaVerde,
    onTertiary = InovaBranco,
    background = InovaAzulEscuro,
    onBackground = InovaBranco,
    surface = InovaAzulEscuroSuave,
    onSurface = InovaBranco
)

private val LightColorScheme = lightColorScheme(
    primary = InovaAzulEscuro,
    onPrimary = InovaBranco,
    secondary = InovaAzulClaro,
    onSecondary = InovaBranco,
    tertiary = InovaVerde,
    onTertiary = InovaBranco,
    background = InovaBranco,
    onBackground = InovaPreto,
    surface = InovaBranco,
    onSurface = InovaPreto
)

@Composable
fun ChallengegrupoaguiabrancaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
