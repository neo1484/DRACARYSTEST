package Clases;

public class PrinterCommands 
{
	public static final byte[] INIT = {27, 64};
	public static byte[] FEED_LINE = {0x0A};

	public static byte[] SELECT_FONT_A = {27, 33, 0};

	public static byte[] SET_BAR_CODE_HEIGHT = {29, 104, 100};
	public static byte[] PRINT_BAR_CODE_1 = {29, 107, 2};
	public static byte[] SEND_NULL_BYTE = {0x00};

	public static byte[] SELECT_PRINT_SHEET = {0x1B, 0x63, 0x30, 0x02};
	public static byte[] FEED_PAPER_AND_CUT = {0x1D, 0x56, 66, 0x00};

	public static byte[] SELECT_CYRILLIC_CHARACTER_CODE_TABLE = {0x1B, 0x74, 0x11};

	//Configuration is (0x1B, 0x2A, densidad{0,1,32,33},{hex low},{hex high})
	public static byte[] SELECT_BIT_IMAGE_MODE = {0x1B, 0x2A, 33, (byte) 0xE1, 0x00};
	public static byte[] SET_LINE_SPACING_0 = {0x1B, 0x33, 0};
	public static byte[] SET_LINE_SPACING_10 = {0x1B, 0x33, 10};
	public static byte[] SET_LINE_SPACING_20 = {0x1B, 0x33, 20};

	public static byte[] TRANSMIT_DLE_PRINTER_STATUS = {0x10, 0x04, 0x01};
	public static byte[] TRANSMIT_DLE_OFFLINE_PRINTER_STATUS = {0x10, 0x04, 0x02};
	public static byte[] TRANSMIT_DLE_ERROR_STATUS = {0x10, 0x04, 0x03};
	public static byte[] TRANSMIT_DLE_ROLL_PAPER_SENSOR_STATUS = {0x10, 0x04, 0x04};
}
