package out;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.List;
import java.util.Random;

@EnableBinding(Sink.class)
public class MsgSink {
	@StreamListener(Sink.INPUT)
	public void messageSink(String payload) {
		Random random = new Random();
//		int i = random.nextInt(2);
//		int a = 1/0;
//		int i1 = random.nextInt(2);
//		int a1 = 1/i1;
//		int i2 = random.nextInt(2);
//		int a2 = 1/i2;
//		opsForValue.set("aaaaaaaaaa-" + UUID.randomUUID() + payload.get(0).getName(),payload.toString());
		System.out.println("Received: ------------------------->" + payload);
	}
	
//	@StreamListener(Sink.INPUT)
//	public void receiver(Message<Object> message) {
//		Object obj = message.getPayload();
//		System.out.println("接受对象:" + obj);
//	}

}
