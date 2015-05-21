///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package sk.stefan.utils;
//
//import java.io.UnsupportedEncodingException;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
///**
// *
// * @author stefan
// */
//public class ToolsEncrypt {
//    
//    
//    private static MessageDigest md;
//    
//    static{
//        try {
//            md = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
////    public byte[] encryptPassword(String password);
//    
//        /**
//     * Vrati hash hesla.
//     *
//     * @param password heslo, z ktorého bude vytvoreny hash.
//     * @return hash hesla
//     */
//    public static synchronized byte[] encryptPassword(String password) {
//        
//        
//        
//        try {
//            byte[] bytesa;
//            
//            String saltedPassword = password;//.toUpperCase() + "KAROLKO";
//            md.update(saltedPassword.getBytes("UTF-8"));
//
//            bytesa = md.digest();
//
//            return bytesa;
//
//        } catch (UnsupportedEncodingException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    
//}
