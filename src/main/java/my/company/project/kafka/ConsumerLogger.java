package my.company.project.kafka;

import my.company.project.logging.MyLogger;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ConsumerLogger {


	private final MyLogger log = new MyLogger(this.getClass());
	@Around("@annotation(EventLogger)")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();

		log.info("JoinPoint args: {}", joinPoint.getArgs());
		if (joinPoint.getArgs().length == 1) {
			if (joinPoint.getArgs()[0] instanceof ConsumerRecord<?, ?>) {
				ConsumerRecord<?, ?> record = (ConsumerRecord<?, ?>)joinPoint.getArgs()[0];
				log.info("logged record: {}", record.value());
			}
		}

		Object proceed = joinPoint.proceed();

		long executionTime = System.currentTimeMillis() - start;
		log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");

		return proceed;
	}
}
