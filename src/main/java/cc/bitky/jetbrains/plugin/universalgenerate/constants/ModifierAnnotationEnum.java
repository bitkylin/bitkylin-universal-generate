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
    TAG("Tag", "io.protostuff.Tag", "value"),


    /**
     * swagger2.0
     */
    API("Api", "io.swagger.annotations.Api", "tags"),
    API_OPERATION("ApiOperation", "io.swagger.annotations.ApiOperation", "value"),
    API_MODEL("ApiModel", "io.swagger.annotations.ApiModel", "description"),
    API_MODEL_PROPERTY("ApiModelProperty", "io.swagger.annotations.ApiModelProperty", "value"),
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

    /**
     * 主要的属性名称
     */
    @Getter
    private final String primaryAttributeName;


    ModifierAnnotationEnum(String name, String qualifiedName, String primaryAttributeName) {
        this.name = name;
        this.qualifiedName = qualifiedName;
        this.primaryAttributeName = primaryAttributeName;
    }
}
