package out;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class SourceApplication {
	
	@Autowired
	private SendService service;
	
	@RequestMapping(value = "/send/{name}/{email}", method = RequestMethod.GET)
	public void send(User user){
		List<User> list = new ArrayList<User>();
		list.add(user);
		service.sendMessage(list);
	}

	public static void main(String[] args) {

		SpringApplication.run(SourceApplication.class, args);

	}
	
}
