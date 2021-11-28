package com.pucrs;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
//        args = new String[]{"1", "2", "C:\\Users\\andre.marques\\Desktop\\config.txt"};
        if (args.length != 3) {
            System.out.println("java -jar Berkeley <process-id><path-to-configuration-file>.txt");
            return;
        }
        var id = Integer.parseInt(args[0]);
        var totalSlaves = Integer.parseInt(args[1]);
        try {
            var configurations = Files.lines(Paths.get(args[2]))
                    .map(Configuration::new)
                    .collect(Collectors.toList());

            var configuration = configurations.stream()
                    .filter(config -> config.getId() == id)
                    .findFirst()
                    .orElseThrow();

            if (id == 0)
                new Master(configuration, totalSlaves, configurations);
            else
                new Slave(configuration, configurations);

        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
