package cc.bitky.jetbrains.plugin.universalgenerate.util;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.ModifierAnnotationEnum;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.ModifierAnnotationWrapper;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author bitkylin
 */
public final class ModifierAnnotationUtils {

    private ModifierAnnotationUtils() {
    }

    public static ModifierAnnotationWrapper createWrapperApi(String tag) {
        return createModifierAnnotationWrapper(ModifierAnnotationEnum.API, "@%s(tags = {\"%s\"})", tag);
    }

    public static ModifierAnnotationWrapper createWrapperTag(int value) {
        return createModifierAnnotationWrapper(ModifierAnnotationEnum.TAG, "@%s(%s)", value);
    }

    public static ModifierAnnotationWrapper createWrapperApiModelProperty(String value) {
        return createModifierAnnotationWrapper(ModifierAnnotationEnum.API_MODEL_PROPERTY, "@%s(value=\"%s\")", value);
    }

    public static ModifierAnnotationWrapper createWrapperApiModel(String description) {
        return createModifierAnnotationWrapper(ModifierAnnotationEnum.API_MODEL, "@%s(description = \"%s\")", description);
    }

    private static ModifierAnnotationWrapper createModifierAnnotationWrapper(ModifierAnnotationEnum annotationEnum,
                                                                             String annotationTemplate,
                                                                             Object... args) {
        String annotationText = formatAnnotationText(annotationEnum, annotationTemplate, args);
        ModifierAnnotationWrapper modifierAnnotationWrapper = new ModifierAnnotationWrapper();
        modifierAnnotationWrapper.setAnnotationEnum(annotationEnum);
        modifierAnnotationWrapper.setAnnotationText(annotationText);
        return modifierAnnotationWrapper;
    }

    private static String formatAnnotationText(ModifierAnnotationEnum annotationEnum,
                                               String annotationTemplate,
                                               Object... args) {
        Object[] modifiedArgs = ArrayUtils.addFirst(args, annotationEnum.getName());
        return String.format(annotationTemplate, modifiedArgs);
    }
}
