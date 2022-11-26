package io.github.lkodex.appsistemabibliotecario.sistema.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
    private static Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String ALGORITHM = "SHA-256";

    public static String hash(String input) throws NoSuchAlgorithmException {
        return SHA.hash(input.getBytes(UTF_8));
    }

    public static String hash(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
        messageDigest.update(input);
        byte[] resultBytes = messageDigest.digest();

        return SHA.byteToHex(resultBytes);
    }

    public static Boolean compare(String inputHash, String hash){
        final int HASH_256_STRING_LENGTH = 64;
        if (hash.length() != HASH_256_STRING_LENGTH) return Boolean.FALSE;
        if (inputHash.length() != HASH_256_STRING_LENGTH) return Boolean.FALSE;
        return inputHash.equals(hash);
    }

    public static Boolean compareHash(String input, String hash){
        final int HASH_256_STRING_LENGTH = 64;
        if(hash.length() != HASH_256_STRING_LENGTH) return Boolean.FALSE;
        String inputHash = "";
        try {
            inputHash = SHA.hash(input);
        } catch (NoSuchAlgorithmException e){
            System.out.println(String.format("Algoritmo %s não disponível nesse ambiente!", ALGORITHM));
            e.printStackTrace();
        }
        return inputHash.equals(hash);
    }

    public static Boolean compare(String input, byte[] hash){
        String hashString = SHA.byteToHex(hash);
        return compare(input, hashString);
    }

    private static String byteToHex(byte[] input){
        StringBuilder stringBuilder = new StringBuilder();
        for(byte b : input){
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
