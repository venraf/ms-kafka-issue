package my.company.project.service.myfunctionality.common.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

public class GlobalMapperConf {
    public static String DOZER_FOLDER = "dozer/";

    public static final String INTERNAL_2_EXTERNAL = "internal_2_external.xml";
    public static final String EXTERNAL_2_INTERNAL = "external_2_internal.xml";
    public static final String INTERNAL_2_INTERNAL = "internal_2_internal.xml";

    protected String operationalMapperFolder;

    public GlobalMapperConf() {
    }

    public GlobalMapperConf(String operationalMapperFolder) {
        this.operationalMapperFolder = DOZER_FOLDER.concat(operationalMapperFolder);
    }

    public Mapper getMapper() {

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        List<String> fileList = new ArrayList<>();

//        fileList.add(operationalMapperFolder + INTERNAL_2_EXTERNAL);
//        fileList.add(operationalMapperFolder + EXTERNAL_2_INTERNAL);
//        fileList.add(operationalMapperFolder + INTERNAL_2_INTERNAL);

        dozerBeanMapper.setMappingFiles(fileList);

        return dozerBeanMapper;
    }
}
