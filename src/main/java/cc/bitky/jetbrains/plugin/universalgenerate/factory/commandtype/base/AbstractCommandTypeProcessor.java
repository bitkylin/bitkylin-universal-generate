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
        try {
            doWriteFile();
        } catch (Exception e) {
            log.error("写操作失败-writeFile : {}", getClass().getName());
        }
    }

    @Override
    public void writeElement() {
        try {
            doWriteElement();
        } catch (Exception e) {
            log.error("写操作失败-writeElement : {}", getClass().getName());
        }
    }

    public abstract void doWriteFile();

    public abstract void doWriteElement();

}
