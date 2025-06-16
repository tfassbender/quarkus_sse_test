package com.riskshield;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseEventSink;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Path("/sse")
public class SseResource {

    @Inject
    Sse sse;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Random random = new Random();

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void stream(@Context SseEventSink sink) {
        executor.execute(() -> {
            try (SseEventSink eventSink = sink) {
				for (int i = 0; i < 10; i++) {
					MyDataObject data = new MyDataObject("event-" + i, new Random().nextInt(100));
					OutboundSseEvent event = sse.newEventBuilder()
							.name("random-json")
							.mediaType(MediaType.APPLICATION_JSON_TYPE)
							.data(MyDataObject.class, data)
							.build();
					sink.send(event);
					TimeUnit.SECONDS.sleep(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
    }
}