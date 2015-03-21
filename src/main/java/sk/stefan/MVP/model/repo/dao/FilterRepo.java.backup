///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package sk.stefan.MVP.model.repo.dao;
//
//import com.vaadin.ui.Notification;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import org.apache.log4j.Logger;
//import sk.stefan.DBconnection.DoDBconn;
//
///**
// *
// * @author stefan
// */
//public class FilterRepo {
//
//    private static final Logger log = Logger.getLogger(FilterRepo.class);
//
//    //0.
//    /**
//     * Konstruktor:
//     */
//    public FilterRepo() {
//    }
//
//    public List<Integer> findAllFilteringIds(String sql) {
//
//        try {
//            Connection conn = DoDBconn.getConnection();
//            Statement st;
//            st = conn.createStatement();
//
//            List<Integer> listIds;
//            // Notification.show(sql);
//            log.info("SQL: " + sql);
//
//            ResultSet rs;
//            rs = st.executeQuery(sql);
//
//            System.out.println(sql);
//            listIds = this.fillListIds(rs);
//
//            rs.close();
//            st.close();
//            DoDBconn.releaseConnection(conn);
//
//            return listIds;
//
//        } catch (SecurityException | IllegalArgumentException | SQLException e) {
//            Notification.show("Chyba, filterRepo::findAllIds(...)",
//                    Notification.Type.ERROR_MESSAGE);
//            log.error(e.getMessage());
//            return null;
//        }
//
//    }
//    
//    /**
//     * 
//     */
//    private List<Integer> fillListIds(ResultSet rs) {
//
//        List<Integer> listIds = new ArrayList<>();
//        int id;
//        
//        try {
//            while (rs.next()) {
//                id = rs.getInt("id");
//                listIds.add(id);
//            }
//
//            return listIds;
//        } catch (SecurityException | IllegalArgumentException | SQLException e) {
//            Notification.show("Chyba, FilterRepo, fillListIds(ResultSet rs)",
//                    Notification.Type.ERROR_MESSAGE);
//            log.error(e.getLocalizedMessage(), e);
//            return null;
//        }
//    }
//}
