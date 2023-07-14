package cc.bitky.jetbrains.plugin.universalgenerate.factory;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.IFileTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.impl.FileTypeControllerProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.impl.FileTypeEntrancePojoProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.DecisionUtils;

/**
 * @author bitkylin
 */
public final class FileTypeProcessorFactory {

    private FileTypeProcessorFactory() {
    }

    public static IFileTypeProcessor decide(WriteContext writeContext) {
        boolean isController = DecisionUtils.isController(writeContext.getPsiClass());
        if (isController) {
            return new FileTypeControllerProcessor();
        } else {
            return new FileTypeEntrancePojoProcessor();
        }
    }

}
