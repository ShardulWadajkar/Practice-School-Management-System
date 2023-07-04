import java.io.*;
import java.util.*;

public class PreferenceOrder {
    private ArrayList<Station> order;

    public PreferenceOrder(File file, HashMap<Integer, Station> stations) {
        /* build preference order from file containing station id */
        this.order = new ArrayList<Station>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextInt()) {
                int stationId = sc.nextInt();

                if (stations.containsKey(stationId)) {
                    this.order.add(stations.get(stationId));
                } else {
                    /* invalid station */
                    continue;
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading preference order from file.");
            e.printStackTrace();
        }
    }

    public void sendToTop(Station station) {
        /* move station to top of preference order */
        this.order.remove(station);
        this.order.add(0, station);
    }

    public void sendToBottom(Station station) {
        /* move station to bottom of preference order */
        this.order.remove(station);
        this.order.add(station);
    }

    public void sendUp(Station station) {
        /* move station up in preference order */
        int index = this.order.indexOf(station);
        if (index == 0) {
            /* station is already at top */
            return;
        }

        this.order.remove(station);
        this.order.add(index - 1, station);
    }

    public void sendDown(Station station) {
        /* move station down in preference order */
        int index = this.order.indexOf(station);
        if (index == this.order.size() - 1) {
            /* station is already at bottom */
            return;
        }

        this.order.remove(station);
        this.order.add(index + 1, station);
    }

    public void sendToN(Station station, int n) {
        /* move station to nth position in preference order */
        if (n < 1 || n > this.order.size()) {
            /* invalid n */
            return;
        }

        this.order.remove(station);
        this.order.add(n - 1, station);
    }

    public String toString() {
        String str = "#\tID\tName\n";

        int i = 1;
        for (Station station : this.order) {
            str += i++ + "\t" + station.getId() + "\t" + station.getName() + "\n";
        }

        return str;
    }

    public ArrayList<Station> getOrder() {
        return this.order;
    }

    public void setOrder(ArrayList<Station> order) {
        this.order = order;
    }

    public void addStation(Station station) {
        this.order.add(station);
    }

    public void addStations(ArrayList<Station> stations) {
        this.order.addAll(stations);
    }
}
