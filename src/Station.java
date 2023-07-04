import java.util.*;

public class Station {
    private int id;
    private String name;
    private int capacity;
    private int occupied;
    private ArrayList<String> compulsorySubjects;
    private ArrayList<String> branches;
    private int stipend;
    private String location;

    public Station(int id, String name, int capacity, ArrayList<String> compulsorySubjects, ArrayList<String> branches, int stipend, String location) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.occupied = 0;
        this.compulsorySubjects = compulsorySubjects;
        this.branches = branches;
        this.stipend = stipend;
        this.location = location;
    }

    public Station(String line) {
        // This constructor is used to create a Station object from a line of text
        // format of the line: id,name,capacity,<compulsorySubjects>,branches,stipend,location

        try {
            String[] data = line.split(",");
    
            this.id = Integer.parseInt(data[0]);
            this.name = data[1];
            this.capacity = Integer.parseInt(data[2]);
            this.occupied = 0;
            this.compulsorySubjects = new ArrayList<String>();
            for (int i = 3; i < data.length - 3; i++) {
                this.compulsorySubjects.add(data[i]);
            }
    
            this.branches = new ArrayList<String>();
            String[] branchData = data[data.length - 3].split(" ");
            for (int i = 0; i < branchData.length; i++) {
                this.branches.add(branchData[i]);
            }

            this.stipend = Integer.parseInt(data[data.length - 2]);
            this.location = data[data.length - 1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error occured while parsing line in Station constructor.");
            e.printStackTrace();
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getOccupied() {
        return this.occupied;
    }

    public boolean hasVacancy() {
        return this.occupied < this.capacity;
    }

    public void incrementOccupied() {
        this.occupied++;
    }

    public void decrementOccupied() {
        this.occupied--;
    }

    public ArrayList<String> getCompulsorySubjects() {
        return this.compulsorySubjects;
    }

    public ArrayList<String> getBranches() {
        return this.branches;
    }

    public int getStipend() {
        return this.stipend;
    }

    public String getLocation() {
        return this.location;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOccupied(int occupied) {
        this.occupied = occupied;
    }

    public void setCompulsorySubjects(ArrayList<String> compulsorySubjects) {
        this.compulsorySubjects = compulsorySubjects;
    }

    public void setBranches(ArrayList<String> branches) {
        this.branches = branches;
    }

    public void setStipend(int stipend) {
        this.stipend = stipend;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString() {
        return "Station " + this.id + " : " + this.name + " (" + this.location + ") " + "\nCompulsory Subjects = " + this.compulsorySubjects + "\nEligible Branches = " + this.branches + "\nStipend = Rs. " + this.stipend;
    }
}
