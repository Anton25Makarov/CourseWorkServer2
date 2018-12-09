package by.bsuir.course.window;

import by.bsuir.course.server.ServerConfigurator;
import by.bsuir.course.server.ServerProperties;
import by.bsuir.course.server.ServerThread;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWindow extends JFrame {
    private static final int ACTIVE_PORT = 8071;
    private InetAddress addr;
    private int backlog;
    private int port;

    private JPanel panel;
    private JButton startButton;
    private JButton endButton;
    private JLabel socketStatus;
    private JLabel activePort;
    private JTextArea connectedList;

    private boolean serverWork;
    private ExecutorService executorService;
    private ServerSocket serverSocket;

    public MainWindow() throws HeadlessException {
        super("Сервер");
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        init();

        Runnable startServer = () -> {
            try {
                setServerParameters();
                serverSocket = new ServerSocket(port, backlog, addr);
                while (serverWork) {
                    System.out.println("wait connection ...");

                    Socket sock = serverSocket.accept();
                    System.out.println(sock.getInetAddress().getHostName() + " connected");
                    connectedList.append("Подключился: " + sock.getInetAddress() +
                            "-" + sock.getPort() + " - " + (new Date()).toString() + "\n");

                    ServerThread server = new ServerThread(sock);
                    server.start();//запуск потока
                }
            } catch (UnknownHostException e) {
                socketStatus.setText("Сокет закрыт");
                JOptionPane.showMessageDialog(this, "Неизвестный номер хоста");
            } catch (SocketException e) {
                socketStatus.setText("Сокет закрыт");
                System.out.println("closed");
            } catch (IOException e) {
                e.getStackTrace();
            }
        };

        startButton.addActionListener(event -> {
            if (!serverWork) {
                executorService = Executors.newSingleThreadExecutor();
                serverWork = true;
                executorService.execute(startServer);
                socketStatus.setText("Сокет открыт");
            }
        });

        endButton.addActionListener(event -> {
            if (serverWork) {
                serverWork = false;
                executorService.shutdown();
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void init() {
        panel = new JPanel();
        panel.setLayout(null);

        startButton = new JButton("Запуск");
        startButton.setLocation(100, 50);
        startButton.setSize(100, 50);

        endButton = new JButton("Стоп");
        endButton.setLocation(250, 50);
        endButton.setSize(100, 50);

        socketStatus = new JLabel("Сокет закрыт");
        socketStatus.setLocation(375, 400);
        socketStatus.setSize(100, 100);
        socketStatus.setBackground(Color.pink);

        activePort = new JLabel("Активный порт: " + ACTIVE_PORT);
        activePort.setLocation(20, 400);
        activePort.setSize(150, 100);
        activePort.setBackground(Color.pink);

        connectedList = new JTextArea();
        connectedList.setLocation(40, 120);
        connectedList.setSize(400, 300);
        connectedList.setEditable(false);

        panel.add(startButton);
        panel.add(endButton);
        panel.add(socketStatus);
        panel.add(activePort);
        panel.add(connectedList);

        add(panel);
    }

    private void setServerParameters() throws UnknownHostException {
        ServerConfigurator configurator = new ServerConfigurator();

        ServerProperties properties = configurator.getProperties();

        addr = InetAddress.getByName(properties.getIp());
        backlog = properties.getBacklog();
        port = properties.getPort();
    }
}
