import java.io.ObjectOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
public class DataWriter {
	
	private static ObjectOutputStream creatureOos;
	private static Instrumentation ins;
	
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
			System.out.println("Creature Written");
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
		ObjectOutputStream statsOos = null;
		
		try {
			boolean isNew = false;
			
			System.out.println(ins.getObjectSize(s));
			
			if(!stats.exists()) {
				stats.createNewFile();
				isNew = true;
			}
			
			FileOutputStream fos = new FileOutputStream(stats);
			
			if(isNew) {
				statsOos = new ObjectOutputStream(fos);
			}
			
			if(s != null) {
				statsOos.writeObject(s);
				statsOos.flush();
				statsOos.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
