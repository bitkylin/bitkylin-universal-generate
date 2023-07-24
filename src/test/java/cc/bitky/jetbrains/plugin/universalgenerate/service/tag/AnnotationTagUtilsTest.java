package cc.bitky.jetbrains.plugin.universalgenerate.service.tag;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import static cc.bitky.jetbrains.plugin.universalgenerate.service.tag.AnnotationTagUtils.calcCurrentGroupBeginNum;
import static cc.bitky.jetbrains.plugin.universalgenerate.service.tag.AnnotationTagUtils.calcNextGroupBeginNum;

/**
 * @author bitkylin
 */
public class AnnotationTagUtilsTest extends BasePlatformTestCase {

    public void testCalcNextBeginNum() {
        assertEquals(200, calcNextGroupBeginNum(100, 100, 150));
        assertEquals(200, calcNextGroupBeginNum(100, 100, 101));
        assertEquals(200, calcNextGroupBeginNum(100, 100, 199));
        assertEquals(300, calcNextGroupBeginNum(100, 100, 200));
        assertEquals(300, calcNextGroupBeginNum(100, 100, 201));
        assertEquals(300, calcNextGroupBeginNum(100, 100, 299));

        assertEquals(201, calcNextGroupBeginNum(101, 100, 150));
        assertEquals(301, calcNextGroupBeginNum(101, 100, 250));
        assertEquals(601, calcNextGroupBeginNum(101, 100, 501));
    }

    public void testCalcGroupBeginNum() {
        assertEquals(100, calcCurrentGroupBeginNum(100, 100, 150));
        assertEquals(100, calcCurrentGroupBeginNum(100, 100, 101));
        assertEquals(100, calcCurrentGroupBeginNum(100, 100, 199));
        assertEquals(200, calcCurrentGroupBeginNum(100, 100, 200));
        assertEquals(200, calcCurrentGroupBeginNum(100, 100, 201));
        assertEquals(200, calcCurrentGroupBeginNum(100, 100, 299));

        assertEquals(101, calcCurrentGroupBeginNum(101, 100, 150));
        assertEquals(201, calcCurrentGroupBeginNum(101, 100, 250));
        assertEquals(501, calcCurrentGroupBeginNum(101, 100, 501));
    }
}