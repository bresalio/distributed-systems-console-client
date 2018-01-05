package app;

import java.io.IOException;

import gateway.FilmRatingGateway;
import gateway.UrlPayloadBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@ComponentScan(basePackages = "gateway")
public class Main {
	
	private static ClassPathXmlApplicationContext context;
	
	@Autowired
	private UrlPayloadBuilder urlPayloadBuilder;

	public static void main(String[] args) throws IOException {
		System.out.println("Starting Distributed systems console client program...");
		
		try {
			context = new ClassPathXmlApplicationContext("si-config.xml");
			context.getBean("main", Main.class).startLogic();
		} catch (BeanDefinitionParsingException e) {
			System.out.println("Setting up Distributed systems client program failed:" +
					" Spring was unable to locate NamespaceHandler for an XML Schema namespace." +
					" Make sure that the jar file was generated in a proper way.");
		}
	}
	
	private void startLogic() throws IOException {		
		Logic logic = new Logic();
		logic.setGateway(context.getBean("filmRatingGateway", FilmRatingGateway.class));
		logic.setUrlPayloadBuilder(urlPayloadBuilder);
		logic.executeLogic();
		context.close();
	}

}