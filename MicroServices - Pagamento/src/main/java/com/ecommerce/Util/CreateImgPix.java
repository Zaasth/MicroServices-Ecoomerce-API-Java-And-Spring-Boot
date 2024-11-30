package com.ecommerce.Util;

import br.com.efi.efisdk.EfiPay;
import br.com.efi.efisdk.exceptions.EfiPayException;
import com.ecommerce.Config.Credentials;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CreateImgPix {

    public ResponseEntity<?> imgPix(Integer id){
        try {
        Credentials credentials = new Credentials();

        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("client_id", credentials.getClientId());
        options.put("client_secret", credentials.getClientSecret());
        options.put("certificate", credentials.getCertificate());
        options.put("sandbox", credentials.isSandbox());

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", id.toString());

            EfiPay efi= new EfiPay(options);
            Map<String, Object> response = efi.call("pixGenerateQRCode", params, new HashMap<String, Object>());
            /*
            File directory = new File("./imagensQrCode");
            if (!directory.exists()) {
                directory.mkdirs(); // Cria o diretório se não existir
            }
            ImageIO.write(ImageIO.read(new ByteArrayInputStream(javax.xml.bind.DatatypeConverter.parseBase64Binary(((String) response.get("imagemQrcode")).split(",")[1]))), "png", new File("./imagensQrCode/image_" + new SimpleDateFormat("dd-MM_HHmmss").format(new Date()) + ".png"));
             */
            System.out.println(response);
            String linkVisualizacao = response.get("linkVisualizacao").toString();
            return ResponseEntity.ok().body(linkVisualizacao);
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
