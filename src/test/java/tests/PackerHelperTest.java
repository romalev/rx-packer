package tests;

import com.mobiquityinc.datamodel.Item;
import com.mobiquityinc.utils.PackerHelper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Tests the PackerHelper functionality.
 * <p>
 * Created by RLYBD20 on 8/11/2017.
 */
public class PackerHelperTest {

    static PackerHelper helper;

    @BeforeClass
    public static void setUp() {
        helper = new PackerHelper();
    }

    @Test
    public void testGenerateCombinations() {
        final List<Item> inputItems = Arrays.asList(
                new Item(1, 53.38, 45d),
                new Item(2, 88.62, 98d),
                new Item(3, 78.48, 3d));

        final List<List<Item>> result = helper.generateCombinations(inputItems);
        // !!! we're only interested in index numbers !!!
        Assert.assertTrue(result.contains(Collections.singletonList(new Item(1, 0d, 0d))));
        Assert.assertTrue(result.contains(Collections.singletonList(new Item(2, 0d, 0d))));
        Assert.assertTrue(result.contains(Collections.singletonList(new Item(3, 0d, 0d))));
        Assert.assertTrue(result.contains(Arrays.asList(
                new Item(1, 0d, 0d),
                new Item(2, 0d, 0d))));
        Assert.assertTrue(result.contains(Arrays.asList(
                new Item(1, 0d, 0d),
                new Item(3, 0d, 0d))));
        Assert.assertTrue(result.contains(Arrays.asList(
                new Item(2, 0d, 0d),
                new Item(3, 0d, 0d))));
        Assert.assertTrue(result.contains(Arrays.asList(
                new Item(1, 0d, 0d),
                new Item(2, 0d, 0d),
                new Item(3,0d,0d))));

    }

}
