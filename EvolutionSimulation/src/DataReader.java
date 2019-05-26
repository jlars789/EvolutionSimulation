import java.io.*;

public class DataReader {
	
	private static ObjectInputStream dis;
	private static Creature creatureRead;
	
	private static final File file = new File("creatureData.dat");
	
	
	private static void createFile() {
		try {
			dis = new ObjectInputStream(new FileInputStream(file));
		}catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static WritableList readStats() {
		File stats = new File("stats.dat");
		if (stats.exists()) {
            WritableList stat = null;
            try {
                FileInputStream fis = new FileInputStream(stats);
                ObjectInputStream ois = new ObjectInputStream(fis);
                stat = (WritableList) ois.readObject();
                ois.close();   
            } catch (FileNotFoundException e) {
                e.printStackTrace();   
            } catch (EOFException e) {
            	
			} catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            return(stat);
        } else {
        	System.out.println("NULL");
        	DataWriter.createStats(new WritableList());
        	return readStats();
        }
	}
	
	public static void readCreature() {
		
		createFile();
		
		try {
			creatureRead = (Creature) dis.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (EOFException e) {
			System.out.println("No Save Data");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Creature creatureRead() {
		return creatureRead;
	}

}
