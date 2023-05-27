import java.util.Arrays;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.BufferedReader;

public class A1Client {

    // First we will need to convert a reading to a int.
    public static int convert(String read) {
        int val = 0;
        try {
            val = Integer.parseInt(read);
        } catch (NumberFormatException e) {
            System.out.println("Wrong type of String");
        }
        return val;
    }

    // quick way of printing. thanks Jayden! :D
    public static void print(String read) {
        try{
    System.out.println(read);
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

            String read = (String) din.readLine();

            dout.write(("AUTH max\n").getBytes());

            read = (String) din.readLine();

            dout.write(("REDY\n").getBytes());

            read = (String) din.readLine();
            print("The Job is: " + read);
            // start the looping for scheduling
            Boolean check = false;
            String savingfirst = "";
            while (!read.equals("NONE")) {
                if (!read.startsWith("JOBN")) {
                    dout.write(("REDY\n").getBytes());
                    print("SENT: REDY");
                    read = (String) din.readLine();
                    print("RCVD: " + read);

                    continue;
                }
                //Splitting into seperate parts of the job
                String[] job = read.split(" ");
                int jobID = convert(job[2]);
                int jobcore = convert(job[4]);
                int jobmem = convert(job[5]);
                int jobdisk = convert(job[6]);
                

                dout.write(("GETS Avail" + " " + jobcore + " " +jobmem + " " + jobdisk + "\n").getBytes());
                print("SENT: GETS Avail");

                read = (String) din.readLine();
                print("SENT: GETS avail"+ read);

                int nRecs2 = 0;
                boolean Capable1 = false;
                //if no data is present when using get avail then we have to get first capable server.
                if(read.contains("DATA 0")){
                    dout.write(("OK\n").getBytes());
                    read = (String) din.readLine();
                    dout.write(("GETS Capable" + " " + jobcore + " " +jobmem + " " + jobdisk + "\n").getBytes());
                    print("SENT: GETS capable");
                    read = (String) din.readLine();
                    Capable1 = true;

                    String[] response = read.split(" ");
                    nRecs2 = convert(response[1]);
                }
                

                print("RCVD: " + read);
                String[] response = read.split(" ");
               
                int nRecs = convert(response[1]);

                dout.write(("OK\n").getBytes());
                print("got: OK");

             

                String smallest = "";
                int smallestID1 = 0;

                boolean find = false;
                // first capable
                for (int i = 0; i < nRecs; i++) {

                    read = (String)din.readLine();
                    print("RCVD: " + read);

                    String[] server = read.split(" ");

                    if (find == false) {
                        print("got" + read);
                        
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

                read = (String) din.readLine();
                print("RCVD: " + read);

                dout.write(("SCHD" + jobID + " " + smallest + " " + smallestID1 + "\n").getBytes());
                print("SENT: SCHD " + jobID + " " + smallest + " " + smallestID1);
                find = false;
                read = (String) din.readLine();
                print("RCVD: " + read);

                dout.write(("REDY\n").getBytes());
                print("SENT: REDY");

                read = (String) din.readLine();
                print("RCVD: " + read);
            }

            dout.write(("QUIT\n").getBytes());

            read = (String) din.readLine();
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