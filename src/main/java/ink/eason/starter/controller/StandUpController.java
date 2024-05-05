package ink.eason.starter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/stand-up")
public class StandUpController {

    private static final Logger logger = LoggerFactory.getLogger(StandUpController.class);

    private final String url = "http://127.0.0.1:7040";

    private LocalDateTime lastStandUpTime = LocalDateTime.now();

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

    private int workingMinutes = 40;

    public StandUpController() {

        scheduler.scheduleAtFixedRate(() -> {

            if (matchStandUpTime()) {
                openUrl(url);
                scheduler.schedule(() -> {
                    if (matchStandUpTime()) {
                        lastStandUpTime = LocalDateTime.now();
                        lockScreen();
                    }
                }, 30, TimeUnit.SECONDS);
            }

        }, 0, 1, TimeUnit.MINUTES);

    }

    private boolean matchStandUpTime() {
        return lastStandUpTime.plusMinutes(workingMinutes).isBefore(LocalDateTime.now());
    }

    @GetMapping
    public String setStandup(@RequestParam(name = "minutes", required = false) int minutes) {

        if (minutes <= 0) {
            workingMinutes = 60 * 24 * 365;
        } else {
            workingMinutes = minutes;
        }


        lastStandUpTime = LocalDateTime.now();

        return "ok";
    }

    @GetMapping("/{ack}")
    public String ack(@PathVariable("ack") String ack) {
        if (Objects.equals("yes", ack)) {
            lockScreen();
        }
        lastStandUpTime = LocalDateTime.now();
        return "ok";
    }


    public void openUrl(String url) {

        String osName = System.getProperty("os.name").toLowerCase();

        ProcessBuilder processBuilder;

        try {
            if (osName.contains("win")) {
                // Windows
                processBuilder = new ProcessBuilder("cmd", "/c", "start", url);
            } else if (osName.contains("mac")) {
                // macOS
                processBuilder = new ProcessBuilder("open", url);
            } else if (osName.contains("nix") || osName.contains("nux")) {
                // Linux (KDE6)
                processBuilder = new ProcessBuilder("xdg-open", url);
            } else {
                throw new UnsupportedOperationException("Unsupported operating system: " + osName);
            }

            processBuilder.start();
        } catch (IOException e) {
            logger.error("openUrl error", e);
        }
    }

    public void lockScreen() {
        String osName = System.getProperty("os.name").toLowerCase();

        ProcessBuilder processBuilder;

        try {
            if (osName.contains("win")) {
                processBuilder = new ProcessBuilder("rundll32.exe", "user32.dll,LockWorkStation");
            } else if (osName.contains("mac")) {
                processBuilder = new ProcessBuilder("/System/Library/CoreServices/Menu Extras/User.menu/Contents/Resources/CGSession", "-suspend");
            } else if (osName.contains("nix") || osName.contains("nux")) {
                processBuilder = new ProcessBuilder("qdbus", "org.kde.screensaver", "/ScreenSaver", "Lock");
            } else {
                throw new UnsupportedOperationException("Unsupported operating system: " + osName);
            }

            processBuilder.start();
        } catch (Exception e) {
            logger.info("lockScreen error", e);
        }
    }


}
