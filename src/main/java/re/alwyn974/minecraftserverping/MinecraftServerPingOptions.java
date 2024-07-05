
package re.alwyn974.minecraftserverping;

public class MinecraftServerPingOptions {
    private String hostname;
    private int port = 25565;
    private int timeout = 2000;
    private String charset = "UTF-8";

    public MinecraftServerPingOptions setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public MinecraftServerPingOptions setPort(int port) {
        this.port = port;
        return this;
    }

    public MinecraftServerPingOptions setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public MinecraftServerPingOptions setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getCharset() {
        return charset;
    }
}
