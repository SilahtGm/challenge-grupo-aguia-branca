package br.com.fiap.challenge_grupo_aguia_branca.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challenge_grupo_aguia_branca.ui.viewmodel.ApiViewModel

@Composable
fun TesteApiScreen(
    viewModel: ApiViewModel = viewModel()
) {
    val usuarioLogado by viewModel.usuarioLogado.collectAsState()
    val registros by viewModel.registros.collectAsState()
    val erro by viewModel.erro.collectAsState()

    var email by remember { mutableStateOf("operador@teste.com") }
    var senha by remember { mutableStateOf("123456") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Teste de API",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.login(email, senha)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Testar Login")
        }

        Button(
            onClick = {
                viewModel.listarRegistrosPorTipo("IDEIA")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Listar Ideias")
        }

        Button(
            onClick = {
                viewModel.listarRegistrosPorTipo("PROJETO")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Listar Projetos")
        }

        erro?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        usuarioLogado?.let {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("Usuário logado:")
                    Text("Nome: ${it.nome}")
                    Text("Perfil: ${it.perfil}")
                    Text("Email: ${it.email}")
                }
            }
        }

        Text("Registros encontrados: ${registros.size}")

        registros.take(5).forEach {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text("ID: ${it.id}")
                    Text("Tipo: ${it.tipo}")
                    Text("Título/Nome: ${it.titulo ?: it.nome}")
                    Text("Status: ${it.status}")
                    Text("Categoria: ${it.categoria}")
                }
            }
        }
    }
}