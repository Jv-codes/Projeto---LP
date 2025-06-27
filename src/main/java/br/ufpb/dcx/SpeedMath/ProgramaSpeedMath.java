package br.ufpb.dcx.SpeedMath;
import javax.swing.*;
import java.io.IOException;
import java.util.List;

import static br.ufpb.dcx.SpeedMath.UsuarioList.usuarios;

public class ProgramaSpeedMath {
    public static void main (String[] args) {
        // 1. Inicialização dos sistemas
        UsuarioList sistema = new UsuarioList();
        Rank sistemaRank = new Rank();

        // 2. Carregamento automático dos dados
        carregarDados(sistema, sistemaRank);

        try {
            sistema.recuperarUsuarios();
            for (Usuario usuario : usuarios) {
                if (!sistemaRank.getUsuarios().contains(usuario)) {
                    sistemaRank.adicionarAoRank(usuario);
                }
            }
            JOptionPane.showMessageDialog(null, "Dados carregados e ranking atualizado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar dados.");
            e.printStackTrace();
        }

        String menu = """
                Digite a opção desejada:
                1) Cadastrar usuário
                2) Remover usuário
                3) Pesquisar usuário
                4) Consultar ranking
                5) Começar questões
                6) Sair
                """;



        boolean sair = false;
        while (!sair) {
            String opcao = JOptionPane.showInputDialog(menu);

            if (opcao == null) {
                opcao = "6";
            }

            switch (opcao) {
                case "1" -> executarCadastro(sistema);
                case "2" -> executarRemocao(sistema, sistemaRank);
                case "3" -> executarPesquisa(sistema);
                case "4" -> consultarRanking(sistema.ListaUsuario(), sistemaRank);
                case "5" -> iniciarQuestoes(sistema, sistemaRank);
                case "6" -> {
                    sair = true;
                    salvarDados(sistema); // Salvamento automático ao sair
                }
                default -> JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente.");
            }
        }
    }

    // --- MÉTODOS AUXILIARES PARA CADA OPÇÃO DO MENU ---

    private static void carregarDados(UsuarioList sistema, Rank sistemaRank) {
        try {
            sistema.recuperarUsuarios();
            for (Usuario usuario : sistema.ListaUsuario()) {
                sistemaRank.adicionarAoRank(usuario);
            }
            JOptionPane.showMessageDialog(null, "Dados carregados com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Bem-vindo! Nenhum dado anterior encontrado.");
            e.printStackTrace();
        }
    }

    private static void salvarDados(UsuarioList sistema) {
        try {
            sistema.salvarDados();
            JOptionPane.showMessageDialog(null, "Dados salvos com sucesso! Até a próxima!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERRO: Problema ao salvar dados. As alterações podem ter sido perdidas.");
            e.printStackTrace();
        }
    }

    private static void executarCadastro(UsuarioList sistema) {
        String nome = JOptionPane.showInputDialog("Nome de usuário: ");
        if (nome == null || nome.isBlank()) return; // Retorna se o usuário cancelar ou deixar em branco

        String senha = JOptionPane.showInputDialog("Senha (8 dígitos): ");
        if (senha == null || senha.isBlank()) return;

        if (senha.length() != 8) {
            JOptionPane.showMessageDialog(null, "A senha deve ter exatamente 8 dígitos!");
            return;
        }

        try {
            sistema.cadastrarUsuario(nome, senha);
            JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
        } catch (UsuarioJaExisteException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private static void executarRemocao(UsuarioList sistema, Rank sistemaRank) {
        String nomeRemover = JOptionPane.showInputDialog("Digite o nome do usuário a ser removido: ");
        if (nomeRemover == null || nomeRemover.isBlank()) return;

        try {
            // Chama os dois métodos separadamente para evitar o problema do '&&'
            boolean removidoDoSistema = sistema.removerUsuario(nomeRemover);
            boolean removidoDoRank = sistemaRank.removerDoRank(nomeRemover);

            if (removidoDoSistema) {
                JOptionPane.showMessageDialog(null, "Usuário removido com sucesso!");
            }
            // A exceção UsuarioNaoEncontradoException já trata o caso de falha.
        } catch (UsuarioNaoEncontradoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private static void executarPesquisa(UsuarioList sistema) {
        String nomePesquisar = JOptionPane.showInputDialog("Nome de usuário: ");
        if (nomePesquisar == null || nomePesquisar.isBlank()) return;

        try {
            List<Usuario> usuariosEncontrados = sistema.pesquisarUsuario(nomePesquisar);

            // Formata a saída para ser legível para o usuário
            StringBuilder resultado = new StringBuilder("Usuários encontrados:\n");
            for (Usuario u : usuariosEncontrados) {
                resultado.append(" - Nome: ").append(u.getNomeUsuario())
                        .append(", Score: ").append(u.getScore()).append("\n");
            }
            JOptionPane.showMessageDialog(null, resultado.toString());

        } catch (UsuarioNaoEncontradoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private static void consultarRanking(List<Usuario> usuarios, Rank sistemaRank) {
        // Lógica do antigo case 4
        boolean temPontuacao = false;
        for (Usuario u : usuarios) {
            if (u.getScore() > 0) {
                temPontuacao = true;
                break;
            }
        }

        if (temPontuacao) {
            JOptionPane.showMessageDialog(null, "===== Ranking =====\n" + sistemaRank.exibirRank());
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum jogador com pontuação no ranking ainda.");
        }
    }

    private static void iniciarQuestoes(UsuarioList sistema, Rank sistemaRank) {
        String nomeLogin = JOptionPane.showInputDialog("Login - Digite seu nome: ");
        if (nomeLogin == null) {
            return;
        }

        String senhaLogin = JOptionPane.showInputDialog("Login - Digite sua senha: ");
        if ( senhaLogin == null) {
            return;
        }

        try {
            boolean sucesso = sistema.autenticarUsuario(nomeLogin,senhaLogin);
            List<Usuario> usuarioLogado = sistema.pesquisarUsuario(nomeLogin);
            JOptionPane.showMessageDialog(null, "Logado com sucesso");
            Usuario usuarioAtual = usuarioLogado.get(0);
            int scoreOriginal = usuarioAtual.getScore();
            int scoreTemporario = 0;
            while (true) {
                Questao sistemaQuestao = new Questao();
                String repostaUsuarioStr = JOptionPane.showInputDialog(null, sistemaQuestao.toString() + "Digite o número da sua resposta: ");
                if (repostaUsuarioStr == null) {
                    if (scoreTemporario > scoreOriginal) {
                        usuarioAtual.setScore(scoreTemporario);
                        sistemaRank.atualizarPontuacao(sistemaRank.getUsuarios(), usuarioLogado);
                    }
                    usuarioAtual.setScore(scoreOriginal);
                    break;
                }

                int repostaUsuario = Integer.parseInt(repostaUsuarioStr);
                if (repostaUsuario == sistemaQuestao.getAlternativaCerta()) {
                    JOptionPane.showMessageDialog(null, sistemaQuestao.verificarQuestao(repostaUsuario, sistemaQuestao.getAlternativaCerta(), usuarioLogado.get(0)));
                } else {
                    sistemaRank.atualizarPontuacao(sistemaRank.getUsuarios(), usuarioLogado);
                    JOptionPane.showMessageDialog(null, "Sua resposta: " + repostaUsuarioStr + "\n" + "Resposta correta: " + sistemaQuestao.getAlternativaCerta());
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
