package io.yeahx4.realtimemovement.ws

import com.google.gson.Gson
import io.yeahx4.realtimemovement.dto.Box
import io.yeahx4.realtimemovement.dto.QuitDto
import io.yeahx4.realtimemovement.dto.SyncDto
import io.yeahx4.realtimemovement.dto.UserJoinDto
import io.yeahx4.realtimemovement.util.getRandomColor
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

class WSHandler: TextWebSocketHandler() {
    private val sessions = ConcurrentHashMap.newKeySet<WebSocketSession>()
    private val boxes = ConcurrentHashMap<String, Box>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        println("New Session: ${session.id}")

        val box = Box(getRandomColor(), 0, 0)
        boxes[session.id] = box

        val gson = Gson()
        val syncDto = SyncDto("sync", boxes)
        val json = gson.toJson(syncDto)

        sessions.forEach {
            it.sendMessage(TextMessage(json))
        }
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val payload = message.payload

        // Broadcast
        sessions.forEach {
            it.sendMessage(TextMessage(payload))
        }
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val dto = QuitDto("quit", session.id)
        val gson = Gson()
        val json = gson.toJson(dto)
        sessions.remove(session)
        boxes.remove(session.id)
        println("Session finished: ${session.id}")

        sessions.forEach {
            it.sendMessage(TextMessage(json))
        }
    }
}
