package com.ecommerce.Controller;

import com.ecommerce.Model.WebHookPagamento;
import com.ecommerce.Repository.WebHookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/webhook/v1")
public class WebHookController {

    @Autowired
    WebHookRepository webHookRepository;

    @PostMapping("/recibe")
    @ResponseBody
    public ResponseEntity<?> receberWebHook(@RequestParam String notification) {
        // Exibe o parâmetro 'notification' recebido
        System.out.println("Recebendo webhook: " + notification);

        WebHookPagamento webHookPagamento = new WebHookPagamento();
        webHookPagamento.setNotification(notification);

        // Salva o objeto no banco de dados
        webHookRepository.save(webHookPagamento);

        return ResponseEntity.ok().body(webHookPagamento);
    }
}
