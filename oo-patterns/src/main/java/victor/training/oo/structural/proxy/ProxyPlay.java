package victor.training.oo.structural.proxy;

import java.lang.reflect.Proxy;

import victor.training.oo.structural.decorator.RealSubject;
import victor.training.oo.structural.decorator.Subject;
import victor.training.oo.webservice.SimpleWs;
import victor.training.oo.webservice.SimpleWs_Service;

public class ProxyPlay {

	public static void main(String[] args) throws Exception {
		consumeWebServiceThroughProxy();

		playWithDynamicProxy();
	}
	

	private static void consumeWebServiceThroughProxy() {
		SimpleWs_Service service = new SimpleWs_Service();
		SimpleWs port = service.getSimpleWsSOAP();
		System.out.println("Using a Dynamic Proxy: <<<" + port + ">>>");
		String response = port.echo("my message");
		System.out.println("Received response from Web Service: '" + response + "'");
	}

	private static void playWithDynamicProxy() {
		Subject realSubject = new RealSubject();

		DynamicProxyInvocationHandler invocationHandler = new DynamicProxyInvocationHandler(realSubject);

		// dynamically created interface implementation
		Subject proxy = (Subject) Proxy.newProxyInstance(ProxyPlay.class.getClassLoader(),
				new Class<?>[] { Subject.class }, invocationHandler);

		codClient(proxy);
	}

	private static void codClient(Subject subject) {
		System.out.println("Cod client: Inainte de a apela metoda A");
		subject.methodA();
		System.out.println("Cod client: Dupa apelarea metodei A");
	}

}
