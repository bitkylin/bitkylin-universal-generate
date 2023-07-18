package cc.bitky.jetbrains.plugin.universalgenerate.constants;

import lombok.Getter;

/**
 * @author bitkylin
 */
public enum DecisionAnnotationEnum {

    /**
     * protostuff
     */
    SERVICE("Service","org.springframework.stereotype.Service"),


    /**
     * swagger2.0
     */
    API("Api","io.swagger.annotations.Api"),

    API_MODEL("ApiModel","io.swagger.annotations.ApiModel"),

    API_MODEL_PROPERTY("ApiModelProperty","io.swagger.annotations.ApiModelProperty"),
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
