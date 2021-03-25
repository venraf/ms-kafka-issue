package my.company.project.service.myfunctionality.common.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CommonDozerMapperConf extends GlobalMapperConf {

    public static final String NAME = "commonMapper";

    public static String COMMON_MAPPER_FOLDER = DOZER_FOLDER + "general/";

    @Qualifier(NAME)
    @Bean(NAME)
    public Mapper getCommonMapper() {
        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        List<String> fileList = new ArrayList<>();

        //for example you can add some common mapping pattern shared accross the service
        //fileList.add(COMMON_MAPPER_FOLDER + "model_2_model.xml");
        //fileList.add(COMMON_MAPPER_FOLDER + "submodel_2_submodel.xml");

        dozerBeanMapper.setMappingFiles(fileList);
        return dozerBeanMapper;
    }

}
