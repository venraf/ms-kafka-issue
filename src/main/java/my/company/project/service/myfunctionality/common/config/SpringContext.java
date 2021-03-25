package my.company.project.service.myfunctionality.common.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static BeanFactory beanFactory;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
        SpringContext.beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * Get bean_old of type "type"
     *
     * @param type
     * @param <T>
     * @return the bean_old
     */
    public static <T> T getBean(Class<T> type) {
        return SpringContext.applicationContext.getBean(type);
    }

    /**
     * Get bean_old of type "type" and qualifier "qualifier"
     *
     * @param type
     * @param qualifier
     * @param <T>
     * @return the bean_old
     */
    public static <T> T getBean(Class<T> type, String qualifier) {
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(SpringContext.beanFactory, type, qualifier);
    }

}