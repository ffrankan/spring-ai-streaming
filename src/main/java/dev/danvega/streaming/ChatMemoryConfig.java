package dev.danvega.streaming;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Frank An
 */
@Configuration

public class ChatMemoryConfig {

 @Bean
 public ChatMemory chatMemory() {
        // 创建一个内存聊天记录存储，使用默认的内存实现
        return new InMemoryChatMemory();
 }
}
