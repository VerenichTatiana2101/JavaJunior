package org.example.messages;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;


    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
    }

    //метод, отправляющий по socket соединению сообщение
    public void send(Message message) throws IOException {
        synchronized (this.out) {
            out.writeObject(message);
        }
    }

    //метод, принимающий сообщение по socket соединению
    public Message receive() throws IOException, ClassNotFoundException {
        synchronized (this.in) {
            Message message = (Message) in.readObject();
            return message;
        }
    }

    //метод, закрывающий потоки чтения, записи и socket
    @Override
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}