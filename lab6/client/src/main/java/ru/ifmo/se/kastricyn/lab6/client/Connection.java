package ru.ifmo.se.kastricyn.lab6.client;

import ru.ifmo.se.kastricyn.lab6.lib.ServerAnswer;
import ru.ifmo.se.kastricyn.lab6.lib.ServerRequest;
import ru.ifmo.se.kastricyn.lab6.lib.utility.Console;
import ru.ifmo.se.kastricyn.lab6.lib.utility.Parser;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

//todo properties
public class Connection implements Closeable {
    public static final int MAX_ATTEMPT = 5;
    public static final int MAX_TIMEOUT = 3000;
    public static final int INTERVAL = 5000;

    private InputStream is;
    private OutputStream os;

    public Connection(SocketAddress sa) throws IOException, InterruptedException {
        Socket socket = new Socket();
        for (int i = 0; i < MAX_ATTEMPT; i++) {
            try {
                socket.connect(sa, MAX_TIMEOUT);
            } catch (IOException e) {
                Console.printError("подключение не установлено, слеудущаяя попытка через " + INTERVAL / 1000 + " с.");
            }
            if (socket.isConnected())
                break;
            Thread.sleep(INTERVAL);
        }
        if (!socket.isConnected())
            throw new IOException();
        is = socket.getInputStream();
        os = socket.getOutputStream();
    }

    public Connection(InetAddress ia, int port) throws IOException, InterruptedException {
        this(new InetSocketAddress(ia, port));
    }

    public ServerAnswer sendRequest(ServerRequest request) throws IOException, JAXBException {
            StringWriter sw = new StringWriter();
            Parser.write(sw, ServerRequest.class, request);
            os.write(sw.toString().getBytes(StandardCharsets.UTF_8));
            return getAnswer();
    }

    protected ServerAnswer getAnswer() throws IOException, JAXBException {
            byte[] b = new byte[1024*1024];
            int len = is.read(b);
            return Parser.get(new StringReader(new String(b, 0, len, "UTF-8")), ServerAnswer.class);
    }


    @Override
    public void close() throws IOException {
        is.close();
        os.close();

    }
}