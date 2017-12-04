package tests;

import com.mobiquityinc.builders.Builder;
import com.mobiquityinc.builders.SetBuilder;
import com.mobiquityinc.datamodel.Item;
import com.mobiquityinc.datamodel.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Contains a set of tests for set builder.
 * <p>
 * Created by RLYBD20 on 18/10/2017.
 */
public class SetBuilderTest {

    Builder<String, Set> builder;

    @Before
    public void setUp() {
        builder = new SetBuilder();
    }

    @Test
    public void testBuildingPackage() {
        final String inputLine = "81 : (1,53.38,E45) (2,88.62,E98) (3,78.48,E3)";

        final Set receivedPackage = builder.build(inputLine);

        final Integer expectedWeightPackageCanTake = 81;
        final List<Item> expectedItems = Arrays.asList(
                new Item(1, 53.38, 45d),
                new Item(2, 88.62, 98d),
                new Item(3, 78.48, 3d));

        final Set expectedPackage = new Set(expectedWeightPackageCanTake, expectedItems);

        Assert.assertEquals(receivedPackage, expectedPackage);
    }

}
