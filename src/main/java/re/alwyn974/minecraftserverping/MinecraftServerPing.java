
package re.alwyn974.minecraftserverping;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MinecraftServerPing {
    public MinecraftServerPingInfos getPing(String hostname) throws IOException {
        return getPing(new MinecraftServerPingOptions().setHostname(hostname));
    }

    public MinecraftServerPingInfos getPing(MinecraftServerPingOptions options) throws IOException {
        MinecraftServerPingUtil.validate(options.getHostname(), "Hostname cannot be null");
        MinecraftServerPingUtil.validate(options.getPort(), "Port cannot be null.");

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(options.getHostname(), options.getPort()), options.getTimeout());
            long startTime = System.currentTimeMillis();
            try (DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                sendHandshake(out, options);
                sendStatusRequest(out);
                String json = readStatusResponse(in);
                long now = System.currentTimeMillis();
                sendPing(out, now);
                validatePingResponse(in);
                GsonBuilder gsonBuilder = new GsonBuilder();
                MinecraftServerPingInfos infos = gsonBuilder.create().fromJson(json, MinecraftServerPingInfos.class);
                infos.setLatency(now - startTime);
                return infos;
            }
        }
    }

    private void sendHandshake(DataOutputStream out, MinecraftServerPingOptions options) throws IOException {
        ByteArrayOutputStream handshakeBytes = new ByteArrayOutputStream();
        try (DataOutputStream handshake = new DataOutputStream(handshakeBytes)) {
            handshake.writeByte(MinecraftServerPingUtil.PACKET_HANDSHAKE);
            MinecraftServerPingUtil.writeVarInt(handshake, MinecraftServerPingUtil.PROTOCOL_VERSION);
            MinecraftServerPingUtil.writeVarInt(handshake, options.getHostname().length());
            handshake.writeBytes(options.getHostname());
            handshake.writeShort(options.getPort());
            MinecraftServerPingUtil.writeVarInt(handshake, MinecraftServerPingUtil.STATUS_HANDSHAKE);
            MinecraftServerPingUtil.writeVarInt(out, handshakeBytes.size());
            out.write(handshakeBytes.toByteArray());
        }
    }

    private void sendStatusRequest(DataOutputStream out) throws IOException {
        out.writeByte(0x01); // Size of packet
        out.writeByte(MinecraftServerPingUtil.PACKET_STATUSREQUEST);
    }

    private String readStatusResponse(DataInputStream in) throws IOException {
        MinecraftServerPingUtil.readVarInt(in); // 读取数据包大小
        int id = MinecraftServerPingUtil.readVarInt(in);
        MinecraftServerPingUtil.io(id == -1, "服务器提前结束了数据流。");
        MinecraftServerPingUtil.io(id != MinecraftServerPingUtil.PACKET_STATUSREQUEST, "服务器返回了无效的数据包 ID。");
        int length = MinecraftServerPingUtil.readVarInt(in);
        MinecraftServerPingUtil.io(length == -1, "服务器提前结束了数据流。");
        MinecraftServerPingUtil.io(length == 0, "服务器返回了意外的值。");
        byte[] data = new byte[length];
        in.readFully(data);
        return new String(data, StandardCharsets.UTF_8);
    }


    private void sendPing(DataOutputStream out, long now) throws IOException {
        out.writeByte(0x09); // Size of packet
        out.writeByte(MinecraftServerPingUtil.PACKET_PING);
        out.writeLong(now);
    }

    private void validatePingResponse(DataInputStream in) throws IOException {
        MinecraftServerPingUtil.readVarInt(in); // Size
        int id = MinecraftServerPingUtil.readVarInt(in);
        MinecraftServerPingUtil.io(id == -1, "Server prematurely ended stream.");
        MinecraftServerPingUtil.io(id != MinecraftServerPingUtil.PACKET_PING, "Server returned invalid packet id.");
    }
}
