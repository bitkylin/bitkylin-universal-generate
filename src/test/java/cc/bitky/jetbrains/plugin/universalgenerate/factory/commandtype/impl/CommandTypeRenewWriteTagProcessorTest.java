package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.impl;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

/**
 * @author bitkylin
 */
public class CommandTypeRenewWriteTagProcessorTest extends BasePlatformTestCase {

    public void testCalcNextBeginNum() {
        assertEquals(200, CommandTypeRenewWriteTagProcessor.calcNextBeginNum(100, 100, 150));
    }
}