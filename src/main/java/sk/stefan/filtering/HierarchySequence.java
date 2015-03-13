///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package sk.stefan.filtering;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
///**
// * Wrapper nad postupnosti hierachii tried v nasom modeli, vzostupne
// * tj. prvy je zavisly od druheho, etc.  posledny nieje zavisly od nicoho.
// *
// * @author stefan
// */
//public class HierarchySequence {
//    private List<String> hierTableNamesSequence;
//    private List<String> bossReferences;
//    
//    private List<ActualEntity> hierActualEntitySequence;
//
//    
//    public HierarchySequence(List<String> hts){
//        this.hierTableNamesSequence = hts;
//        initHierSeq();
//    }
//    
//    private void createHS(){
//        for (String s : hierTableNamesSequence){
//            
//        }
//    }
//    
//    /**
//     * vrati korenoveho sefa v danej hierarchickej postupnosti
//     * @return 
//     */
//    public String getBigBoss(){
//        int last = hierTableNamesSequence.size() - 1;
//        return this.hierTableNamesSequence.get(last);
//    }
//
//    private void initHierSeq() {
//        this.hierActualEntitySequence = new ArrayList<>();
//        String tn, br;
//        ActualEntity ae;
//        for (int i = 0; i < hierTableNamesSequence.size() ; i++){
//            tn = hierTableNamesSequence.get(i);
//            br = bossReferences.get(i);
//            ae = new ActualEntity(tn, br);
//            hierActualEntitySequence.add(ae);
//        }
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    //getters & setters:
//    public List<ActualEntity> getHierarchySequence() {
//        return Collections.unmodifiableList(hierActualEntitySequence);
//    }
//    
//    public void setHierarchySequence(List<ActualEntity> hierarchySequence) {
//        this.hierActualEntitySequence = hierarchySequence;
//    }
//
//    public List<String> getHierTableSequence() {
//        return hierTableNamesSequence;
//    }
//    
//
//    public void setHierTableSequence(List<String> hierTableSequence) {
//        this.hierTableNamesSequence = hierTableSequence;
//    }
//
//    
//}
