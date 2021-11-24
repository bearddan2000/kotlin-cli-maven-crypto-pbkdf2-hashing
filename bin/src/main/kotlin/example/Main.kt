package example

import org.apache.commons.codec.binary.Hex;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

class Encode {

  val SALT = "abc123";
  val iterations = 10000;
  val keyLength = 512;

  fun hashpw(plainText: String): String {

    val passwordChars = plainText.toCharArray();
    val saltBytes = SALT.toByteArray();

    try {
        val skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
        val spec = PBEKeySpec( passwordChars, saltBytes, iterations, keyLength );
        val key = skf.generateSecret( spec );
        val res = key.getEncoded( );
        return Hex.encodeHexString(res);
    } catch ( e: Exception) {
        throw RuntimeException( e );
    }
    return String()
  }

  fun verify(pass: String, hash: String): Boolean {
    return hashpw(pass).equals(hash);
  }
}

fun main(args: Array<String>) {
  val encode = Encode();
  val plainText = "Hello World!";

  val hash = encode.hashpw(plainText);

  val isMatch = encode.verify(plainText, hash);

  println("Original: " + plainText);
  println("Hash: " + hash);
  println("Verified: " + isMatch);
}
