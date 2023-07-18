package cc.bitky.jetbrains.plugin.universalgenerate.constants;

import lombok.Getter;

/**
 * 注解枚举
 *
 * @author bitkylin
 */
public enum ModifierAnnotationEnum {

    /**
     * protostuff
     */
    TAG("Tag", "io.protostuff.Tag"),


    /**
     * swagger2.0
     */
    API("Api", "io.swagger.annotations.Api"),
    API_MODEL("ApiModel", "io.swagger.annotations.ApiModel"),
    API_MODEL_PROPERTY("ApiModelProperty", "io.swagger.annotations.ApiModelProperty"),
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


    ModifierAnnotationEnum(String name, String qualifiedName) {
        this.name = name;
        this.qualifiedName = qualifiedName;
    }
}
