package com.example.gradeCourse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAUtil {
  private static String RSA_ALGORITHM_NOPADDING = "RSA";
  private static Logger logger = LoggerFactory.getLogger(RSAUtil.class);

  public static String decryptRSA(String content, String privateKeyString) {
    try {
      KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_NOPADDING);
      privateKeyString = privateKeyString.replace("-----BEGIN PRIVATE KEY-----", "");
      privateKeyString = privateKeyString.replace("-----END PRIVATE KEY-----", "");
      privateKeyString = privateKeyString.replace("\r\n", "");
      privateKeyString = privateKeyString.replace(" ", "");

      byte[] privateKeyArray = privateKeyString.getBytes();
      byte[] dataArray = content.getBytes();
      PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyArray));
      PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

      Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_NOPADDING);
      cipher.init(Cipher.DECRYPT_MODE, privateKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(dataArray)), "UTF-8");

    } catch (Exception e) {
      logger.error("RSA解密遇到异常", e);
      return null;
    }
  }

}
