package dev.danvega.streaming;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin
public class ChatController {

    private final ChatClient chatClient;
    private final ChatMemory chatMemory;

    public ChatController(ChatClient.Builder builder, ChatMemory chatMemory) {
        this.chatClient = builder.build();
        this.chatMemory = chatMemory;
    }

    // 流式SSE接口，兼容ChatGPT风格：每次对话新建SSE连接
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String message, @RequestParam String conversationId) {
        return chatClient.prompt()
                .user(message)
                .advisors(new MessageChatMemoryAdvisor(chatMemory, conversationId, 20))
                .stream()
                .content()
                .concatWith(Flux.just("[DONE]"));
    }

}

