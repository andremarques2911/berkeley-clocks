//package com.pucrs;
//
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.Socket;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.Objects;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class UnicastSender {
//    private static final Logger log = Logger.getLogger(UnicastSender.class.getName());
//    private Configuration config;
//
//    public UnicastSender(Configuration config) {
//        this.config = config;
//    }
//
//    private void send() {
//        Socket socket = null;
//        OutputStream outputStream = null;
//        DataOutputStream dataOutputStream = null;
//        try {
//            socket = new Socket(master.getHost(), master.getPort());
//            outputStream = socket.getOutputStream();
//            dataOutputStream = new DataOutputStream(outputStream);
//            dataOutputStream.writeUTF(this.slave.getId() + "|" + this.time.toString());
//            System.out.println("SLAVE[" + this.slave.getId() + "]- Send: " + this.time);
//            dataOutputStream.flush();
//        } catch (IOException e) {
//            log.log(Level.SEVERE, e.getMessage(), e);
//        } finally {
//           Utils.closeSocketConnection(outputStream, dataOutputStream, socket);
//        }
//    }
//}
