package main;


import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import main.filters.AuthFilter;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class CouponSystemServerApplication {
	
	private @Autowired AutowireCapableBeanFactory beanFactory;
	
	public static void main(String[] args) {
		SpringApplication.run(CouponSystemServerApplication.class, args);
		
	}
	
	@Bean
	public FilterRegistrationBean AuthRequiredFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		Filter authRequiredFilter = new AuthFilter();
		beanFactory.autowireBean(authRequiredFilter);
		registration.setFilter(authRequiredFilter);
		registration.addUrlPatterns("/admin/*");
		registration.addUrlPatterns("/company/*");
		registration.addUrlPatterns("/customer/*");
		return registration;
	}
	
}
