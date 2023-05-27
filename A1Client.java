import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.util.Arrays;

public class MyClient {

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
    // quick way of printing. thanks Jayden! :D
    public static void print(String str) {
        try{
    System.out.println(str);
        } catch(NumberFormatException e) {
            System.out.println("Cant print right now");
        }

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
            print("The Job is: " + str);
            // start the looping for scheduling
            Boolean check = false;
            String savingfirst = "";
            while (!str.equals("NONE")) {
                if (!str.startsWith("JOBN")) {
                    dout.write(("REDY\n").getBytes());
                    print("SENT: REDY");
                    str = (String) din.readLine();
                    print("RCVD: " + str);

                    continue;
                }
                //Splitting into seperate parts of the job
                String[] job = str.split(" ");
                int jobID = convert(job[2]);
                int jobcore = convert(job[4]);
                int jobmem = convert(job[5]);
                int jobdisk = convert(job[6]);
                

                dout.write(("GETS Avail" + " " + jobcore + " " +jobmem + " " + jobdisk + "\n").getBytes());
                print("SENT: GETS Avail");

                str = (String) din.readLine();
                print("SENT: GETS avail"+ str);

                int nRecs2 = 0;
                boolean Capable1 = false;
                //if no data is present when using get avail then we have to get any capable server.
                if(str.contains("DATA 0")){
                    dout.write(("OK\n").getBytes());
                    str = (String) din.readLine();
                    dout.write(("GETS Capable" + " " + jobcore + " " +jobmem + " " + jobdisk + "\n").getBytes());
                    print("SENT: GETS capable");
                    str = (String) din.readLine();
                    Capable1 = true;

                    String[] response = str.split(" ");
                    nRecs2 = convert(response[1]);
                }
                

                print("RCVD: " + str);
                String[] response = str.split(" ");
               
                int nRecs = convert(response[1]);

                dout.write(("OK\n").getBytes());
                print("got: OK");

             

                String smallest = "";
                int smallestID1 = 0;

                boolean find = false;
                // first capable
                for (int i = 0; i < nRecs; i++) {

                    str = (String)din.readLine();
                    print("RCVD: " + str);

                    String[] server = str.split(" ");

                    if (find == false) {
                        print("got" + str);
                        
                            smallest = server[0];
                            smallestID1 = convert(server[1]);
                            find = true;
                            
                        
                    }
                }

                String largestServer = "";
                int largestServerID = 0;
                int ejwt = 0;
                int smallejwt = 10000000;

                String servername = "";
                int serverid = 0;
                if(Capable1 == true){
                    for (int i = 0; i < nRecs2; i++) {
                        
                    }
                }
                    
                dout.write(("OK\n").getBytes());
                print("SENT: OK");

                str = (String) din.readLine();
                print("RCVD: " + str);

                dout.write(("SCHD" + jobID + " " + smallest + " " + smallestID1 + "\n").getBytes());
                print("SENT: SCHD " + jobID + " " + smallest + " " + smallestID1);
                find = false;
                str = (String) din.readLine();
                print("RCVD: " + str);

                dout.write(("REDY\n").getBytes());
                print("SENT: REDY");

                str = (String) din.readLine();
                print("RCVD: " + str);
            }

            dout.write(("QUIT\n").getBytes());

            str = (String) din.readLine();
            print("Done Scheduling");

            din.close();
            dout.close();
            s.close();

        } catch (Exception a) {
            a.printStackTrace();
            // fix here
        }

    }
}