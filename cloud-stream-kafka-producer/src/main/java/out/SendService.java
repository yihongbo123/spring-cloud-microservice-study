package out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;

@EnableBinding(Source.class)
public class SendService {

	@Autowired
	private Source source;

	public void sendMessage(List<User> users) {

		try {

			source.output().send(MessageBuilder.withPayload(users).build());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
