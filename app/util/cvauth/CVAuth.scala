package util.cvauth

import java.util.Date
import java.text.SimpleDateFormat

object CVAuth {
    
    def encrypt (secretKey: String, sourcePassword: String, targetLen: Int):String = {
        var text = secretKey;
        // Our output text
        var outText = "";
        // Iterate through each character
        var i = 0
        while(i < text.length) {
            var j = 0
            while(j < sourcePassword.length && i < text.length) {
                outText += (text.charAt(i).toByte ^ 
                    sourcePassword.charAt(j).toByte).toChar
                j += 1
                i += 1
            }
        }
        return outText;
    }
    
    def getW3CDate():String = {
        var format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
        var date = format.format(new Date()) + "+00:00";
        return date;
    }
    
    def salt(saltSize: Int): String = {

        var chars = "123456789abcdefghijklmnopqrstuvwxyz";
        val rnd = new scala.util.Random();

        var value = new Array[Byte](saltSize)
        var len = chars.length;

        var i = 0
        while ( i < saltSize) {
            var pos = rnd.nextInt(20);
            value(i) = chars.charAt(pos).toByte
            i += 1
        }

        var md5sum = play.api.libs.Codecs.md5(value);
        
        
        
        return md5sum;
    }
    
    def createToken(apiKey: String,apiSecret: String,userName: String,userPass: String): String = {

        var nonce = salt(10);
    
        var userPass64 = new sun.misc.BASE64Encoder().encode(userPass.getBytes)
        var created = getW3CDate();
        var sha1 = play.api.libs.Codecs.sha1(nonce + created + apiSecret);
    
        var digest = new sun.misc.BASE64Encoder().encode(sha1.getBytes)
    
        var token = "AuthToken ApiKey=\"" + apiKey + "\", TokenDigest=\"" +
        digest + "\", Nonce=\""+ nonce + "\", Created=\"" + created + "\", Username=\"" + 
        userName + "\", Password=\"" + userPass64 + "\"";
        
        return token;
    }

}