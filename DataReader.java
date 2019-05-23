import java.io.*;
import java.util.ArrayList;

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
            WritableList stat = new WritableList();
            try {
            	System.out.println("Called");
                FileInputStream fis = new FileInputStream(stats);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object obj = null;
                while((obj=ois.readObject()) != EOFException.class) {
                    if (obj instanceof Stats) {
                        stat.add((Stats)obj);
                    }
                }
                ois.close();
                 
                 
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                 
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            
            return(stat);
        } else {
        	DataWriter.createStats(new WritableList());
        	return readStats();
        }
		
		
		/*
		createStats();
		
		ArrayList<Stats> s = new ArrayList<Stats>();
		int j = 0;
		while(true) {
				try {
					s.add((Stats)dis.readObject());
					j++;
				} catch (StreamCorruptedException e) {
					DataWriter.overWrite();
					e.printStackTrace();
					break;
				} catch(EOFException e){
					System.out.println("End of File");
					break;
				} catch (ClassNotFoundException | IOException e) {
					e.printStackTrace();
				}
		}
		System.out.println(j);
		return s;
		*/
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
