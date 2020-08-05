package pl.wojciech.sw_task.spock

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import pl.wojciech.sw_task.character.CharacterController
import spock.lang.Specification
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.http.HttpStatus.*

@SpringBootTest
@AutoConfigureMockMvc
public class SpockApplicationTests extends Specification {

    @Autowired(required = false)
    private CharacterController characterController

    @Autowired
    private MockMvc mvc

    def "when context is loaded then all expected beans are created"() {
        expect: "the CharacterController is created"
        characterController
    }

    def "when get character is performed then the response has status 200 and content is not null"() {
        expect: "Status is 200 and the response is not null"
        when:
        def response = mvc.perform(get("/characters/1"))
                .andReturn()
                .response
        def content = new JsonSlurper().parseText(response.contentAsString)
        then:
        response.status == OK.value()
        content != null
        content.name != null
    }

    def "when get wrong character is performed then the response has status 404 and validated response received"() {
        expect: "Status is 404 and the response is not null"
        when:
        def response = mvc.perform(get("/characters/0"))
                .andReturn()
                .response
        def content = new JsonSlurper().parseText(response.contentAsString)
        then:
        response.status == NOT_FOUND.value()
        content != null
        content.detail == 'Not found'
    }

    def "when get characters page is performed then the response has status 200 and content is not null"() {
        expect: "Status is 200 and the response is not null"
        when:
        def response = mvc.perform(get("/characters?page=1"))
                .andReturn()
                .response
        def content = new JsonSlurper().parseText(response.contentAsString)
        then:
        response.status == OK.value()
        content.results != null
        content.results[0].name != null
    }

    def "when get characters page wrong is performed then the response has status 404 and validated response received"() {
        expect: "Status is 404 and the response is not null"
        when:
        def response = mvc.perform(get("/characters?page=0"))
                .andReturn()
                .response
        def content = new JsonSlurper().parseText(response.contentAsString)
        then:
        response.status == NOT_FOUND.value()
        content != null
        content.detail == 'Not found'
    }

}
