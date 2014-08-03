package pers.ywheel.fsql;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * File-SQL main
 * @author wheel
 *
 */
public class FileSQL {
	public static void main(String[] args) throws Exception {
		BeanFactory factory  = new XmlBeanFactory(new ClassPathResource("spring-config/application.xml"));
		MainTask main = (MainTask) factory.getBean("main");
		main.start();
	}
}
