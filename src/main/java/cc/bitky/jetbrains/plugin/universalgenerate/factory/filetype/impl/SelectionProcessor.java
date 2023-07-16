package cc.bitky.jetbrains.plugin.universalgenerate.factory.filetype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;

/**
 * @author bitkylin
 */
public class SelectionProcessor {

    public void doWrite(WriteContext writeContext) {
        GenerateUtils.generateSelection(writeContext);
    }
}
