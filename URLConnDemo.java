import java.net.*;
import java.io.*;
public class URLConnDemo
{
   public static void main(String [] args)
   {
      try
      {
         URL url = new URL("https://sibylcloudnewgeneration.win/link/x9vxd7n1rBqDGSAF?is_ss=1");
         URLConnection urlConnection = url.openConnection();
         HttpURLConnection connection = null;
         if(urlConnection instanceof HttpURLConnection)
         {
            connection = (HttpURLConnection) urlConnection;
         }
         else
         {
            System.out.println("请检查URL地址");
            return;
         }
         BufferedReader in = new BufferedReader(
         new InputStreamReader(connection.getInputStream()));
         String current;
         while((current = in.readLine()) != null)
         System.out.println(current);

    }catch(IOException e)
      {
         e.printStackTrace();
      }
   }
}