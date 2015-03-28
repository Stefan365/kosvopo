//package com.example.samplevaadin;
//
//import javax.servlet.annotation.WebServlet;
//
//import com.example.samplevaadin.ui.componet.custom.AdvancedFileDownloader;
//import com.example.samplevaadin.ui.componet.custom.AdvancedFileDownloader.AdvancedDownloaderListener;
//import com.example.samplevaadin.ui.componet.custom.AdvancedFileDownloader.DownloaderEvent;
//import com.vaadin.annotations.Theme;
//import com.vaadin.annotations.VaadinServletConfiguration;
//import com.vaadin.server.VaadinRequest;
//import com.vaadin.server.VaadinServlet;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Link;
//import com.vaadin.ui.TextField;
//import com.vaadin.ui.UI;
//import com.vaadin.ui.VerticalLayout;
//
//@SuppressWarnings("serial")
////@Theme("samplevaadin")
//public class NewClass extends UI {
//
//    @WebServlet(value = "/*", asyncSupported = true)
//    @VaadinServletConfiguration(productionMode = false, ui = NewClass.class)
//    public static class Servlet extends VaadinServlet {
//    }
//
//    @Override
//    protected void init(VaadinRequest request) {
//
//        final VerticalLayout layout = new VerticalLayout();
//        layout.setMargin(true);
//
//        final TextField inputFilepathField = new TextField();
//        inputFilepathField.setValue("/home/visruthcv/README.txt");
//        inputFilepathField.setImmediate(true);
//        layout.addComponent(inputFilepathField);
//
//        Button downloadButton = new Button("Download Button");
//        // or
//        Link downloadLink = new Link();
//        downloadLink.setCaption("Download link");
//
//        final AdvancedFileDownloader downloader = new AdvancedFileDownloader();
//        
//        downloader.addAdvancedDownloaderListener(new AdvancedDownloaderListener() {
//
//            /**
//             * This method will be invoked just before the download starts.
//             * Thus, a new file path can be set.
//             *
//             * @param downloadEvent
//             */
//            @Override
//            public void beforeDownload(DownloaderEvent downloadEvent) {
//
//                String filePath = inputFilepathField.getValue();
//
//                downloader.setFilePath(filePath);
//
//                System.out.println("Starting downlad by button "
//                        + filePath.substring(filePath.lastIndexOf("/")));
//            }
//
//        });
//
//        downloader.extend(downloadButton);
//        layout.addComponent(downloadButton);
//
//        final AdvancedFileDownloader downloaderForLink = new AdvancedFileDownloader();
//        downloaderForLink
//                .addAdvancedDownloaderListener(new AdvancedDownloaderListener() {
//
//                    /**
//                     * This method will be invoked just before the download
//                     * starts. Thus, a new file path can be set.
//                     *
//                     * @param downloadEvent
//                     */
//                    @Override
//                    public void beforeDownload(DownloaderEvent downloadEvent) {
//
//                        String filePath = inputFilepathField.getValue();
//
//                        downloaderForLink.setFilePath(filePath);
//                        System.out.println("Starting download by link "
//                                + filePath.substring(filePath.lastIndexOf("/")));
//
//                    }
//
//                });
//
//        downloaderForLink.extend(downloadLink);
//        layout.addComponent(downloadLink);
//
//        setContent(layout);
//    }
//
//}
