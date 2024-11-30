package com.ecommerce.Model;

import com.ecommerce.Enum.CATEGORIA;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String nome;

    @Column
    private String descricao;

    @Lob
    @Column(name = "imagem", columnDefinition = "BYTEA")
    private byte[] imagem;

    @Column
    private Integer quantidade;

    @Column
    private Integer tamanho;

    @Column
    private Double preco;

    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque;

    @Column
    private String cor;

    @Column
    private CATEGORIA categoria;

    @Column
    private Double desconto;
}
