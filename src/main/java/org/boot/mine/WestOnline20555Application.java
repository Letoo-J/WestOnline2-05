package org.boot.mine;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/*启动程序
 * 
 */
//@EnableCaching  //使用缓存
@MapperScan("org.boot.mine.dao") 
@SpringBootApplication
public class WestOnline20555Application {
	
	public static void main(String[] args) {
		SpringApplication.run(WestOnline20555Application.class, args);
	}

}
