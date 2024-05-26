//
//import java.io.*;
//import java.util.Scanner;
//
//class voterLogin{
//    protected String username;
//    protected String password;
//    voterLogin() throws IOException {
//        FileReader writer= new FileReader(username);
//        writer.write(password);
//        writer.close();
//    }
//}
//class login extends voterLogin{
//    Scanner scanner = new Scanner(System.in);
//    System.out.println("what is ur userID");
//    String Username = scanner.nextLine();
//    Scanner scanner1 = new Scanner(System.in);
//    System.out.println("what is ur password:-");
//    String Password = scanner1.nextLine();
//    login() throws IOException{
//        while (true) {
//            String line;
//            FileReader reader = new FileReader(Username.txt);
//            try {
//                while((line = reader1.readline()) != null){
//                    if(line.contains(Password)){
//                        System.out.println("match found");
//                        break;
//                    }
//                }
//            }
//            finally {
//                reader.close();
//            }
//        }
//    }
//
//}
//public class Login {
//    public static void main(String[] args) {
//    }
//}