package br.com.fiap.challenge_grupo_aguia_branca.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import br.com.fiap.challenge_grupo_aguia_branca.data.model.RegistroRequest
import br.com.fiap.challenge_grupo_aguia_branca.viewmodel.ApiViewModel
import java.time.Instant

@Composable
fun TesteApiScreen(
    viewModel: ApiViewModel = viewModel()
) {
    val usuarioLogado by viewModel.usuarioLogado.collectAsState()
    val registros by viewModel.registros.collectAsState()
    val dashboard by viewModel.dashboard.collectAsState()
    val erro by viewModel.erro.collectAsState()

    var email by remember { mutableStateOf("operador@teste.com") }
    var senha by remember { mutableStateOf("123456") }
    var mensagemLocal by remember { mutableStateOf<String?>(null) }

    val orientacaoTeste = registros.firstOrNull { it.tipo == "ORIENTACAO" }
    val ideiaTeste = registros.firstOrNull { it.tipo == "IDEIA" }
    val ideiaAprovada = registros.firstOrNull { it.tipo == "IDEIA" && it.status == "APROVADA" }
    val projetoTeste = registros.firstOrNull { it.tipo == "PROJETO" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Painel de Testes da API",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "MockAPI + Retrofit + ViewModel",
            style = MaterialTheme.typography.bodyMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleMedium
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
                        mensagemLocal = null
                        viewModel.login(email, senha)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Entrar")
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            email = "operador@teste.com"
                            senha = "123456"
                            mensagemLocal = "Usuário operador selecionado."
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Operador")
                    }

                    OutlinedButton(
                        onClick = {
                            email = "gestor@teste.com"
                            senha = "123456"
                            mensagemLocal = "Usuário gestor selecionado."
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Gestor")
                    }

                    OutlinedButton(
                        onClick = {
                            email = "lider@teste.com"
                            senha = "123456"
                            mensagemLocal = "Usuário liderança selecionado."
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Líder")
                    }
                }

                usuarioLogado?.let {
                    Divider()

                    Text("Usuário logado:")
                    Text("ID: ${it.id}")
                    Text("Nome: ${it.nome}")
                    Text("Email: ${it.email}")
                    Text("Perfil: ${it.perfil}")
                }

                Button(
                    onClick = {
                        viewModel.logout()
                        mensagemLocal = "Logout realizado."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Listagens",
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = {
                        mensagemLocal = "Listando orientações."
                        viewModel.listarOrientacoes()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Listar Orientações")
                }

                Button(
                    onClick = {
                        mensagemLocal = "Listando todas as ideias."
                        viewModel.listarIdeias()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Listar Ideias")
                }

                Button(
                    onClick = {
                        mensagemLocal = "Listando minhas ideias."
                        viewModel.listarMinhasIdeias()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Listar Minhas Ideias")
                }

                Button(
                    onClick = {
                        mensagemLocal = "Listando projetos."
                        viewModel.listarProjetos()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Listar Projetos")
                }

                Button(
                    onClick = {
                        val orientacaoId = orientacaoTeste?.id

                        if (orientacaoId.isNullOrBlank()) {
                            mensagemLocal = "Liste as orientações primeiro para testar o filtro por orientação."
                            return@Button
                        }

                        mensagemLocal = "Listando ideias da orientação $orientacaoId."
                        viewModel.listarIdeiasPorOrientacao(orientacaoId)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Listar Ideias por Orientação")
                }

                Button(
                    onClick = {
                        val orientacaoId = orientacaoTeste?.id

                        if (orientacaoId.isNullOrBlank()) {
                            mensagemLocal = "Liste as orientações primeiro para testar o filtro por orientação."
                            return@Button
                        }

                        mensagemLocal = "Listando projetos da orientação $orientacaoId."
                        viewModel.listarProjetosPorOrientacao(orientacaoId)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Listar Projetos por Orientação")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Cadastros",
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = {
                        val usuario = usuarioLogado

                        if (usuario == null) {
                            mensagemLocal = "Faça login como operador para cadastrar ideia."
                            return@Button
                        }

                        val orientacaoId = orientacaoTeste?.id ?: "1"

                        viewModel.cadastrarRegistro(
                            RegistroRequest(
                                dataCriado = Instant.now().toString(),
                                tipo = "IDEIA",
                                titulo = "Ideia criada pelo app",
                                descricao = "Teste de cadastro de ideia usando a tela de testes.",
                                categoria = "Operacional",
                                status = "PENDENTE",
                                prioridade = "MEDIA",
                                operadorId = usuario.id,
                                orientacaoId = orientacaoId,
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
                            )
                        )

                        mensagemLocal = "Cadastro de ideia enviado."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar Ideia de Teste")
                }

                Button(
                    onClick = {
                        val usuario = usuarioLogado

                        if (usuario == null) {
                            mensagemLocal = "Faça login como gestor para cadastrar projeto."
                            return@Button
                        }

                        val ideia = ideiaAprovada ?: ideiaTeste

                        if (ideia?.id.isNullOrBlank()) {
                            mensagemLocal = "Liste ideias primeiro. É necessário ter uma ideia para criar projeto."
                            return@Button
                        }

                        viewModel.cadastrarRegistro(
                            RegistroRequest(
                                dataCriado = Instant.now().toString(),
                                tipo = "PROJETO",
                                titulo = "",
                                descricao = "Projeto criado pela tela de testes a partir de uma ideia.",
                                categoria = ideia?.categoria ?: "Operacional",
                                status = "PLANEJAMENTO",
                                prioridade = "",
                                operadorId = "",
                                orientacaoId = ideia?.orientacaoId ?: "1",
                                nome = "Projeto criado pelo app",
                                etapa = "Planejamento",
                                investimento = 5000.0,
                                retornoFinanceiro = 15000.0,
                                reducaoCustos = 8000.0,
                                ganhoProdutividade = 12.0,
                                prazo = "2026-12-31",
                                ideiaId = ideia?.id ?: "",
                                gestorId = usuario.id,
                                ativo = true
                            )
                        )

                        ideia?.id?.let {
                            viewModel.marcarIdeiaComoProjeto(it)
                        }

                        mensagemLocal = "Cadastro de projeto enviado."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cadastrar Projeto de Teste")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Ações de Ideias",
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = {
                        val id = ideiaTeste?.id

                        if (id.isNullOrBlank()) {
                            mensagemLocal = "Liste ideias primeiro para testar essa ação."
                            return@Button
                        }

                        viewModel.colocarIdeiaEmAnalise(id)
                        mensagemLocal = "Ideia $id enviada para análise."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Colocar Primeira Ideia em Análise")
                }

                Button(
                    onClick = {
                        val id = ideiaTeste?.id

                        if (id.isNullOrBlank()) {
                            mensagemLocal = "Liste ideias primeiro para testar essa ação."
                            return@Button
                        }

                        viewModel.aprovarIdeia(id, "ALTA")
                        mensagemLocal = "Ideia $id aprovada."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Aprovar Primeira Ideia")
                }

                Button(
                    onClick = {
                        val id = ideiaTeste?.id

                        if (id.isNullOrBlank()) {
                            mensagemLocal = "Liste ideias primeiro para testar essa ação."
                            return@Button
                        }

                        viewModel.reprovarIdeia(id)
                        mensagemLocal = "Ideia $id reprovada."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reprovar Primeira Ideia")
                }

                Button(
                    onClick = {
                        val id = ideiaTeste?.id

                        if (id.isNullOrBlank()) {
                            mensagemLocal = "Liste ideias primeiro para testar essa ação."
                            return@Button
                        }

                        viewModel.atualizarPrioridadeIdeia(id, "BAIXA")
                        mensagemLocal = "Prioridade da ideia $id alterada."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Mudar Prioridade da Primeira Ideia")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Ações de Projetos",
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = {
                        val id = projetoTeste?.id

                        if (id.isNullOrBlank()) {
                            mensagemLocal = "Liste projetos primeiro para testar essa ação."
                            return@Button
                        }

                        viewModel.atualizarStatusProjeto(
                            id = id,
                            status = "EM_ANDAMENTO",
                            etapa = "Execução"
                        )

                        mensagemLocal = "Status do projeto $id atualizado."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Atualizar Status do Primeiro Projeto")
                }

                Button(
                    onClick = {
                        val id = projetoTeste?.id

                        if (id.isNullOrBlank()) {
                            mensagemLocal = "Liste projetos primeiro para testar essa ação."
                            return@Button
                        }

                        viewModel.atualizarResultadosProjeto(
                            id = id,
                            retornoFinanceiro = 25000.0,
                            reducaoCustos = 12000.0,
                            ganhoProdutividade = 18.0
                        )

                        mensagemLocal = "Resultados do projeto $id atualizados."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Atualizar Resultados do Primeiro Projeto")
                }

                Button(
                    onClick = {
                        val id = projetoTeste?.id

                        if (id.isNullOrBlank()) {
                            mensagemLocal = "Liste projetos primeiro para testar essa ação."
                            return@Button
                        }

                        viewModel.atualizarMetaProjeto(
                            id = id,
                            investimento = 8000.0,
                            retornoFinanceiro = 30000.0,
                            reducaoCustos = 15000.0,
                            ganhoProdutividade = 20.0,
                            prazo = "2026-12-31"
                        )

                        mensagemLocal = "Meta do projeto $id atualizada."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Atualizar Meta do Primeiro Projeto")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Dashboard",
                    style = MaterialTheme.typography.titleMedium
                )

                Button(
                    onClick = {
                        viewModel.carregarDashboard()
                        mensagemLocal = "Dashboard carregado."
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Carregar Dashboard")
                }

                dashboard?.let {
                    Divider()

                    Text("Total de projetos: ${it.totalProjetos}")
                    Text("Projetos concluídos: ${it.projetosConcluidos}")
                    Text("Investimento total: R$ ${it.investimentoTotal}")
                    Text("Retorno financeiro total: R$ ${it.retornoFinanceiroTotal}")
                    Text("Redução de custos total: R$ ${it.reducaoCustosTotal}")
                    Text("Ganho médio de produtividade: ${it.ganhoProdutividadeMedio}%")
                    Text("ROI geral: ${it.roiGeral}%")
                }
            }
        }

        mensagemLocal?.let {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = it,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        erro?.let {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error
                    )

                    Button(
                        onClick = {
                            viewModel.limparErro()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Limpar Erro")
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Resultado da Última Listagem",
                    style = MaterialTheme.typography.titleMedium
                )

                Text("Registros encontrados: ${registros.size}")

                Spacer(modifier = Modifier.height(4.dp))

                registros.take(10).forEach {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("ID: ${it.id ?: "-"}")
                            Text("Tipo: ${it.tipo ?: "-"}")
                            Text("Título: ${it.titulo ?: "-"}")
                            Text("Nome: ${it.nome ?: "-"}")
                            Text("Status: ${it.status ?: "-"}")
                            Text("Prioridade: ${it.prioridade ?: "-"}")
                            Text("Categoria: ${it.categoria ?: "-"}")
                            Text("Orientação ID: ${it.orientacaoId ?: "-"}")
                            Text("Ideia ID: ${it.ideiaId ?: "-"}")
                            Text("Operador ID: ${it.operadorId ?: "-"}")
                            Text("Gestor ID: ${it.gestorId ?: "-"}")
                            Text("Ativo: ${it.ativo ?: false}")
                        }
                    }
                }
            }
        }
    }
}