package my.company.project.starter.main.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "company.swagger.springfox")
public class SwaggerSpringFoxProperties {

    private boolean enableCustomPlugin;

    public boolean isEnableCustomPlugin() {
        return enableCustomPlugin;
    }

    public void setEnableCustomPlugin(boolean enableCustomPlugin) {
        this.enableCustomPlugin = enableCustomPlugin;
    }
}
