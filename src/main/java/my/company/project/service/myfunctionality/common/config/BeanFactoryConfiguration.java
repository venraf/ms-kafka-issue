//package my.company.project.service.acceptance.common.config;
//
//import brave.Tracing;
//import brave.context.log4j2.ThreadContextScopeDecorator;
//import brave.propagation.ThreadLocalCurrentTraceContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//
//@Configuration
//public class BeanFactoryConfiguration {
//
//    @Bean
//    PropertySourcesPlaceholderConfigurer getCustomPropertySourcesPlaceholderConfigurer() {
//        return new CustomPropertySourcesPlaceholderConfigurer();
//    }
//
//    @Bean
//    public Tracing getTracing() {
//        Tracing tracing = Tracing.newBuilder()
//                .currentTraceContext(ThreadLocalCurrentTraceContext.newBuilder()
//                        .addScopeDecorator(ThreadContextScopeDecorator.get())
//                        .build()
//                ).build();
//        return tracing;
//    }
//
//
//}
//
//
//
//
