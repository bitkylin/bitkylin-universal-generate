package cc.bitky.jetbrains.plugin.universalgenerate.factory;

import cc.bitky.jetbrains.plugin.universalgenerate.action.action.*;
import cc.bitky.jetbrains.plugin.universalgenerate.action.action.base.AbstractUniversalGenerateAction;
import cc.bitky.jetbrains.plugin.universalgenerate.config.localization.ActionLocalizationConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;

/**
 * @author bitkylin
 */
public class ActionFactory {

    public static AbstractUniversalGenerateAction create(ActionLocalizationConfig actionLocalizationConfig, ActionEnum actionEnum) {
        String titleText = actionLocalizationConfig.fetchActionTitle(actionEnum);
        return switch (actionEnum) {
            case RE_GENERATE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT -> new ReGenerateSwaggerToJavaDocForElementAction(titleText);
            case RE_GENERATE_SWAGGER_TO_JAVA_DOC_FOR_FILE -> new ReGenerateSwaggerToJavaDocForFileAction(titleText);
            case POPULATE_MISSING_ANNOTATION_FOR_ELEMENT -> new PopulateMissingAnnotationForElementAction(titleText);
            case POPULATE_MISSING_ANNOTATION_FOR_FILE -> new PopulateMissingAnnotationForFileAction(titleText);
            case POPULATE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT -> new PopulateSwaggerToJavaDocForElementAction(titleText);
            case POPULATE_SWAGGER_TO_JAVA_DOC_FOR_FILE -> new PopulateSwaggerToJavaDocForFileAction(titleText);
            case RE_GENERATE_ANNOTATION_FOR_ELEMENT -> new ReGenerateAnnotationForElementAction(titleText);
            case RE_GENERATE_ANNOTATION_FOR_FILE -> new ReGenerateAnnotationForFileAction(titleText);
            case POPULATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_ELEMENT -> new PopulateElementNameToJavaDocForElementAction(titleText);
            case POPULATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_FILE -> new PopulateElementNameToJavaDocForFileAction(titleText);
            case RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_ELEMENT -> new ReGenerateElementNameToJavaDocForElementAction(titleText);
            case RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC_FOR_FILE -> new ReGenerateElementNameToJavaDocForFileAction(titleText);
            case DELETE_ANNOTATION_SWAGGER_FOR_ELEMENT -> new DeleteElementAnnotationSwaggerForElementAction(titleText);
            case DELETE_ANNOTATION_SWAGGER_FOR_FILE -> new DeleteElementAnnotationSwaggerForFileAction(titleText);
            case DELETE_ANNOTATION_TAG_FOR_ELEMENT -> new DeleteElementAnnotationTagForElementAction(titleText);
            case DELETE_ANNOTATION_TAG_FOR_FILE -> new DeleteElementAnnotationTagForFileAction(titleText);
            case DELETE_JAVA_DOC_FOR_ELEMENT -> new DeleteElementJavaDocForElementAction(titleText);
            case DELETE_JAVA_DOC_FOR_FILE -> new DeleteElementJavaDocForFileAction(titleText);
            case DELETE_ELEMENT_ALL_FOR_ELEMENT -> new DeleteElementAllForElementAction(titleText);
            case DELETE_ELEMENT_ALL_FOR_FILE -> new DeleteElementAllForFileAction(titleText);
        };
    }


}
