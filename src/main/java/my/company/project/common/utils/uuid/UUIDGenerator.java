package my.company.project.common.utils.uuid;

import java.util.UUID;

public interface UUIDGenerator {

    String DASH = "-";
    String EMPTY_STRING = "";

    static String next() {
        return UUID.randomUUID().toString().replace(DASH, EMPTY_STRING);
    }
}
