//
//import com.vaadin.server.FileDownloader;
//import com.vaadin.ui.Button;
//
//public class ExportFile
//{
//   protected FileDownloader fileDownloader;
//   protected Button downloadButton;
//
//   //1.
//   /**
//    * 
//    */
//   public void initAction(TableView tableView)
//   {
//        downloadButton = new Button("Download");
//        fileDownloader = new FileDownloader(FileDownloadUtils.createFileResource(new File("NoPath")))
//        {
//            @Override
//            public boolean handleConnectorRequest(VaadinRequest request, VaadinResponse response, String path) throws IOException
//            {
//                createExportContent();
//                return super.handleConnectorRequest(request, response, path);
//            }
//        };
//        fileDownloader.extend(downloadButton);
//   }
//
//   //2.
//   /**
//    */
//   public void createExportContent()
//   {
//       try
//       {
//           final File file = File.createTempFile("export", getFileExtension());
//           file.deleteOnExit();
//
//           BufferedOutputStream bufferedStream = new BufferedOutputStream(new FileOutputStream(file));
//           generateContent(bufferedStream);
//           bufferedStream.close();
//
//           fileDownloader.setFileDownloadResource(FileDownloadUtils.createFileResource(file));
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException("Error exporting!", e);
//        }
//    }
//}