package com.example.demo;

import java.util.ArrayList;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.binance.connector.client.impl.WebsocketClientImpl;

@SpringBootApplication
public class BinanceAppApplication {

	public static void main(String[] args) {
		
		WebsocketClientImpl client = new WebsocketClientImpl(); // defaults to production envrionment unless stated,
		int streamID1 = client.aggTradeStream("btcusdt",((event) -> {
		    System.out.println(event);
		}));

		//Combining Streams
		ArrayList<String> streams = new ArrayList<>();
		streams.add("btcusdt@trade");
		streams.add("bnbusdt@trade");

		int streamID2 = client.combineStreams(streams, ((event) -> {
		    System.out.println(event);
		}));

		//Listening to User Data Stream
		String listenKey = "tgBdHepZY2Uwv423DKGyeoguE2fntJmg1mAelC7sKMLbsypav1D0No3LfZgrlGeZ";
		int streamID3 = client.listenUserStream(listenKey, ((event) -> {
		  System.out.println(event);
		}));

		//Closing a single stream
		client.closeConnection(streamID1); //closes aggTradeStream-btcusdt
		        
		//Closing all streams
		client.closeAllConnections();
		
		SpringApplication.run(BinanceAppApplication.class, args);
	}

}
