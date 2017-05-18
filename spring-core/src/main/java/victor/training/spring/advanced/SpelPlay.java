package victor.training.spring.advanced;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelPlay {

	static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config-spel.xml");

	public static void main(String[] args) {
		Object spelSandbox = applicationContext.getBean("spelSandbox");
		System.out.println(spelSandbox.toString());
		
		manualSpELPlay(spelSandbox);
	}

	private static void manualSpELPlay(Object root) {
		ExpressionParser parser = new SpelExpressionParser();
		Expression stringsOver20 = parser.parseExpression("stringList.?[toString() gt '20']");
		System.out.println(stringsOver20.getValue(new StandardEvaluationContext(root)));
	}
	
}
