package br.ufpb.dcx.SpeedMath;
import javax.swing.*;
import java.io.IOException;
import java.util.List;

import static br.ufpb.dcx.SpeedMath.UsuarioList.usuarios;

public class ProgramaSpeedMath {
    public static void main (String[] args) {
        UsuarioList sistema = new UsuarioList();
        Rank sistemaRank = new Rank();



        boolean sair = false;
        while (!sair) {
            int opcao = Integer.parseInt(JOptionPane.showInputDialog
                    ("Digite a opção desejada:\n" +
                            "1) Cadastrar usuário \n" +
                            "2) Remover usuário \n" +
                            "3) Pesquisar usuário \n" +
                            "4) Consultar ranking \n" +
                            "5) Começar questões \n" +
                            "6) Salvar \n" +
                            "7) Recuperar dados \n" +
                            "8) Sair"));

            switch (opcao) {
                case 1:
                    String nome = JOptionPane.showInputDialog("Nome de usuário: ");
                    if (nome == null) {
                        break;
                    }

                    String senha = JOptionPane.showInputDialog("Senha(8 dígitos): ");
                    if (senha == null) {
                        break;
                    }

                    if (senha.length() != 8) {
                        JOptionPane.showMessageDialog(null, "A senha deve ter exatamente 8 dígitos!");
                        break;
                    }

                    try {
                        boolean sucesso = sistema.cadastrarUsuario(nome,senha);
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso");
                    } catch (UsuarioJaExisteException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;
                case 2:
                    String usuarioRemover = JOptionPane.showInputDialog("Digite o nome do usuário a ser removido: ");
                    if (usuarioRemover == null) {
                        break;
                    }

                    try {
                        boolean sucesso = sistema.removerUsuario(usuarioRemover) && sistemaRank.removerDoRank(usuarioRemover);
                        if (sucesso) {
                            JOptionPane.showMessageDialog(null, "Usuário removido com sucesso");
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;
                case 3:
                    String nomePesquisar = JOptionPane.showInputDialog("Nome de usuário: ");
                    if (nomePesquisar == null) {
                        break;
                    }

                    try {
                        JOptionPane.showMessageDialog(null, sistema.pesquisarUsuario(nomePesquisar));
                    } catch (UsuarioNaoEncontradoException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;
                case 4:
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
                    break;

                case 5:
                    String nomeLogin = JOptionPane.showInputDialog("Login - Digite seu nome: ");
                    if (nomeLogin == null) {
                        break;
                    }

                    String senhaLogin = JOptionPane.showInputDialog("Login - Digite sua senha: ");
                    if ( senhaLogin == null) {
                        break;
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
                    break;
                case 6:
                    try{
                        sistema.salvarDados();
                        JOptionPane.showMessageDialog(null, "Dados salvos com sucesso");
                    }catch (IOException e){
                        JOptionPane.showMessageDialog(null, "Problema ao salvar dados. Tente mais tarde ");
                        e.printStackTrace();
                    }
                    break;
                case 7:
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
                    break;
                case 8:
                    JOptionPane.showMessageDialog(null, "Até a próxima! Programa encerrado.");
                    sair = true;



            }
        }
    }
}
