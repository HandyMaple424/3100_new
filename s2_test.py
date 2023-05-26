#!/usr/bin/env python3
import json
from argparse import ArgumentParser
from pathlib import Path

from mark_client import print_results, parse_client_results
from ref_results import parse_ref_results

metrics = ["Turnaround time", "Resource utilisation", "Total rental cost"]

parser = ArgumentParser(description="Stage 2 marking, e.g. ./s2_test.py \"java MyClient\" -n")
parser.add_argument("client_command_string", help="a command string used to run the client")
parser.add_argument("-c", "--config_dir", default="S2TestConfigs", help="config directory")
parser.add_argument("-n", "--newline", action="store_true", help="messages to ds-server are newline-terminated")
parser.add_argument("-r", "--reference_results", help="reference results path")
parser.add_argument("-t", "--client_results", help="client results path")
args = parser.parse_args()

if args.reference_results:
    with open(args.reference_results, 'r') as f:
        reference_results = json.load(f)
else:
    reference_results = parse_ref_results(args.config_dir, metrics)
    out_path = Path("./results/ref_results.json")
    out_path.parent.mkdir(parents=True, exist_ok=True)
    with open(out_path, 'w') as f:
        json.dump(reference_results, f, indent=2)

if args.client_results:
    with open(args.client_results, 'r') as f:
        client_results = json.load(f)
else:
    client_results = parse_client_results(args.config_dir, metrics, args.client_command_string, args.newline)

print_results(client_results, reference_results, metrics, "tt")


			str = (String) din.readLine();
                        System.out.println("lowest amount: " + str);

                        String[] server = str.split(" ");
                        largestServer =server[0];
                        System.out.println(largestServer + " ni");
                        largestServerID = convert(server[1]);
                        System.out.println(largestServerID+ "niiiiiiiii");

                        dout.write(("OK\n").getBytes());
                        str = (String) din.readLine();

                        dout.write(("EJWT " +  largestServer + " " + largestServerID +"\n").getBytes());
                        
                        str = (String) din.readLine();
                        System.out.println(str + " THIS IS SUPPOSED TO BE EJWT RESPONSE");
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
