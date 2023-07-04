import java.util.*;

public class Allocator {
    public static <T extends Student> HashMap<T, Station> allocate(HashMap<T, Station> currentAllotment, ArrayList<T> students) {
        /* 
         * This is the main allocation algorithm. It is a greedy algorithm. It accepts generic types.
         * Depending on parameters passed, it can be used for both second and final year students.
         * 
         * The algorithm is as follows:
         * 1. Sort the students in descending order of their CGPA.
         * 2. For each student, find the highest priority station that he is eligible for.
         * 
         * The algorithm is guaranteed to work because the student with the highest CGPA will be allocated first.
         * Firstly, we have to update the station capacities depending on the current status of students.
         * 
         * 1. If a student has accepted allotment, keep them allotted and don't change capacity.
         * 2. If a student has rejected allotment, decrement capacity of their previously allotted station.
         * 3. If a student has withdrawn, decrement capacity of their previously allotted station (if any).
         */

        HashMap<T, Station> newAllotment = new HashMap<T, Station>();

        /* Clean up old allotment by considering changes in status. */
        for (Map.Entry<T, Station> entry : currentAllotment.entrySet()) {
            T student = entry.getKey();
            Station station = entry.getValue();
            
            if (student.hasAccepted()) {
                /* accepted allotment */
                newAllotment.put(student, station);
            } else if (student.hasRejected()) {
                /* awaiting new allotment */
                if (station != null) {
                    station.decrementOccupied();
                }
            } else if (student.hasWithdrawn()) {
                /* withdrawn */
                if (station != null) {
                    station.decrementOccupied();
                }

                newAllotment.put(student, null);
            } else {
                /* invalid status, withdraw student from process */
                if (station != null) {
                    station.decrementOccupied();
                }
                
                newAllotment.put(student, null);
                student.withdraw();
            }
        }
        
        /* After cleanup, proceed with allotment for students with status 'r'. */
        Collections.sort(students);
    
        for (T student : students) {
            if (student.hasAccepted()) {
                /* accepted students are done */
                continue;
            } else if (student.hasRejected()) {
                if (student.getPreferenceOrder() == null) {
                    /* student has no preference order */
                    newAllotment.put(student, null);
                    continue;
                } else {
                    ArrayList<Station> stations = student.getPreferenceOrder().getOrder();
                    for (Station station : stations) {
                        if (student.isEligible(station) && station.hasVacancy()) {
                            newAllotment.put(student, station);
                            station.incrementOccupied();
                            break;
                        }
                    }
                }

            } else if (student.hasWithdrawn()) {
                /* withdrawn students are done */
                continue;
            } else {
                /* invalid status students are done */
                student.withdraw();
                continue;
            }
        }

        return newAllotment;
    }
}
