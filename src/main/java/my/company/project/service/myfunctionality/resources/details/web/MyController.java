package my.company.project.service.myfunctionality.resources.details.web;

import my.company.project.service.myfunctionality.resources.details.dto.out.MyResponse;
import io.swagger.annotations.Api;
import my.company.project.common.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping(value = "/${info.api.version}")
@Api(tags = {"MyController"})
@Validated

public class MyController {

    private final Logger log = new Logger(this.getClass());

    @Autowired
    public MyController() {
    }

    @GetMapping(
            value = {"/resources/{id}"},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MyResponse> endpoint(
            @PathVariable String id) {
        log.info("Called!");
        MyResponse myResponse = new MyResponse();
        myResponse.setId(id);
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }
}