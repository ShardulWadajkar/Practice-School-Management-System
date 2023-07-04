import java.util.*;
import java.io.*;

public class FinalYear extends Student implements Runnable {
    private enum State {
        HOME,
    }

    public Thread t;
    public Scanner sc;
    private volatile boolean isRunning = true;
    private State state;
    private String resume;

    public FinalYear(File studentFile, File preferenceOrderFile, HashMap<Integer, Station> stations, String resume, Scanner sc) {
        super(studentFile, preferenceOrderFile, stations);
        this.sc = sc;
        this.state = State.HOME;
        this.resume = resume;
    }

    public FinalYear(String name, float cgpa, String id, String branch, ArrayList<String> subjects, String resume, Scanner sc) {
        super(name, cgpa, id, branch, subjects);
        this.sc = sc;
        this.state = State.HOME;
        this.resume = resume;
    }

    public void pressEnterToContinue(Scanner sc) {
        System.out.print("\nPress Enter key to continue...");
        try {
            System.in.read();
            sc.nextLine();
        } catch(Exception e) {
            e.printStackTrace();
        } 
    }

    public void start() {
        this.isRunning = true;
        this.t = new Thread(this, "SecondYear");
        this.t.start();
    }

    public void run() {
        int choice;

        while (this.isRunning) {
            App.clearScreen();

            switch (state) {
                case HOME:
                    System.out.println("Welcome to PS-II portal, " + this.getName());
                    System.out.println("1. View your allocation");
                    System.out.println("2. View your preferences");
                    System.out.println("3. Set your preferences");
                    System.out.println("4. Accept your allocation");
                    System.out.println("5. Withdraw your application");
                    System.out.println("6. Update Resume");
                    System.out.println("7. Logout");
                    System.out.print("Enter your choice: ");

                    try {
                        choice = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Please enter a number");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        }

                        sc.nextLine();
                        break;
                    }

                    if (choice == 1) {
                        App.clearScreen();
                        Station curr = AppData.getFinalYearAllocations().get(this);
                        if (curr == null) {
                            System.out.println("You have not been allocated a station yet");
                        } else {
                            System.out.println("You have been allocated ");
                            System.out.println(curr);
                        }

                        pressEnterToContinue(sc);
                    } else if (choice == 2) {
                        App.clearScreen();
                        if (this.preferenceOrder == null) {
                            System.out.println("You have not set your preferences yet. Please set your preferences from the menu.");
                        } else {
                            System.out.println("Your preferences are: ");
                            System.out.println(this.preferenceOrder);
                        }

                        pressEnterToContinue(sc);
                    } else if (choice == 3) {
                        App.clearScreen();
                        System.out.print("Enter path of file containing your preferences: ");
                        String path = sc.next();
                        File file = new File(path);
                        if (!file.exists()) {
                            System.out.println("File not found.");
                            pressEnterToContinue(sc);
                            break;
                        } else {
                            this.preferenceOrder = new PreferenceOrder(file, AppData.getFinalYearStations());
                            System.out.println("Stations added successfully.");
                            pressEnterToContinue(sc);
                        }
                    } else if (choice == 4) {
                        App.clearScreen();
                        Station curr = AppData.getFinalYearAllocations().get(this);
                        if (curr == null) {
                            System.out.println("You have not been allocated a station yet");
                        } else {
                            System.out.println("You have been allocated ");
                            System.out.println(curr);
                            System.out.print("Do you want to accept this allocation? (y/n) ");
                            String ans = sc.next();
                            if (ans.equals("y")) {
                                this.accept();
                                System.out.println("Allocation accepted successfully.");
                            } else {
                                System.out.println("Allocation not accepted.");
                            }
                        }

                        pressEnterToContinue(sc);
                    } else if (choice == 5) {
                        App.clearScreen();
                        
                        Station curr = AppData.getFinalYearAllocations().get(this);
                        if (curr == null) {
                            System.out.println("You have not been allocated a station yet");
                        } else {
                            System.out.println("You have been allocated ");
                            System.out.println(curr);
                        }

                        System.out.print("Do you want to withdraw from PS II? (y/n) ");
                        String ans = sc.next();
                        if (ans.equals("y")) {
                            System.out.print("Are you sure? This cannot be undone. (y/n) ");
                            ans = sc.next();
                            if (ans.equals("y")) {
                                this.withdraw();
                                HashMap<FinalYear, Station> currentAllocation = AppData.getFinalYearAllocations();
                                currentAllocation.put(this, null);
                                AppData.setFinalYearAllocations(currentAllocation);
                                System.out.println("Withdrawal successful.");
                            } else {
                                System.out.println("Did not withdraw.");
                            }
                        } else {
                            System.out.println("Did not withdraw.");
                        }

                        pressEnterToContinue(sc);
                    } else if (choice == 6) {
                        System.out.print("Enter path of file containing your resume: ");
                        this.resume = sc.next();
                    } else if (choice == 7) {
                        try {
                            System.out.println("Logging out...");
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
        
                        this.stop();
                    } else {
                        try {
                            System.out.println("Invalid choice");
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    public void stop() {
        this.isRunning = false;
    }

    public String getResume() {
        return this.resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }
}
