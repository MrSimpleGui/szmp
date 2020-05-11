package com.webdrp;

import com.google.common.base.Predicate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;

@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@EnableCaching
@EnableScheduling  //开启定时任务
@MapperScan("com.webdrp.mapper")
public class WebdrpApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebdrpApplication.class, args);
	}


	/**
	 * api文档配置   localhost:8080/swagger-ui.html
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
			@Override
			public boolean apply(RequestHandler requestHandler) {
				Class<?> declearingClass = requestHandler.declaringClass();
				if (declearingClass == BasicErrorController.class){
					return false;
				}

				if (requestHandler.isAnnotatedWith(ResponseBody.class)){
					return true;
				}

				if (requestHandler.isAnnotatedWith(RestController.class)){
					return true;
				}

				if (requestHandler.isAnnotatedWith(PostMapping.class)){
					return true;
				}

				if (requestHandler.isAnnotatedWith(GetMapping.class)){
					return true;
				}

				return false;
			}
		};
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(predicate)
				.paths(PathSelectors.any())
				.build()
				//线上文档
//				.host("test.xiaoye0724.cn")
				;
	}
	/**
	 * 文档的部分描述
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				//标题
				.title("webdrp 系统接口文档 v1.0")
				//描述
				.description("更多相关内容请联系：mail")
				//版本
				.version("1.0")
				.build();
	}
}
