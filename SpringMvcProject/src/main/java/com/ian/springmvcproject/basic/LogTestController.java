package com.ian.springmvcproject.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * - @Controller: 반환 값이 ViewName으로 인식됨
 * - @RestController: 반환 값이 데이터 그 자체로, HttpBody에 바로 입력됨
 * -> @Controller + @ResponseBody
 */
@RestController
@Slf4j
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("log-test")
    private String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        /**
         * ※ log 작성 시 "data = {}", data와 같은 모양으로 작성해야 함
         * + 연산자를 사용 시 출력하지 않는 레벨의 로그여도 더하기 연산이 먼저 발생하여 메모리가 사용됨
         */
        log.trace("trace log: name = {}", name);
        log.debug("debug log: name = {}", name);
        log.info("info log: name = {} ", name);
        log.warn("warn log: name = {}", name);
        log.error("error log: name = {}", name);

        return "OK";
    }
}
