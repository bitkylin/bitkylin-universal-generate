package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base.AbstractCommandTypeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;

/**
 * 空处理器
 *
 * @author bitkylin
 */
public class CommandTypeEmptyProcessor extends AbstractCommandTypeProcessor implements ICommandTypeProcessor {

    public CommandTypeEmptyProcessor(WriteContext writeContext) {
    }

    @Override
    public void doWriteFile() {
    }

    @Override
    public void doWriteElement() {
    }

}
