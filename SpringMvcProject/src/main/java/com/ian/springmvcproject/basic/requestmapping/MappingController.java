package com.ian.springmvcproject.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MappingController {

    /**
     * 기본 요청
     * url pattern: 배열로 제공되어 여러 개 작성 가능
     * HTTP Method 모두 허용 (GET, HEAD, POST, PUT, PATCH, DELETE)
     */
    @RequestMapping({"/hello-basic1", "/hello-basic2"})
    public String helloBasic() {
        log.info("hello basic request");

        return "OK";
    }

    /**
     * method 속성: 특정 HTTP Method만 요청할 수 있도록 제한
     * GET, HEAD, POST, PUT, PATCH, DELETE
     */
    @RequestMapping(value = "/mapping-get-v1", method = RequestMethod.GET)
    public String mappingGetV1() {
        log.info("mapping get v1");

        return "OK";
    }

    /**
     * HTTP Method 관련 축약 애너테이션
     * - @GetMapping
     * - @PostMapping
     * - @PutMapping
     * - @PatchMapping
     * - @DeleteMapping
     */
    @GetMapping("/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping get v2");

        return "OK";
    }

    /**
     * 경로 변수
     * - REST API 설계 시 많이 사용
     * - @PathVariable 애너테이션 사용
     * - 변수 명이 같으면 속성 생략 가능
     * -> @PathVariable("userId") String userId == @PathVariable String userId
     */
    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String userId) {
        log.info("mappingPath: userId = {}", userId);

        return "OK";
    }

    /**
     * PathVariable 다중 매핑
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable String orderId) {
        log.info("mappingPath: userId = {}, orderId = {}", userId, orderId);

        return "OK";
    }

    /**
     * 특정 파라미터 조건 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug"
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug")
    public String mappingParam() {
        log.info("mappingParam");

        return "OK";
    }

    /**
     * 특정 헤더 조건 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug"
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug")
    public String mappingHeader() {
        log.info("mappingHeader");

        return "OK";
    }

    /**
     * 요청 미디어 타입 조건 매핑
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsumes() {
        log.info("mappingConsumes");

        return "OK";
    }

    /**
     * 응답 미디어 타입 조건 매핑
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");

        return "OK";
    }
}
