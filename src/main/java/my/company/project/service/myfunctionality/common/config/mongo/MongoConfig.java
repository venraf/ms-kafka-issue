//package my.company.project.service.acceptance.common.config.mongo;
//
//import com.mongodb.client.MongoClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.config.EnableMongoAuditing;
//
//import javax.annotation.Resource;
//
//@Profile({"TEST", "PRE", "SND", "PRO"})
//@Configuration
//@EnableMongoAuditing
//public class MongoConfig extends AbstractMongoClientConfiguration {
//
//	@Resource(lookup = "java:/comp/env/mongodb/AxMongoPool")
//	MongoClient	mongoClient;
//
//	@Value("${spring.data.mongodb.database}")
//	String			databaseName;
//
//	@Override
//	protected String getDatabaseName() {
//		return databaseName;
//	}
//
//	@Override
//	public MongoClient mongoClient() {
//		return mongoClient;
//	}
//
//}