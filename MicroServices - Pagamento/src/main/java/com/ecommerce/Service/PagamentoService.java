package com.ecommerce.Service;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.ecommerce.Config.Credentials;
import com.ecommerce.Enum.CATEGORIA;
import com.ecommerce.Model.Pagamento;
import com.ecommerce.Model.PagamentoPixRequest;
import com.ecommerce.Model.Produto;
import com.ecommerce.Util.CreateImgPix;
import com.ecommerce.Util.DataUtil;
import com.ecommerce.VO.v1.PagamentoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PagamentoService {

    @Autowired
    DataUtil dataUtil;

    @Autowired
    CreateImgPix createImgPix;


    @Autowired
    PagamentoVO pagamentoVO;

    public ResponseEntity<?> processarPagamentoCartaoBoleto(Pagamento pagamento) {

        String urlWebHook = System.getenv("URL_WEBHOOK");
        Double descontos = 0.0;

        if (pagamento == null){
            return ResponseEntity.badRequest().body("Recebeu null no body que devia ter o json pagamento.");
        }
        if (pagamento.getProdutos().isEmpty()){
            return ResponseEntity.badRequest().body("A lista de produtos está vázia.");
        }

        try {
        /* *********  Set credentials parameters ******** */
        Credentials credentials = new Credentials();
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("sandbox", credentials.isSandbox());
        /* ************************************************* */

        List<Object> items = new ArrayList<Object>();

        for(Produto produto : pagamento.getProdutos()) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("name", produto.getNome());
            item.put("amount", produto.getQuantidade());
            item.put("value", produto.getPreco() * 100);
            if(produto.getDesconto() != null && produto.getDesconto() > 0) {
                descontos += produto.getDesconto();
            }
            items.add(item);
        }
        Map<String, Object> settings = new HashMap<String, Object>();
        settings.put("payment_method", "all");
        if (descontos > 0){
            settings.put("billet_discount", descontos * 100);
            settings.put("card_discount", descontos * 100);
        }
        settings.put("expire_at",dataUtil.pegarDiasUteis(3));
        settings.put("request_delivery_address", false);

        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("notification_url", urlWebHook);

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("items", items);
        body.put("settings", settings);
        body.put("metadata", metadata);


            EfiPay efi = new EfiPay(options);
            Map<String, Object> response = efi.call("createOneStepLink", new HashMap<String,String>(), body);
            System.out.println(response);
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            System.out.println(data.get("payment_url"));
            pagamentoVO.setPayment_url((String) data.get("payment_url"));
            return ResponseEntity.ok().body(response);
        }catch (EfiPayException e){
            System.out.println(e.getCode());
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity<?> processarPagamentoPix(PagamentoPixRequest pixRequest) {

        String urlWebHook = System.getenv("URL_WEBHOOK");
        Double descontos = 0.0;

        if (pixRequest == null){
            return ResponseEntity.badRequest().body("Recebeu null no body que devia ter o json pagamento.");
        }
        if (pixRequest.getPagamento().getProdutos().isEmpty()){
            return ResponseEntity.badRequest().body("A lista de produtos está vázia.");
        }
        try{

        Credentials credentials = new Credentials();

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());


        Map<String, Object> body = new HashMap<String, Object>();

        Map<String, Object> calendario = new HashMap<String, Object>();
        calendario.put("expiracao", 3600);

        Map<String, Object> devedor = new HashMap<String, Object>();
        devedor.put("cpf", pixRequest.getUsuario().getCpf());
        devedor.put("nome", pixRequest.getUsuario().getNome());

        Map<String, Object> valor = new HashMap<String, Object>();
        valor.put("original", String.valueOf(pixRequest.getPagamento().calcularTotal()));

            for(Produto produto : pixRequest.getPagamento().getProdutos()) {
                if(produto.getDesconto() != null && produto.getDesconto() > 0) {
                    descontos += produto.getDesconto();
                }
            }

        List<Map<String, Object>> infoAdicionais = new ArrayList<>();
        Map<String, Object> info1 = new HashMap<String, Object>();
        info1.put("nome", "Descontos");
        info1.put("valor", "Desconto no valor de R$ " + descontos + " foi aplicado.");
        infoAdicionais.add(info1);

        body.put("calendario", calendario);
        body.put("devedor", devedor);
        body.put("valor", valor);
        body.put("chave", pixRequest.getPagamento().getChavePixLoja());
        body.put("solicitacaoPagador", "Compra de produtos.");
        body.put("infoAdicionais", infoAdicionais);


            EfiPay efi= new EfiPay(options);
            Map<String, Object> response = efi.call("pixCreateImmediateCharge", new HashMap<String,String>(), body);
            System.out.println(response);
           // Map<String, Object> responseBody = (Map<String, Object>) response.get("body");
            Map<String, Object> loc = (Map<String, Object>) response.get("loc");
            Integer id = (Integer) loc.get("id");
            ResponseEntity<?> qrCode = createImgPix.imgPix(id);
            return ResponseEntity.ok().body(qrCode);
        }catch (EfiPayException e){
            System.out.println(e.getError());
            System.out.println(e.getErrorDescription());
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
