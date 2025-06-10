package br.ufpb.dcx.SpeedMath;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GravadorDeUsuario {

    private String usuarioos;

    public GravadorDeUsuario (String nomeUsuario){
        this.usuarioos = nomeUsuario;
    }

    public GravadorDeUsuario(){
        this("usuarios.txt");

    }
    public void gravarUsuarios(List<Usuario> usuarios) throws IOException {
        BufferedWriter escritor = null;
        try {
            escritor = new BufferedWriter(new FileWriter(this.usuarioos));
            for (Usuario u: usuarios){
                escritor.write(u.getNomeUsuario()+"___"+ u.getScore()+ "___" + u.getSenha()+ "\n");
            }
        }finally {
            if (escritor != null) {
                escritor.close();
            }
        }

    }

    public List<Usuario> carregarUsuarios() throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        BufferedReader leitor = null;
        try {
            leitor = new BufferedReader(new FileReader(this.usuarioos));
            String linha;

            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split("___");
                if (partes.length == 3) {
                    String nome = partes[0];
                    int score = Integer.parseInt(partes[1]);
                    String senha = partes[2];
                    Usuario u = new Usuario(nome, senha);
                    u.setScore(score);
                    usuarios.add(u);
                }
            }
        } finally {
            if (leitor != null) {
                leitor.close();
            }
        }

        return usuarios;
    }
}
