package myCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class FieldLoader {

	public void save(Field field){
		try{
		 File file = new File("field.bin");
	     FileOutputStream fOutputStream = new FileOutputStream(file);
	     ObjectOutputStream oOutputStream = new ObjectOutputStream(fOutputStream);
	     oOutputStream.writeObject(field);
	     oOutputStream.close();
		}catch(IOException e){
			System.err.println("Nepodarilo sa ulozit hru");
		}
	}

	public Field load() { 
		Field field;
		try (FileInputStream fInputStream = new FileInputStream("field.bin");
				ObjectInputStream oInputStream = new ObjectInputStream(fInputStream);) {
			
				field = (Field) oInputStream.readObject();
				return field;
			
		} catch (ClassNotFoundException |IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
}
