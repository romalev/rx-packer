package tests;

import com.rx.packer.builders.Builder;
import com.rx.packer.builders.ItemsMessageBuilder;
import com.rx.packer.builders.SetBuilder;
import com.rx.packer.datamodel.Item;
import com.rx.packer.datamodel.Set;
import com.rx.packer.utils.PackerHelper;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tests the PackerHelper functionality.
 * <p>
 * Created by RLYBD20 on 8/11/2017.
 */
public class PackerHelperTest {

    private static final Logger LOG = Logger.getLogger(PackerHelperTest.class);

    private static PackerHelper helper;
    private static Builder<String, Set> builder;

    @BeforeClass
    public static void setUp() {
        helper = new PackerHelper();
        builder = new SetBuilder();
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
                new Item(3, 0d, 0d))));

    }

    @Test
    public void testLookUpBestOptionsForPackaging_GeneralCase() {
        // given
        final String inputLine = "20 : (1,10.38,E45) (2,9,E98) (3,15,E3) (4,10.37,E45) (5,9,E98)";
        final Builder<List<List<Item>>, String> niceItemsMessageBuilder = new ItemsMessageBuilder();
        // when
        final Set set = builder.build(inputLine);
        List<List<Item>> generateCombinations = helper.generateCombinations(set.getItems());
        LOG.trace(niceItemsMessageBuilder.build(generateCombinations));
        List<Item> itemList = helper.lookUpBestOptionsForPackaging(generateCombinations, set.getWeightPackageCanTake());


        List<Integer> indexes = itemList.stream().map(Item::getIndexNumber).collect(Collectors.toList());

        // then
        Assert.assertEquals(indexes, Arrays.asList(2, 5));
    }
}
