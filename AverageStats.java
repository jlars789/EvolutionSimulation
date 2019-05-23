
public final class AverageStats{
	
	private WritableList stats;

	public AverageStats(WritableList stats) {
		this.stats = stats;
	}
	
	public double averageSize() {
		int size = 0;
		int j = 0;
		for(int i = 0; i < stats.getList().size(); i++) {
			size += stats.getList().get(i).getSize();
			j++;
		}
		
		if(j > 0) {
			return size / j;
		} else {
			return size;
		}
	}
	
	public double averageMitosis() {
		int mit = 0;
		int j = 0;
		for(int i = 0; i < stats.getList().size(); i++) {
			mit += stats.getList().get(i).getMitosisTime();
			j++;
		}
		
		if(j > 0) {
			return mit / j;
		} else {
			return mit;
		}
	}
	
	public double averageSpeed() {
		float spd = 0;
		int j = 0;
		for(int i = 0; i < stats.getList().size(); i++) {
			spd += stats.getList().get(i).getSpeed();
			j++;
		}
		
		if(j > 0) {
			return spd / j;
		} else {
			return spd;
		}
	}
	
	public double averageRange() {
		float rng = 0;
		int j = 0;
		for(int i = 0; i < stats.getList().size(); i++) {
			rng += stats.getList().get(i).getRange();
			j++;
		}
		
		if(j > 0) {
			return rng / j;
		} else {
			return rng;
		}
	}
	
	public double averageMetabolism() {
		double met = 0;
		int j = 0;
		for(int i = 0; i < stats.getList().size(); i++) {
			met += stats.getList().get(i).getMetabolism();
			j++;
		}
		
		if(j > 0) {
			return met / j;
		} else {
			return met;
		}
	}

}
