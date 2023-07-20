package cc.bitky.jetbrains.plugin.universalgenerate.factory.scope.impl;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.scope.ICommandScopeProcessor;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;
import cc.bitky.jetbrains.plugin.universalgenerate.util.GenerateUtils;

/**
 * @author bitkylin
 */
public class CommandScopeElementProcessor implements ICommandScopeProcessor {

    private final WriteContext writeContext;

    public CommandScopeElementProcessor(WriteContext writeContext) {
        this.writeContext = writeContext;
    }

    @Override
    public void process() {
        GenerateUtils.generateSelection(writeContext);
    }
}
