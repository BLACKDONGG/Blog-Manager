package com.chpt10.model;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
/**
 * 
 * @since 2015��8��20�� ����2:22:08
 * @author hym
 */
public class SignDemo {
    
    /**�õ�������˽Կ/��Կ��
     * @return
     * @author hym
     */
    public static KeyPair getKeypair(){
      //����RSA��Կ��(myKeyPair)
        KeyPairGenerator myKeyGen = null;
        try {
            myKeyGen = KeyPairGenerator.getInstance("RSA");
            myKeyGen.initialize(1024);            
        } catch (NoSuchAlgorithmException e) {           
            e.printStackTrace();
        }
        KeyPair myKeyPair = myKeyGen.generateKeyPair();
        return myKeyPair;
    }
    /**������Կ�Զ���Ϣ���м��ܣ����ع�Կֵ
     * @param mySig
     * @param myKeyPair
     * @param infomation
     * @return
     * @author hym
     */
    public static byte[] getpublicByKeypair(Signature mySig,KeyPair myKeyPair,byte[] infomation){
        byte[] publicInfo=null;
        try {           
            mySig.initSign(myKeyPair.getPrivate());  //��˽Կ��ʼ��ǩ������
            mySig.update(infomation);  //����ǩ�������ݴ��͸�ǩ������     
            publicInfo = mySig.sign();  //����ǩ������ֽ�����          
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicInfo;
    }
    
    /**��Կ��֤ǩ��
     * @param mySig
     * @param myKeyPair
     * @param infomation
     * @param publicInfo
     * @return
     * @author hym
     */
    public static boolean decryptBypublic(Signature mySig,  KeyPair myKeyPair,String infomation,byte[] publicInfo){
        boolean verify=false;
        try {
            mySig.initVerify(myKeyPair.getPublic());  //ʹ�ù�Կ��ʼ��ǩ������,������֤ǩ��
            mySig.update(infomation.getBytes()); //����ǩ������
            verify= mySig.verify(publicInfo); //�õ���֤���
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verify;
    }
    
    
    public static void main(String[] args) {       
        try {           
            KeyPair keyPair=getKeypair();
            Signature mySig = Signature.getInstance("MD5WithRSA");//��ָ���㷨����ǩ������
            byte[] publicinfo=getpublicByKeypair(mySig,keyPair,"��֤��".getBytes());
            boolean verify=decryptBypublic(mySig, keyPair, "��֤��", publicinfo);
            System.out.println("��֤ǩ���Ľ���ǣ�"+verify);
        } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
        }  
    }
}