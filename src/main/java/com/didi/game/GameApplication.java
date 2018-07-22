package com.didi.game;

import com.didi.game.configuration.BaseConfiguration;
import com.didi.game.configuration.WebApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author zy
 */
@SpringBootApplication
@MapperScan({"com.didi.game.dao"})
@Import({BaseConfiguration.class, WebApplication.class})
public class GameApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameApplication.class, args);
	}

}
