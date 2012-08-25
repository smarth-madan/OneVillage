package org.oneVillage.group.db;



import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ReadFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
			//System.out.print("hre2");
			File f = new File("resources/readid.txt");
			System.out.println("value1: ");
			FileChannel rwCh= new RandomAccessFile(f, "rw").getChannel();
			MappedByteBuffer mBuffer=rwCh.map(FileChannel.MapMode.READ_WRITE, 0, 20);

//			mBuffer.putInt(0, 45);
//			mBuffer.putInt(4, 67);

			System.out.println("value 1:"+mBuffer.getInt(0));
			System.out.println("value 2: "+mBuffer.getInt(4));
			System.out.println("value 3: "+mBuffer.getInt(8));
			System.out.println("value 4: "+mBuffer.getInt(12));
	}

	catch (Exception e) {
		System.out.println("value: "+e.getMessage());
		// TODO: handle exception
	}


}

	public static int getID(int index) {
		try {
			// System.out.print("hre2");
			File f = new File("readid.txt");
			System.out.println("value1: ");
			FileChannel rwCh = new RandomAccessFile(f, "rw").getChannel();
			MappedByteBuffer mBuffer = rwCh.map(FileChannel.MapMode.READ_WRITE,
					0, 24);


			System.out.println("value received from file: " + mBuffer.getInt(index));
			int idValue = mBuffer.getInt(index) + 1;


			mBuffer.putInt(index, idValue);
			mBuffer.force();

			return idValue;
		}

		catch (Exception e) {
			System.out.println("value: " + e.getMessage());

			// TODO: handle exception
		}
		return 0;
	}
	public static void setID() {
		try {
			// System.out.print("hre2");
			File f = new File("readid.txt");
			System.out.println("value1: ");
			FileChannel rwCh = new RandomAccessFile(f, "rw").getChannel();
			MappedByteBuffer mBuffer = rwCh.map(FileChannel.MapMode.READ_WRITE,
					0, 20);

			mBuffer.putInt(0, 0);
			mBuffer.putInt(4, 0);
			mBuffer.putInt(8, 0);
			mBuffer.putInt(12, 0);

			mBuffer.force();


		}

		catch (Exception e) {
			System.out.println("value: " + e.getMessage());

			// TODO: handle exception
		}

	}

}


