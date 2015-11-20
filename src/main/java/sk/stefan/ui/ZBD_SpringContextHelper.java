//package sk.stefan.ui;
//
//import javax.servlet.ServletContext;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//public class ZBD_SpringContextHelper {
//
//    private final ApplicationContext context;
//
//    public ZBD_SpringContextHelper(ServletContext servletContext) {
//        /*ServletContext servletContext =
//                ((WebApplicationContext) application.getContext())
//                .getHttpSession().getServletContext();*/
//        context = WebApplicationContextUtils.
//                getRequiredWebApplicationContext(servletContext);
//    }
//
//    public Object getBean(final String beanRef) {
//        return context.getBean(beanRef);
//    }
//
////    private ApplicationContext context;
////
////    public SpringContextHelper(Application application) {
////        ServletContext servletContext = ((WebApplicationContext) application.getContext()).getHttpSession().getServletContext();
////        context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
////
////        context = WebApplicationContextUtils.
////                getRequiredWebApplicationContext(servletContext);
////    }
////
////    public Object getBean(final String beanRef) {
////        return context.getBean(beanRef);
////    }
//}