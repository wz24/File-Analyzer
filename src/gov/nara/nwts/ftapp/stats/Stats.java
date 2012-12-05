package gov.nara.nwts.ftapp.stats;

import java.io.File;
import java.util.Vector;

import gov.nara.nwts.ftapp.filetest.FileTest;

/**
 * Base class for Stats objects containing a key and a variable lenght list of values.
 * @author TBrady
 *
 */
public class Stats {
	public static enum StatsItems implements StatsItemEnum {
		Type(StatsItem.makeStringStatsItem("Type"));
		
		StatsItem si;
		StatsItems(StatsItem si) {this.si=si;}
		public StatsItem si() {return si;}
	}

	//private to prevent use by subclass inner classes
	private static StatsItemConfig details = StatsItemConfig.create(StatsItems.class);
	static {
		details.hashCode();  //intentional no op
	}
	
	public StatsItem header;
	private Vector<Object> vals;
	public String key;
	
	public Stats(String key) {
		this.key = key;
		vals = new Vector<Object>();
	}

	public void setKeyVal(StatsItem si, Object val) {
		if (si == null) return;
		int index = si.getIndex();
		if (vals.size() > index) {
			vals.set(index,val);
		}		
	}
	
	public void setVal(StatsItemEnum eitem, Object val) {
		int index = eitem.si().getIndex();
		if ((index >= 0) && (vals.size() > index)) {
			vals.set(index,val);
		}
	}
	 
	public void sumVal(StatsItemEnum eitem, int val) {
		int index = eitem.si().getIndex();
		if ((index >= 0) && (vals.size() > index)) {
			Object obj = vals.get(index);
			if (obj == null) {
				vals.set(index,val);				
			} else if (obj instanceof Integer) {
				int n = (Integer)obj;
				n += val;
				vals.set(index, n);
			} else if (obj instanceof Float) {
				float n = (Float)obj;
				n += val;
				vals.set(index, n);
			} else if (obj instanceof Long) {
				long n = (Long)obj;
				n += val;
				vals.set(index, n);
			}
		}
	}
	 
	public void sumVal(StatsItemEnum eitem, float val) {
		int index = eitem.si().getIndex();
		if ((index >= 0) && (vals.size() > index)) {
			Object obj = vals.get(index);
			if (obj == null) {
				vals.set(index,val);				
			} else if (obj instanceof Float) {
				float n = (Float)obj;
				n += val;
				vals.set(index, n);
			}
		}
	}
	 
	public void sumVal(StatsItemEnum eitem, long val) {
		int index = eitem.si().getIndex();
		if ((index >= 0) && (vals.size() > index)) {
			Object obj = vals.get(index);
			if (obj == null) {
				vals.set(index,val);				
			} else if (obj instanceof Long) {
				long n = (Long)obj;
				n += val;
				vals.set(index, n);
			}
		}
	}
	 
	public void appendVal(StatsItemEnum eitem, String val) {
		int index = eitem.si().getIndex();
		if ((index >= 0) && (vals.size() > index)) {
			Object obj = vals.get(index);
			if (obj == null) {
				vals.set(index,val);				
			} else if (obj instanceof String) {
				String s = (String)obj;
				s += val;
				vals.set(index, s);
			}
		}
	}
	 
	public Vector<Object> getVals() {
		return vals;
	}
	
	public Object getVal(StatsItemEnum eitem) {
		return getVal(eitem, null);
	}
	
	public Long getLongVal(StatsItemEnum eitem) {
		return (Long)getVal(eitem, null);
	}

	public Object getVal(StatsItemEnum eitem, Object def) {
		int index = eitem.si().getIndex();
		if ((index >= 0) && (vals.size() > index)) {
			return vals.get(index);
		}
		return def;
	}
	 
	public <T extends Enum<T> & StatsItemEnum> void init(StatsItemConfig config) {
		boolean first = true;
		vals.clear();
		for(StatsItem item: config) {
			if (first) {
				first = false;
			} else {
				vals.add(item.initVal);
			}
		}
	}
	
	public Object compute(File f, FileTest fileTest) {
		Object o = fileTest.fileTest(f);
		return o;
	}
}
