package senai.mercado.entity;

import jakarta.persistence.*;

@Entity
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private int quantidade;

    public Estoque() {}

    public Estoque(String tipo, int quantidade) {
        this.tipo = tipo;
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getQuantidde() {
        return quantidade;
    }

    public void setQuantidde(int quantidde) {
        this.quantidade = quantidde;
    }

    public Long getId() {return id;}
}
