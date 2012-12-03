package gov.nara.nwts.ftapp.stats;

import java.io.File;
import java.util.Random;

import gov.nara.nwts.ftapp.YN;
import gov.nara.nwts.ftapp.filetest.FileTest;

/**
 * Generate a random value for each file in a set.
 * @author TBrady
 *
 */
public class RandomStats extends Stats {

	public static enum RandomStatsItems implements StatsItemEnum {
		Path(StatsItem.makeStringStatsItem("Path", 450)),
		Selected(StatsItem.makeEnumStatsItem(YN.class,"Selected").setInitVal(YN.N).setExport(false));
		
		StatsItem si;
		RandomStatsItems(StatsItem si) {this.si=si;}
		public StatsItem si() {return si;}
	}
	
	public static Object[][] details = StatsItem.toObjectArray(RandomStatsItems.class);
	long randomVal;
	Random random;
	
	public RandomStats(String key) {
		super(key);
		init(RandomStatsItems.class);
		random = new Random();
	}
	
	public Object compute(File f, FileTest fileTest) {
		randomVal = random.nextLong();
		if (fileTest instanceof Randomizer) {
			Randomizer r = (Randomizer)fileTest;
			while(r.getTreeSet().get(randomVal) != null) {
				randomVal = random.nextLong();				
			}
			r.getTreeSet().put(randomVal, key);
		}
		return randomVal;
	}
}