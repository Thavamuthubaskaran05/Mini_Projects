package com.company;

import java.util.*;

public class Atm {
    static Scanner sc=new Scanner(System.in);
    static int amount[]={100,200,500,2000};
    static int count[]={0,0,0,0};
    static int max[]={200,200,200,200};
    static long total_amount=0;
    static User user_obj[]=new User[3];
    static int currentuser=-1;



    public static void main(String args[]){

        user_obj[0]=new User("thavamuthu",1234,100000);
        user_obj[1]=new User("muthu",1235,200000);
        user_obj[2]=new User("bass",1234,300000);

        Flush.flush();

        System.out.println("\n***Welcome To ATM Machine***\n\nType 1 To Go To: Admin\nType 2 To Go To: User\nType 3 To Go To: Exit\n\nEnter Your Choice:");
        int choice=sc.nextInt();
        boolean flag=true;

        while(flag){
            switch(choice)
            {
                case 1:
                    Admin();
                    break;
                case 2:
                    Users();
                    break;
                case 3:
                    // System.out.println("Exit");
                    Flush.flush();
                    flag=false;
                    break;
                default :
                    Flush.flush();
                    System.out.println("Invalid Choice");
                    break;

            }
        }
    }

    public static void Admin()
    {

        Flush.flush();

        System.out.println("Welcome To The Admin Dashboard\n");
        System.out.println("\nEnter The Username:");
        String username=sc.next();{}
        System.out.println("\nEnter The Password:");
        int password=sc.nextInt();
        if(username.equals("admin")&&password==1234){

            boolean adflag=true;
            while(adflag){
                System.out.println("\nType 1 To Go To: Load\nType 2 To Go To: View\nType 3 To Go To: Exit/n");
                int adchoice=sc.nextInt();
                switch(adchoice)
                {
                    case 1:
                        for(int i=0;i<count.length;){
                            System.out.println("Give The Amount To Load" + amount[i] + " ");
                            int load_money=sc.nextInt();
                            if(load_money+count[i]<=max[i]){
                                count[i]+=load_money;
                                total_amount=total_amount+amount[i]*count[i];
                                i++;
                            }
                            else{
                                System.out.println("Already presented " + count[i]);

                                System.out.println("maximum limit for " + amount[i] + " is " + max[i]);
                                System.out.println("Enter between 0 to " + (max[i] - count[i]));
                            }
                        }
                        break;

                    case 2:
                        for(int i=0;i<count.length;i++){
                            System.out.println(amount[i]+"="+count[i]);
                        }
                        break;

                    case 3:
                        adflag=false;
                        Flush.flush();
                        break;

                    default:
                        System.out.println("Invalid Input");
                        break;


                }
            }
        }
    }
    public static void Users()
    {
        System.out.println("Enter The Username");
        String username=sc.next();
        try{
            for(int i=0;i<3;i++){
                if(user_obj[i].name.equals(username)){
                    currentuser=i;
                }
            }
        }catch (Exception e){
            System.out.println("User Not Found");
        }
        if(currentuser<0){
            System.out.println("User Not Found");
        }
        else{
            boolean uflag=true;
            int attempt=0;
            while(uflag){
                if (attempt > 0) {
                    System.out.println("Wrong pin entered try again");
                    System.out.println("Attempt left " + (3 - attempt));
                }
                System.out.println("enter pin");
                int pass = sc.nextInt();
                if (pass == user_obj[currentuser].pin) {
                    uflag = false;
                    Flush.flush();

                    boolean usertransaction=true;
                    while(usertransaction){
                        System.out.println("Welcome " + user_obj[currentuser].name);
                        System.out.println("1.withdraw\n2.Deposit\n3.mini statement\n4.balance\n5.exit");
                        int operation = sc.nextInt();
                        Flush.flush();
                        switch (operation) {
                            case 1:
                                WithDraw();
                                break;
                            case 2:
                                Deposit();
                                break;
                            case 3:
                                break;
                            case 4:
                                System.out.println("Your balance is " + user_obj[currentuser].amount);
                                break;
                            case 5:
                                usertransaction = false;
                                break;
                            default:
                                System.out.println("Wrong Input try again");
                        }
                    }
                } else {
                    attempt++;
                }
                if (attempt == 3) {
                    System.out.println(user_obj[currentuser].name + " is blocked temporiarily");
                    uflag = false;
                }

            }
        }
    }

    public static void WithDraw() {
        System.out.println("Enter amount to withdraw");
        // System.out.println("Yor balance is "+user1[currentUser].amount);

        int nowwith = sc.nextInt();
        int temp = nowwith;
        if (nowwith >= total_amount && user_obj[currentuser].amount >= nowwith) {
            System.out.println("Enter a lower amount to withdraw");

        } else {
            int possibility[] = { 0, 0, 0, 0 };
            int presentCount[] = count;
            if (nowwith % 10 == 0 && nowwith % 100 == 0) {
                while (nowwith >= 2000 && presentCount[3] > 0) {
                    nowwith -= 2000;
                    presentCount[3]--;
                    possibility[3]++;
                }
                while (nowwith >= 500 && presentCount[2] > 0) {
                    nowwith -= 500;
                    presentCount[2]--;
                    possibility[2]++;
                }
                while (nowwith >= 200 && presentCount[1] > 0) {
                    nowwith -= 200;
                    presentCount[1]--;
                    possibility[1]++;
                }
                while (nowwith >= 100 && presentCount[0] > 0) {
                    nowwith -= 100;
                    presentCount[0]--;
                    possibility[0]++;
                }
                if (nowwith == 0) {
                    count = presentCount;
                    total_amount = total_amount - temp;
                    user_obj[currentuser].amount = user_obj[currentuser].amount - temp;
                } else {
                    System.out.println("Enter a valid combination");
                }
            } else {
                System.out.println("Enter amount in 100's only");
            }
        }
    }

    public static void Deposit() {
        long totalDeposit = 0;
        int notes[] = { 0, 0, 0, 0 };
        for (int i = 0; i < 4;) {
            System.out.println("No of " + amount[i]);
            int m = sc.nextInt();
            if (m + count[i] <= max[i]) {
                notes[i] = count[i] + m;
                totalDeposit+=amount[i]*m;
                i++;
            } else {
                System.out.println("Maximum notes of " + amount[i] + " exceeded");
            }
        }
        user_obj[currentuser].amount += totalDeposit;
        count = notes;
        total_amount += totalDeposit;

    }

}

class User {
    String name;
    int pin, amount;

    public User(String name, int pin, int amount) {
        this.name = name;
        this.pin = pin;
        this.amount = amount;

    }

}
class Flush {
    static void flush() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
