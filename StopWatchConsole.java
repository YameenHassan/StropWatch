import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Yameen on 1/22/2019.
 */
public class StopWatchConsole extends JFrame {
    public long start = System.currentTimeMillis();
    public long lap;
    public Integer counter = 0;
    public Map<Integer, Long> timeMap = new HashMap<>();

    StopWatchConsole() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Integer keyCode = e.getKeyCode();
                key(keyCode);
            }
        });
    }

    public void key(Integer keyCode) {
        if (keyCode == 32) {
            lap();
        }
    }

    public void lap() {
        long currentTime = System.currentTimeMillis();
        lap = currentTime - start;
        timeMap.put(counter, lap);
        counter++;
    }

    public void showMap() {
        for (Integer count : timeMap.keySet()) {
            System.out.println(setFormat(timeMap.get(count)));
        }
    }

    public String setFormat(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(time);
    }


    public static void main(String args[]) {
        StopWatchConsole f = new StopWatchConsole();

        SwingUtilities.invokeLater(() -> {
            f.setFocusable(true);
            f.setVisible(true);
        });
        ShowMap show = new ShowMap(f);
        Runtime.getRuntime().addShutdownHook(show);
    }
}

class ShowMap extends Thread {
    StopWatchConsole stp;
    public ShowMap(StopWatchConsole sw){
        stp = sw;
    }

    public void run() {
        stp.showMap();
        System.out.println("Program Terminated.");
    }
}
