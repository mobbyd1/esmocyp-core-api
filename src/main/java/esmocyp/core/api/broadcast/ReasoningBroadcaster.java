package esmocyp.core.api.broadcast;

import lac.cnclib.net.NodeConnection;
import lac.cnclib.net.NodeConnectionListener;
import lac.cnclib.net.mrudp.MrUdpNodeConnection;
import lac.cnclib.sddl.message.ApplicationMessage;
import lac.cnclib.sddl.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Scope("prototype")
public class ReasoningBroadcaster implements NodeConnectionListener {

    @Value("${contextnet.gateway.ip}")
    private String gatewayIP;

    @Value("${contextnet.gateway.port}")
    private int gatewayPort;

    private MrUdpNodeConnection connection;
    private List<String> uuids = new ArrayList<>();
    private static final UUID appUUID = UUID.randomUUID();

    public ReasoningBroadcaster() {
        InetSocketAddress address = new InetSocketAddress(gatewayIP, gatewayPort);
        try {
            connection = new MrUdpNodeConnection(ReasoningBroadcaster.appUUID);
            connection.addNodeConnectionListener(this);
            connection.connect(address);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadcast(String message) {
        uuids.stream().forEach( uuid -> {
            ApplicationMessage applicationMessage = new ApplicationMessage();
            applicationMessage.setContentObject(message);
            applicationMessage.setRecipientID(UUID.fromString(uuid));

            try {
                connection.sendMessage(applicationMessage);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }

    @Override
    public void connected(NodeConnection nodeConnection) {}

    @Override
    public void reconnected(NodeConnection nodeConnection, SocketAddress socketAddress, boolean b, boolean b1) {}

    @Override
    public void disconnected(NodeConnection nodeConnection) {}

    @Override
    public void newMessageReceived(NodeConnection nodeConnection, Message message) {}

    @Override
    public void unsentMessages(NodeConnection nodeConnection, List<Message> list) {}

    @Override
    public void internalException(NodeConnection nodeConnection, Exception e) {}
}
