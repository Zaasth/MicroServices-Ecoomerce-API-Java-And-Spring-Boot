package com.ecommerce.Controller;

import com.ecommerce.Model.Pagamento;
import com.ecommerce.Model.PagamentoPixRequest;
import com.ecommerce.Model.Usuario;
import com.ecommerce.Service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pagamento/v1", produces = "application/json")
@Tag(name = "MicroServices-Pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping(value = "/bolix", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Este endpoint realiza o processamento de pagamentos para " +
            "cartões de crédito e boletos bancários utilizando a função createOneStepLink " +
            "da SDK do efiPay. Ele facilita a execução de transações financeiras de maneira " +
            "rápida e segura, integrando-se diretamente ao fluxo de pagamento do nosso sistema.", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação do link de pagamento para cartão e boleto realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Falha na criação do link de pagamento para cartão e boleto.")
    })
    public ResponseEntity<?> processarPagamentoCartaoBoleto(@RequestBody Pagamento pagamento) {
        try {
            if(pagamento == null){
                return ResponseEntity.badRequest().body("Json recebido para pagamento é null");
            }
            return ResponseEntity.ok().body(pagamentoService.processarPagamentoCartaoBoleto(pagamento));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/pix")
    @Operation(method = "POST", summary = "Este endpoint realiza a criação de um pagamento via PIX, gerando tanto o QR Code quanto um link para uma página onde a imagem do QR Code pode ser escaneada. Isso facilita o processo de pagamento rápido e seguro para os usuários.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Criação do link de pagamento para pix realizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Falha na criação do link de pagamento para pix.")
    })
    public ResponseEntity<?> processarPagamentoPix(@RequestBody PagamentoPixRequest pixRequest) {
        try {
            if(pixRequest == null){
                return ResponseEntity.badRequest().body("Json recebido para pixRequest é null");
            }
            return ResponseEntity.ok().body(pagamentoService.processarPagamentoPix(pixRequest));
        } catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
