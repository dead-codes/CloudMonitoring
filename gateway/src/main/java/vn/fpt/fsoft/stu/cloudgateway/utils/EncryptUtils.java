package vn.fpt.fsoft.stu.cloudgateway.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Component
public class EncryptUtils {
    private static Logger LOGGER = LoggerFactory.getLogger(EncryptUtils.class);

    private static SecretKeySpec secretKey;
    private static byte[] key;
    final String saltKey = "pJ6d6C*cCSTv@*qt#cVn^T!bS36FUYLNdnX8!HS@xsmypsK+SQYF2m%G4ryg6JUy9&usy&haL?-%JjsYb_e!rQq";

    public String base64Encrypt(String source) {
        String encrypted = null;
        if (source != null && (!"".equals(source))) {
            try {
                encrypted = Base64.getEncoder().encodeToString(source.getBytes("utf-8"));
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        return encrypted;
    }

    public String base64Decrypt(String source) {
        String decrypted = null;
        if (source != null && (!"".equals(source))) {
            try {
                byte[] base64decodedBytes = Base64.getDecoder().decode(source);
                decrypted = new String(base64decodedBytes, "utf-8");
            } catch (UnsupportedEncodingException e) {
                System.out.println("Error :" + e.getMessage());
            } catch (Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }
        }
        return decrypted;
    }

    public void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public String AESEncrypt(String strToEncrypt) {
        try {
            setKey(saltKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String AESDecrypt(String strToDecrypt) {
        try {
            setKey(saltKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

    public static void main(String[] args) {
        EncryptUtils encryptUtils = new EncryptUtils();
        try {
            String e = encryptUtils.base64Encrypt(encryptUtils.AESEncrypt("0ILMTwoTX1/C59Y1poJzBSSS1nH3yDxdxGTe2ASn"));
            System.out.println(e);
            String decrypt = encryptUtils.AESDecrypt(encryptUtils.base64Decrypt(e));
            System.out.println(decrypt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
