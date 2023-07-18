package cc.bitky.jetbrains.plugin.universalgenerate.pojo;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author bitkylin
 */
@Setter
@Getter
public class ModifierAnnotationWrapper {

    /**
     * 注解
     */
    private ModifierAnnotationEnum annotationEnum;

    /**
     * 注解文本
     */
    private String annotationText;
}
