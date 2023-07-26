package cc.bitky.jetbrains.plugin.universalgenerate.factory;

import cc.bitky.jetbrains.plugin.universalgenerate.action.action.*;
import cc.bitky.jetbrains.plugin.universalgenerate.action.action.base.AbstractBitkylinUniversalGenerateAction;
import cc.bitky.jetbrains.plugin.universalgenerate.config.ActionConfig;
import cc.bitky.jetbrains.plugin.universalgenerate.constants.ActionEnum;

/**
 * @author bitkylin
 */
public class ActionFactory {

    public static AbstractBitkylinUniversalGenerateAction create(ActionConfig actionConfig, ActionEnum actionEnum) {
        String titleText = actionConfig.fetchActionTitleByActionEnum(actionEnum);
        return switch (actionEnum) {
            case MERGE_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT -> new MergeSwaggerToJavaDocForElementAction(titleText);
            case MERGE_SWAGGER_TO_JAVA_DOC_FOR_FILE -> new MergeSwaggerToJavaDocForFileAction(titleText);
            case PADDING_GENERATE_FOR_ELEMENT -> new PaddingGenerateForElementAction(titleText);
            case PADDING_GENERATE_FOR_FILE -> new PaddingGenerateForFileAction(titleText);
            case PADDING_SWAGGER_TO_JAVA_DOC_FOR_ELEMENT -> new PaddingSwaggerToJavaDocForElementAction(titleText);
            case PADDING_SWAGGER_TO_JAVA_DOC_FOR_FILE -> new PaddingSwaggerToJavaDocForFileAction(titleText);
            case RENEW_GENERATE_FOR_ELEMENT -> new RenewGenerateForElementAction(titleText);
            case RENEW_GENERATE_FOR_FILE -> new RenewGenerateForFileAction(titleText);
        };
    }


}
