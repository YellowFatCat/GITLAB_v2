package prepare;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class AppSimpleJMXAgent {
   private MBeanServer mbs = null;

   public AppSimpleJMXAgent() {

       mbs = ManagementFactory.getPlatformMBeanServer();

      //  
      Hello helloBean = new Hello();
      ObjectName helloName = null;

      try {
         //  
         helloName = new ObjectName("AppSimpleJMXAgent:name=hellothere");
         mbs.registerMBean(helloBean, helloName);
      } catch(Exception e) {
         e.printStackTrace();
      }
   }

   //  
   private static void waitForEnterPressed() {
      try {
         System.out.println("Press <enter> to continue...");
         System.in.read();
      } catch (Exception e) {
         e.printStackTrace();
      }
    }

   public static void main(String argv[]) {
      AppSimpleJMXAgent agent = new AppSimpleJMXAgent();
      System.out.println("AppSimpleJMXAgent is running...");
      AppSimpleJMXAgent.waitForEnterPressed();
   }



    public interface HelloMBean {

        public void setMessage(String message);

        public String getMessage();

        public void sayHello();
    }

    public class Hello implements HelloMBean {
        private String message = null;

        public Hello() {
            message = "Hello there";
        }

        public Hello(String message) {
            this.message = message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void sayHello() {
            System.out.println(message);
        }
    }
}