package com.pucrs;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Slave {
    private static final Logger log = Logger.getLogger(Slave.class.getName());
    protected Configuration slave;
    private final List<Configuration> configurations;
    private LocalTime time;

    public Slave(Configuration slave, List<Configuration> configurations) {
        this.slave = slave;
        this.configurations = configurations;
        this.time = slave.getTime();
        new MulticastReceiver(this, "233.0.0.1", 9000).start();
        new UnicastReceiver(this).start();
    }

    protected void sendTimeToMaster() {
        var master = this.configurations.stream()
                .filter(config -> config.getId() == 0)
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(master)) {
            Socket socket = null;
            OutputStream outputStream = null;
            DataOutputStream dataOutputStream = null;
            try {
                socket = new Socket(master.getHost(), master.getPort());
                outputStream = socket.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF(this.slave.getId() + "|" + this.time.toString());
                System.out.println("SLAVE[" + this.slave.getId() + "]- Send: " + this.time);
                dataOutputStream.flush();
            } catch (IOException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            } finally {
               Utils.closeSocketConnection(outputStream, dataOutputStream, socket);
            }
        }
    }

    protected void fixTime(LocalTime time) {
        this.time = time;
//        this.time = this.time.isAfter(time) ?
//                this.time.minusNanos(time.toNanoOfDay()) :
//                this.time.plusNanos(time.toNanoOfDay());
    }
}
