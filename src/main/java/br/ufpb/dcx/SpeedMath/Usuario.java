package br.ufpb.dcx.SpeedMath;
import java.util.Objects;

    public class Usuario {
        private String nomeUsuario;
        private String senha;
        private int score;

    public Usuario(String nome, String senha) {
        this.nomeUsuario = nome;
        this.senha = senha;
        this.score = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void adicionarScore(int pontos) {
        this.score += pontos;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
        }

    public String getSenha() {
        return senha;
    }

    public int getScore () {
        return score;
    }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Usuario usuario = (Usuario) o;
            return Objects.equals(nomeUsuario, usuario.nomeUsuario);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(nomeUsuario);
        }

        @Override
        public String toString () {
            return "Player: "+ this.nomeUsuario + " - pts: " + this.score;
    }


    }
