package cc.bitky.jetbrains.plugin.universalgenerate.constants;

import lombok.Getter;

import java.util.List;

/**
 * 注解枚举
 *
 * @author bitkylin
 */
public enum ModifierAnnotationEnum {

    /**
     * protostuff
     */
    TAG("Tag", "io.protostuff.Tag", List.of("value")),


    /**
     * swagger2.0
     */
    API("Api", "io.swagger.annotations.Api", List.of("tags", "value")),
    API_OPERATION("ApiOperation", "io.swagger.annotations.ApiOperation", List.of("value", "notes")),
    API_MODEL("ApiModel", "io.swagger.annotations.ApiModel", List.of("description", "value")),
    API_MODEL_PROPERTY("ApiModelProperty", "io.swagger.annotations.ApiModelProperty", List.of("value", "name")),
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
    private final List<String> attributeNameList;


    ModifierAnnotationEnum(String name, String qualifiedName, List<String> attributeNameList) {
        this.name = name;
        this.qualifiedName = qualifiedName;
        this.attributeNameList = attributeNameList;
    }
}
