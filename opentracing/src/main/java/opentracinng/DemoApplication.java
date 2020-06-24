package opentracinng;

import io.jaegertracing.Configuration;
import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DemoApplication {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.build();
	}

	@Bean
	public io.opentracing.Tracer initTracer() {
		SamplerConfiguration samplerConfig = new SamplerConfiguration().withType("const").withParam(1);
		ReporterConfiguration reporterConfig = new ReporterConfiguration().withLogSpans(true);
		return new Configuration("demo-app-subscription").withSampler(samplerConfig).withReporter(reporterConfig).getTracer();
	}

	public static void main(String[] args) {
		System.setProperty("server.port", "8090");
		SpringApplication.run(DemoApplication.class, args);
	}

}
