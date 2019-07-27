/* SslSocketClient.java
 - Copyright (c) 2014, HerongYang.com, All Rights Reserved.
 */
import java.io.*;
import java.net.*;
import javax.net.ssl.*;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

class SSLTool {

  public static SSLSocketFactory getAllTrustedSocketFactory() {
    // Create a trust manager that does not validate certificate chains
    TrustManager[] trustAllCerts = new TrustManager[] { 
      new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() { 
          return new X509Certificate[0]; 
        }
        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
    }};

    // Ignore differences between given hostname and certificate hostname
    HostnameVerifier hv = new HostnameVerifier() {
      public boolean verify(String hostname, SSLSession session) { return true; }
    };

    // Install the all-trusting trust manager
    try {
      SSLContext sc = SSLContext.getInstance("TLS");
      sc.init(null, trustAllCerts, new SecureRandom());
      return sc.getSocketFactory();
    } catch (Exception e) {}
    return null;
  }
}

public class SslClient {
   public static void main(String[] args) {

      BufferedReader in = new BufferedReader(
         new InputStreamReader(System.in));
      PrintStream out = System.out;
      SSLSocketFactory f = SSLTool.getAllTrustedSocketFactory();
      try {
        //  SSLSocket c = (SSLSocket) f.createSocket("google.com", 443);
         SSLSocket c = (SSLSocket) f.createSocket("localhost", 5001);
         printSocketInfo(c);
         c.startHandshake();
         BufferedWriter w = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
         BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()));
         String m = null;
         out.println("ASD");
         while ((m=r.readLine())!= null) {
            out.println("ASD2");
            out.println(m);
            m = in.readLine();
            w.write(m,0,m.length());
            w.newLine();
            w.flush();
         }
         w.close();
         r.close();
         c.close();
      } catch (IOException e) {
         System.err.println(e.toString());
      }
   }
   private static void printSocketInfo(SSLSocket s) {
      System.out.println("Socket class: "+s.getClass());
      System.out.println("   Remote address = "
         +s.getInetAddress().toString());
      System.out.println("   Remote port = "+s.getPort());
      System.out.println("   Local socket address = "
         +s.getLocalSocketAddress().toString());
      System.out.println("   Local address = "
         +s.getLocalAddress().toString());
      System.out.println("   Local port = "+s.getLocalPort());
      System.out.println("   Need client authentication = "
         +s.getNeedClientAuth());
      SSLSession ss = s.getSession();
      System.out.println("   Cipher suite = "+ss.getCipherSuite());
      System.out.println("   Protocol = "+ss.getProtocol());
   }
}
