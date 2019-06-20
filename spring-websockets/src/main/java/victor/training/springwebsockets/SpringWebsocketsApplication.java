package victor.training.springwebsockets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.messaging.DefaultSimpUserRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@SpringBootApplication
@EnableWebSocket
public class SpringWebsocketsApplication implements WebSocketConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebsocketsApplication.class, args);
	}


	@Autowired
	private ChatSocket chatSocket;
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(chatSocket, "/chat/*")
				.addInterceptors(new HttpSessionHandshakeInterceptor());
	}

	@Bean
	public DefaultSimpUserRegistry userRegistry() {
		return new DefaultSimpUserRegistry();
	}
}
