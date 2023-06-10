package io.yeahx4.realtimemovement.ws

import org.json.JSONObject
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

class WSHandler: TextWebSocketHandler() {
    private val sessions = ConcurrentHashMap.newKeySet<WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)

        sessions.forEach {
            it.sendMessage(TextMessage("New connection: ${session.id}"))
        }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload
        val json = JSONObject(payload)

        sessions.forEach {
            it.sendMessage(TextMessage("Message: ${json.get("message")}"))
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
    }
}
