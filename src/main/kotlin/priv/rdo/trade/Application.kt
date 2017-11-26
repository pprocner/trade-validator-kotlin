package priv.rdo.trade

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
@EnableMetrics(proxyTargetClass = true)
class Application : MetricsConfigurerAdapter()

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
