package com.example.demo.Infrastructure.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.io.Decoders;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileUtils {

    public static PrivateKey loadPrivateKey(final String keyPath) {
        try {
            final String content = getFileContent(keyPath);
            if (content == null) {
                log.error("Private key content is null. Check file path: {}", keyPath);
                return null;
            }

            byte[] decoded = Decoders.BASE64.decode(content);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        } catch (Exception e) {
            log.error("Error occurred while loading private key", e);
            return null;
        }
    }

    public static PublicKey loadPublicKey(final String keyPath) {
        try {
            final String content = getFileContent(keyPath);
            if (content == null) {
                log.error("Public key content is null. Check file path: {}", keyPath);
                return null;
            }

            byte[] decoded = Decoders.BASE64.decode(content);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePublic(spec);
        } catch (Exception e) {
            log.error("Error occurred while loading public key", e);
            return null;
        }
    }

    private static String getFileContent(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            String raw = Files.readString(resource.getFile().toPath());

            // Remove headers and all whitespace (newlines, spaces, tabs)
            return raw.replaceAll("-----BEGIN (.*)-----", "")
                      .replaceAll("-----END (.*)-----", "")
                      .replaceAll("\\s", "");
        } catch (IOException e) {
            log.error("Failed to read key file from classpath: {}", path, e);
            return null;
        }
    }
}
