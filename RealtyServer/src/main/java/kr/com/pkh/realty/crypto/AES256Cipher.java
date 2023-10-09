package kr.com.pkh.realty.crypto;


/**
 * 
 * JRE System lib 를 사용한 AES 암복호화 
 * 
 * @author Park_Kwang_Ho
 *
 */
public class AES256Cipher {
    private static volatile AES256Cipher INSTANCE;

    static String IV = ""; // 16bit (2byte)

    public static AES256Cipher getInstance() {
        if (INSTANCE == null) {
            synchronized (AES256Cipher.class) {
                if (INSTANCE == null)
                    INSTANCE = new AES256Cipher();
            }
        }
        return INSTANCE;
    }
    
    public String generateKey() {
    	char[] tmp = new char[32];
		for(int i=0; i<tmp.length; i++) {
			int div = (int) Math.floor( Math.random() * 2 );
			
			if(div == 0) {
				tmp[i] = (char) (Math.random() * 10 + '0') ;
			}else {
				tmp[i] = (char) (Math.random() * 26 + 'A') ;
			}
		}
    	return new String(tmp);
    }
    
    public String generateIv() {
    	char[] tmp = new char[16];
		for(int i=0; i<tmp.length; i++) {
			int div = (int) Math.floor( Math.random() * 2 );
			
			if(div == 0) {
				tmp[i] = (char) (Math.random() * 10 + '0') ;
			}else {
				tmp[i] = (char) (Math.random() * 26 + 'A') ;
			}
		}
    	return new String(tmp);
    }

//    public static String AES256Encode(String str, String secretKey, String secretIv)
//            throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
//            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//        byte[] keyData = secretKey.getBytes();
//        IV = secretIv;
//
//        SecretKey secureKey = new SecretKeySpec(keyData, "AES");
//
//        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes()));
//
//        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
//        String enStr = new String(Base64.encodeBase64(encrypted));
//
//        return enStr;
//    }

//    public static String AES256Decode(String str, String secretKey, String secretIv)
//            throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException,
//            InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
//        byte[] keyData = secretKey.getBytes();
//        IV = secretIv;
//        
//        SecretKey secureKey = new SecretKeySpec(keyData, "AES");
//        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV.getBytes("UTF-8")));
//
//        byte[] byteStr = Base64.decodeBase64(str.getBytes());
//
//        return new String(c.doFinal(byteStr), "UTF-8");
//    }
}
