package controller;

import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.binance.connector.client.impl.WebsocketClientImpl;

import config.PrivateConfig;

@Controller
@ServerEndpoint("/Api")
public class BinanceAppController {
	
	/**
     * 웹소켓 세션을 담는 ArrayList
     */
    private static ArrayList<Session> sessionList = new ArrayList<Session>();
    WebsocketClientImpl client = new WebsocketClientImpl(); 
    
	@MessageMapping("/chart/List") 
	public class chartList {
		
		ArrayList<String> streams = new ArrayList<>();
		
		streams.add("btcusdt@trade");
		
		client.klineStream("btcusdt", "h", ((event) -> {
	            System.out.println(event);
	            client.closeAllConnections();
	     }));
	    
		SpringApplication.run(BinanceAppApplication.class, args);
	}

	@OnClose
	public void clientClose(Session userClose) {
		client.closeAllConnections();
	}
	
	
	public static void main(String[] args) {
		
		WebsocketClientImpl client = new WebsocketClientImpl(); // defaults to production envrionment unless stated,
		
		int streamID1 = client.aggTradeStream("btcusdt",((event) -> {
		    System.out.println(event);
		}));

		//Combining Streams
		ArrayList<String> streams = new ArrayList<>();
		streams.add("btcusdt@trade");
		 client.klineStream("btcusdt", "h", ((event) -> {
	            System.out.println(event);
	            client.closeAllConnections();
	     }));

		//SpringApplication.run(BinanceAppApplication.class, args);
	}

}
