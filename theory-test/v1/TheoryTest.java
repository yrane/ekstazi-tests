import static org.hamcrest.CoreMatchers.is;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TheoryTest {
	@DataPoint
	public static int MARKET_FIRST_GOALSCORERE_ID = 2007;
	@DataPoint
	public static int MARKET_WDW_ID = 2008;
	@DataPoint
	public static BigDecimal PRICE_BD = new BigDecimal(6664.0);
	@DataPoint
	public static double PRICE_1 = 0.01;
	@DataPoint
	public static double PRICE_2 = 100.0;
	@DataPoint
	public static double PRICE_3 = 13999.99;
	@Theory
	public void lowTaxRateIsNineteenPercent(int market_id, double price) {
		Assume.assumeThat(market_id, is(2008));
		Assume.assumeThat(price, is(100.0));
		// run your test
		Assert.assertThat(price, is(100.0));
	}
	@Theory
	public void highTaxRateIsNineteenPercent(int market_id, double price) {
		Assume.assumeThat(market_id, is(2007));
		Assume.assumeThat(price, is(13999.99));
		Assert.assertThat(price, is(13999.99));
	}
	@Theory
	public void highTaxRateIsNineteenPercent(int market_id, BigDecimal price) {
		Assume.assumeThat(market_id, is(2007));
		Assert.assertThat(price, is(BigDecimal.valueOf(6664)));
	}
}
