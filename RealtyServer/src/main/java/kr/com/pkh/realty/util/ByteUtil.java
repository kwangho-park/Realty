package kr.com.pkh.realty.util;

public class ByteUtil
{
	public static long bytesToLong(byte[] src, int srcOff)
	{
		long word = 0;

		for (int i = 0; i <= 7; i++) {
			word = (word << 8) + (src[i + srcOff] & 0xff);
		}

		return word;
	}

	public static void longToBytes(byte[] dest, int destOff, long value)
	{
		for (int i = 0; i < 8; i++) {
			dest[i + destOff] = (byte) (value >> ((7 - i) * 8));
		}
	}

	public static int bytesToInt(byte[] src, int srcOff)
	{
		int word = 0;

		for (int i = 0; i <= 3; i++) {
			word = (word << 4) + (src[i + srcOff] & 0xff);
		}

		return word;
	}

	public static void intToBytes(byte[] dest, int destOff, int value)
	{
		for (int i = 0; i < 4; i++) {
			// 2009.06.04 주정민 수정
			// dest[i + destOff] = (byte)(value >> ((3 - i) * 4));
			dest[i + destOff] = (byte) (value >> ((3 - i) * 8));
		}
	}

	public static boolean equals(byte[] source, byte[] dest)
	{
		if (source.length != dest.length)
			return false;

		for (int i = 0; i < source.length; i++) {
			if (source[i] != dest[i])
				return false;
		}

		return true;
	}
}
