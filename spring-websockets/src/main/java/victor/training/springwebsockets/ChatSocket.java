package victor.training.springwebsockets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ChatSocket extends TextWebSocketHandler {

    private Map<String, List<WebSocketSession>> openSessions = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String roomId = extractRoomId(session);
        log.info("Message received ");

        List<WebSocketSession> myRoom = openSessions.get(roomId);
        log.info("There are {} persons in my room", myRoom.size());
        for (WebSocketSession socketSession : myRoom) {
            socketSession.sendMessage(new TextMessage(message.getPayload()));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = extractRoomId(session);
        log.info("Connection established to {}", roomId);
        openSessions.computeIfAbsent(roomId, k -> new ArrayList<>()).add(session);
    }

    private String extractRoomId(WebSocketSession session) {
        String[] arr = session.getUri().toString().split("/");
        return arr[arr.length-1];
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String roomId = extractRoomId(session);
        log.info("Disconnecting {}", roomId);
        openSessions.get(roomId).remove(session);


    }
}
