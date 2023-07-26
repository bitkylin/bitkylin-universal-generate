package cc.bitky.jetbrains.plugin.universalgenerate.constants;

import lombok.Getter;

/**
 * @author bitkylin
 */
public enum DecisionAnnotationEnum {

    CONTROLLER("Controller", "org.springframework.stereotype.Controller"),
    REST_CONTROLLER("RestController", "org.springframework.web.bind.annotation.RestController"),
    SERVICE("Service", "org.springframework.stereotype.Service"),
    COMPONENT("Component", "org.springframework.stereotype.Component"),
    REPOSITORY("Repository", "org.springframework.stereotype.Repository"),


    DATA("Data", "lombok.Data"),
    GETTER("Getter", "lombok.Getter"),
    SETTER("Setter", "lombok.Setter"),
    VALUE("Value", "lombok.Value"),

    ;

    /**
     * 注解名称
     */
    @Getter
    private final String name;

    /**
     * 注解全限定名
     */
    @Getter
    private final String qualifiedName;


    DecisionAnnotationEnum(String name, String qualifiedName) {
        this.name = name;
        this.qualifiedName = qualifiedName;
    }
}
