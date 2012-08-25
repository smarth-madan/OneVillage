package org.oneVillage.photo.resource;

import java.io.InputStream;
import java.io.OutputStream;

public class FileCopy {
	public static int copy(InputStream from, OutputStream to) {
		if (from == null || to == null)
			return -1;

		int n = 0, N = 0;
		try {
			byte[] chunk = new byte[7725];
			while ((n = from.read(chunk)) > 0) {
				to.write(chunk, 0, n);
				N += n;
			}
		} catch (Exception ex) {
			N = -1;
		}

		return N;
	}
}
