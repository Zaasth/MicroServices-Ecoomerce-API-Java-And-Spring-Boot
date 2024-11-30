package com.ecommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    private List<Produto> produtos;

    @Column(name = "chave_pix_loja")
    private String chavePixLoja;

    public double calcularTotal() {
        double total = 0.0;

        for (Produto produto : produtos) {
            double subtotal = produto.getPreco().doubleValue() * produto.getQuantidade();
            total += subtotal;
        }

        return Math.round(total * 100.0) / 100.0;
    }
}
