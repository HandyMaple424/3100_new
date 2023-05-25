import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.util.Arrays;

public class MyClient22 {

    // First we will need to convert a string to a int.
    public static int convert(String str) {
        int val = 0;
        try {
            val = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            System.out.println("Wrong type of String");
        }
        return val;
    }

    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 50000);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));

            dout.write(("HELO\n").getBytes());

            String str = (String) din.readLine();

            dout.write(("AUTH max\n").getBytes());

            str = (String) din.readLine();

            dout.write(("REDY\n").getBytes());

            str = (String) din.readLine();
            System.out.println("The Job is: " + str);

            Boolean check = false;
            String savingfirst = "";
            while (!str.equals("NONE")) {
                if (!str.startsWith("JOBN")) {
                    dout.write(("REDY\n").getBytes());
                    System.out.println("SENT: REDY");
                    str = (String) din.readLine();
                    System.out.println("RCVD: " + str);

                    continue;
                }

                String[] job = str.split(" ");
                int jobID = convert(job[2]);

                int jobcore = convert(job[4]);
                int jobmem = convert(job[5]);
                int jobdisk = convert(job[6]);

                dout.write(("GETS Avail" + " " + jobcore + " " +jobmem + " " + jobdisk + "\n").getBytes());
                System.out.println("SENT: GETS Avail");

                str = (String) din.readLine();
                boolean Capable1 = false;
                if(str.contains("DATA 0")){
                    dout.write(("OK\n").getBytes());
                    str = (String) din.readLine();
                    dout.write(("GETS Capable" + " " + jobcore + " " +jobmem + " " + jobdisk + "\n").getBytes());
                    System.out.println("SENT: GETS capable");
                    str = (String) din.readLine();
                    Capable1 = true;
                }
                

                System.out.println("RCVD: " + str);
                String[] response = str.split(" ");
               
                int nRecs = convert(response[1]);

                dout.write(("OK\n").getBytes());
                System.out.println("got: OK");

                String largestServer = "";
                int largestServerID = 0;
                int ejwt = 0;
                int smallejwt = 10000000;

                String servername = "";
                int serverid = 0;
                if(Capable1 == true){
                    for (int i = 0; i < nRecs; i++) {
                        str = (String)din.readLine();
                        System.out.println("lowest amount: " + str);
                        String[] server = str.split(" ");
                        largestServer =server[0];
                        largestServerID = convert(server[1]);
                        dout.write(("EJWT " +  largestServer + " " + largestServerID +"\n").getBytes());
                        str = (String)din.readLine();
                        ejwt = convert(str);

                        if(smallejwt < ejwt){
                           servername.equals(largestServer);
                           serverid = largestServerID; 
                        }
                    }
                }

                

                String smallest = "";
                int smallestID1 = 0;

                boolean find = false;
                // first capable
                for (int i = 0; i < nRecs; i++) {

                    str = (String)din.readLine();
                    System.out.println("RCVD: " + str);

                    String[] server = str.split(" ");

                    if (find == false) {
                        System.out.println("got" + str);
                        
                            smallest = server[0];
                            smallestID1 = convert(server[1]);
                            find = true;
                            
                        
                    }
                }

                /*
                 * for (int i = 0; i < nRecs; i++) {
                 * str = (String)din.readLine();
                 * System.out.println("RCVD: " + str);
                 * 
                 * String[] server = str.split(" ");
                 * 
                 * largestServer = server[0];
                 * largestServerID = Integer.parseInt(server[1]);
                 * }
                 */

                dout.write(("OK\n").getBytes());
                System.out.println("SENT: OK");

                str = (String) din.readLine();
                System.out.println("RCVD: " + str);

                dout.write(("SCHD" + jobID + " " + smallest + " " + smallestID1 + "\n").getBytes());
                System.out.println("SENT: SCHD " + jobID + " " + smallest + " " + smallestID1);
                find = false;
                str = (String) din.readLine();
                System.out.println("RCVD: " + str);

                dout.write(("REDY\n").getBytes());
                System.out.println("SENT: REDY");

                str = (String) din.readLine();
                System.out.println("RCVD: " + str);
            }

            dout.write(("QUIT\n").getBytes());

            str = (String) din.readLine();
            System.out.println("Done Scheduling");

            din.close();
            dout.close();
            s.close();

        } catch (Exception a) {
            a.printStackTrace();
            // fix here
        }

    }
}