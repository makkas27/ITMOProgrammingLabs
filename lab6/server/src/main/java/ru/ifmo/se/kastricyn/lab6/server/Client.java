package ru.ifmo.se.kastricyn.lab6.server;

import ru.ifmo.se.kastricyn.lab6.lib.connection.ServerAnswer;
import ru.ifmo.se.kastricyn.lab6.lib.connection.ServerRequest;
import ru.ifmo.se.kastricyn.lab6.lib.utility.Parser;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;

public class Client {
    //todo: read params from properties

    private SocketChannel sh;
    private Selector selector;

    public Client(ServerSocketChannel ssc, Selector selector) throws IOException {
        sh = ssc.accept();
        if (sh == null)
            return;
        sh.configureBlocking(false);
        this.selector = selector;
        sh.register(selector, SelectionKey.OP_READ, this);
    }


    public void reply(ByteBuffer bf) {
        try {
            write(processing(read(bf)));
        } catch (IOException | JAXBException e){
            System.out.println("Конец соединения");
            try {
                sh.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return;
        }
        if (sh.isConnected()) {
            try {
                sh.register(selector, SelectionKey.OP_READ, this);
            } catch (ClosedChannelException e) {
                e.printStackTrace();
            }
        }
    }

    protected void write(ServerAnswer sa) throws IOException {
        StringWriter sw = new StringWriter();

        Parser.write(sw, ServerAnswer.class, sa);
        sh.write(ByteBuffer.wrap(sw.toString().getBytes(StandardCharsets.UTF_8)));
    }

    protected ServerRequest read(ByteBuffer bf) throws IOException, JAXBException {
        sh.read(bf);
        bf.flip();
        ServerRequest request = Parser.get(new StringReader(new String(bf.array(), bf.position(), bf.remaining(), "UTF-8")), ServerRequest.class);
        bf.clear();
        return request;
    }

    protected  ServerAnswer processing(ServerRequest serverRequest){
        return new ServerAnswer("dsfa" + serverRequest);
    }
}
