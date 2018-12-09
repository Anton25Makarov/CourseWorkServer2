package by.bsuir.course.server;

public class ServerProperties {
    private int port;
    private String ip;
    private int backlog;

    public ServerProperties(int port, String ip, int backlog) {
        this.port = port;
        this.ip = ip;
        this.backlog = backlog;
    }

    public ServerProperties() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }
}
