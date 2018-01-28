package ${packageName};

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "user-service")
public interface UserServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/v2/users/{userId}", headers = {"Content-Type=application/json"})
    String getUserName(@PathVariable("userId") final String userId) throws Exception;

}