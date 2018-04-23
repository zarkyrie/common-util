package cn.liangjieheng.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class RSAUtil {

    /** 指定加密算法为DESede */
    private static String ALGORITHM = "RSA";
    /** 指定key的大小 */
    private static int KEYSIZE = 1024;
    /** 指定公钥存放文件 */
    private static String PUBLIC_KEY_FILE = "PrivateKey";
    /** 指定私钥存放文件 */
    private static String PRIVATE_KEY_FILE = "PublicKey";
    /**
     * 生成密钥对
     */
    private static void generateKeyPair() throws Exception{
        /** RSA算法要求有一个可信任的随机数源 */
        SecureRandom sr = new SecureRandom();
        /** 为RSA算法创建一个KeyPairGenerator对象 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        /** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
        kpg.initialize(KEYSIZE, sr);
        /** 生成密匙对 */
        KeyPair kp = kpg.generateKeyPair();
        /** 得到公钥 */
        Key publicKey = kp.getPublic();
        /** 得到私钥 */
        Key privateKey = kp.getPrivate();
        /** 用对象流将生成的密钥写入文件 */
        ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
        ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
        oos1.writeObject(publicKey);
        oos2.writeObject(privateKey);
        /** 清空缓存，关闭文件输出流 */
        oos1.close();
        oos2.close();
    }

    /**
     * 加密方法
     * source： 源数据
     */
    public static String encrypt(String source) throws Exception{
        generateKeyPair();
        /** 将文件中的公钥对象读出 */
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
        Key key = (Key) ois.readObject();
        ois.close();
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b1);
    }

    /**
     * 解密算法
     * cryptograph:密文
     */
    public static String decrypt(String cryptograph) throws Exception{
        /** 将文件中的私钥对象读出 */
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
        Key key = (Key) ois.readObject();
        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b1 = decoder.decodeBuffer(cryptograph);
        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }
    public static void main(String[] args) throws Exception {
        //生成加密码的格式:40000000171111201111,29,山东日照职业技术学院
        String source = "04000000171111201111,18,山东日照职业技术学院";//要加密的字符串
        String cryptograph = encrypt(source);//生成的密文
        System.out.println("加密："+cryptograph);

        String target = decrypt(cryptograph);//解密密文
        System.out.println("解密："+target);

        //System.out.println("解密2："+decrypt("C4xqCDEJ8I7b1ABAfGFPHIZq2IXJMoBXV0NvTYK5idBY02LzxW4ohn6JIXybhFK/aFbJRY+oxn8s7jTAXAuHT+GJP+x1wKtiDuvesglSRmWc0zs8AroWSv/gy4Jlg41+cn2PtDqOFtMKZuloMm7sUuNmRVQV6MEpmGWA6ZUbIGc="));

        //System.out.println("解密3："+decrypt("XBc1Jt2XADDQGNKLMyl5hnj5C4KkXvTfR4dlDijMpYkirExGXkBaZGzhE0eitXNwTjtcTwsGNTh6jhkWAonzeUwPOCSTzGytCY6QJyOPw1IKXxOSMtyjNdmVM8aUBelUPnN2MyROWF8qNjrMdv8kuXOPQVBcE/KzaOdKo28BVzM="));

        //System.out.println("解密4："+decrypt("EkO8gEL+M0ItdMvYWPAjh8TgIQTYc0cMQDT8i7WyYbgadeDKLaxiCy2kliIwKyhejiRcH28zC+1l+FF7O0VkDmXCkM+Dm+c8F14eZ0s4QWwyTA5FTVD1Yqy5n1wG68dCNM91fy5tHn9JXkaSvmznVmy3Hn+W62afh0y6Qcgkwf8="));

        //System.out.println("解码5："+decrypt("byL9mzrQ+7zqJayR3beLRf4t6G7IcuZeFJGRoOb7rzvG4PXcSEY9fXzv8b0/dHuW4kEQVh9c+eeY6eAlaeYX2Bbm1s7frSx8+UpNJLDK4Qu0T8iAX6KOfsV4z/JfPK7fxd6/S6BnDYSGGL6zXflp0JYGXMbYHIPDFobhuDc5PRE="));

        //System.out.println("解码6:"+decrypt("aj9oEa2M63IINch5m6r7Xn0kgcQZ6gep6O0NWDNGNUyEGh7E1KFYhekQ80KNmDOzVE0ce0J0OU6QtLGekP0cnv0pzjC+vDZNU65547QI/Yro8AcVRBz6fiZS+dvFPeLGHHXEorm/x1fEcCKdAFxNLo+6zU1Nv09wtssgH3QN1Bo="));

        //System.out.println("解码6:"+decrypt("X4j+19+R2q5zGu0LZnoeEXbBPUY7KvCMSDVKObf5q7jvU1woAwwAMOzlrMSiyIRfyzo94SUAJV767YCMQTA8cXzRT7r1ZFt7mpwyA6KVbLdkh7UbjiJOYhxXxqq2GoPiyR0fhYxWCHrd7+pe30/IAObcK1uSDTbg87sul8kFAs4="));

    }
}
