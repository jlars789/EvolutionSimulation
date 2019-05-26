import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
public class DataWriter {
	
	private static ObjectOutputStream creatureOos;
	
	private static final File file = new File("creatureData.dat");
	
	private static void createWriter() {
		if(!file.exists()) {
			System.out.println("File Created");
			try {
				creatureOos = new ObjectOutputStream(new FileOutputStream("creatureData.dat"));	
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				creatureOos = new ObjectOutputStream(new FileOutputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeOut(Creature c) {
		
		createWriter();
		
		try {
			creatureOos.writeObject(c);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				creatureOos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createStats(WritableList s) {
		
		File stats = new File("stats.dat");
		
		try {
			
			if(!stats.exists()) {
				stats.createNewFile();
			}
			
			FileOutputStream fos = new FileOutputStream(stats);
			ObjectOutputStream statsOos = new ObjectOutputStream(fos);
			
			if(s != null) {
				statsOos.writeObject(s);
				statsOos.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
