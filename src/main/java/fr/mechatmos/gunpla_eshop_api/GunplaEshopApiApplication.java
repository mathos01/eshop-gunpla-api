package fr.mechatmos.gunpla_eshop_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class GunplaEshopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GunplaEshopApiApplication.class, args);
	}


    @GetMapping("/hello")
    public String sayHello() {
        return "hello world";
    }
    @GetMapping("/am-i-a-warthog")
    public String sayYes(){
        return "Yes, of course !";
    }

}
