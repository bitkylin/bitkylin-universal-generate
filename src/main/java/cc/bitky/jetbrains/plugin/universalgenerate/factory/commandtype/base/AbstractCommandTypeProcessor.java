package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base;

import cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.ICommandTypeProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bitkylin
 */
@Slf4j
public abstract class AbstractCommandTypeProcessor implements ICommandTypeProcessor {

    @Override
    public void writeFile() {
        doWriteFile();
    }

    @Override
    public void writeElement() {
        doWriteElement();
    }

    public abstract void doWriteFile();

    public abstract void doWriteElement();

}
