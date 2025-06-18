package br.ufpb.dcx.SpeedMath;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia a gravação e leitura de dados de usuários em um arquivo.
 * Esta versão utiliza a serialização de objetos para persistir a lista de usuários,
 * tornando o código mais simples e robusto.
 *
 * @version 2.0 (Refatorado)
 */
public class GravadorDeUsuario {

    // O nome do arquivo foi definido como uma constante privada.
    // A extensão ".dat" é uma convenção para arquivos de dados serializados.
    private static final String NOME_ARQUIVO = "usuarios.dat";

    /**
     * Construtor padrão.
     */
    public GravadorDeUsuario() {
    }

    /**
     * Grava uma lista de objetos Usuario no arquivo.
     * A lista inteira é salva de uma só vez usando ObjectOutputStream.
     * <p>
     * <b>IMPORTANTE:</b> A classe 'Usuario' DEVE implementar a interface {@link java.io.Serializable}
     * para que este método funcione. Ex: {@code public class Usuario implements Serializable { ... }}
     *
     * @param usuarios A lista de usuários a ser gravada.
     * @throws IOException Se ocorrer um erro de I/O durante a gravação.
     */
    public void gravarUsuarios(List<Usuario> usuarios) throws IOException {
        try (ObjectOutputStream gravador = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            gravador.writeObject(usuarios);
        }
    }

    /**
     * Carrega a lista de usuários a partir do arquivo.
     * Lê o objeto da lista inteiro do arquivo usando ObjectInputStream.
     *
     * @return Uma {@link List<Usuario>} com os dados recuperados do arquivo.
     * Retorna uma lista vazia se o arquivo não for encontrado (primeira execução).
     * @throws IOException Se ocorrer um erro de I/O (que não seja arquivo não encontrado) ou
     * se a classe do objeto lido não for compatível.
     */
    @SuppressWarnings("unchecked") // Necessário para o cast para List<Usuario>
    public List<Usuario> carregarUsuarios() throws IOException {
        try (ObjectInputStream leitor = new ObjectInputStream(new FileInputStream(NOME_ARQUIVO))) {
            return (List<Usuario>) leitor.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            throw new IOException("Erro de compatibilidade: A definição da classe Usuario pode ter mudado.", e);
        }
    }
}
