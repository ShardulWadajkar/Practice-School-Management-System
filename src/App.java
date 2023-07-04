import java.util.*;

public class App {
    enum State {
        HOME,
        ADMIN_LOGIN,
        STUDENT_SELECT,
        ADMIN_HOME,
        SECOND_YEAR,
        FINAL_YEAR,
    }

    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    public static void main(String[] args) throws Exception {
        App.clearScreen();

        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        State state = State.HOME;
        int choice;

        while (isRunning) {
            clearScreen();

            switch (state) {
                case HOME:
                    System.out.println("Welcome to the Placement Portal");
                    System.out.println("1. Admin Login");
                    System.out.println("2. Student Login");
                    System.out.println("3. Exit");
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
                        state = State.ADMIN_LOGIN;
                    } else if (choice == 2) {
                        state = State.STUDENT_SELECT;
                    } else if (choice == 3) {
                        isRunning = false;
                    } else {
                        try {
                            System.out.println("Invalid choice");
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                
                case ADMIN_LOGIN:
                    System.out.println("Admin Login");
                    System.out.print("Enter your password: ");
                    String password = sc.next();

                    if (password.equals("p")) {
                        state = State.ADMIN_HOME;
                    } else {
                        try {
                            System.out.println("Invalid password");
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        state = State.HOME;
                    }
                    break;
                
                case STUDENT_SELECT:
                    System.out.println("Student Login");
                    System.out.println("1. Second Year");
                    System.out.println("2. Final Year");
                    System.out.println("3. Back");
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
                        state = State.SECOND_YEAR;
                    } else if (choice == 2) {
                        state = State.FINAL_YEAR;
                    } else if (choice == 3) {
                        state = State.HOME;
                    } else {
                        try {
                            System.out.println("Invalid choice");
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case ADMIN_HOME:
                    Admin admin = new Admin(sc);
                    admin.start();
                    admin.t.join();
                    state = State.HOME;
                    break;
                
                case SECOND_YEAR:
                    System.out.println("Second Year Login");
                    System.out.print("Enter your ID: ");
                    String secondYearId = sc.next();

                    SecondYear secondYear = null;
                    for (SecondYear s : AppData.getSecondYearStudents()) {
                        if (s.getId().equals(secondYearId)) {
                            secondYear = s;
                            break;
                        }
                    }

                    if (secondYear == null) {
                        try {
                            System.out.println("Invalid ID");
                            state = State.STUDENT_SELECT;
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        secondYear.start();
                        secondYear.t.join();
                        state = State.STUDENT_SELECT;
                    }
                    break;

                case FINAL_YEAR:
                    System.out.println("Final Year Login");
                    System.out.print("Enter your ID: ");
                    String finalYearId = sc.next();

                    FinalYear finalYear = null;
                    for (FinalYear s : AppData.getFinalYearStudents()) {
                        if (s.getId().equals(finalYearId)) {
                            finalYear = s;
                            break;
                        }
                    }

                    if (finalYear == null) {
                        try {
                            System.out.println("Invalid ID");
                            state = State.STUDENT_SELECT;
                            Thread.sleep(2000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        finalYear.start();
                        finalYear.t.join();
                        state = State.STUDENT_SELECT;
                    }
                    break;
            }
        }
        
        sc.close();
    }
}
