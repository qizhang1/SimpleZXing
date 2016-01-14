/**
 * Created by qiz on 1/13/16.
 */
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;


import static org.junit.Assert.*;

public class BarcodeReaderTest {
    @Test
    public void testTiffBarcodeReader() throws Exception {
        try (InputStream barCodeInputStream = new FileInputStream("TestBarcode128.tiff")) {
            String barcode = BarcodeReader.BarCodeReader(barCodeInputStream);
            assertEquals("Test", barcode);
        }
    }

    @Test
    public void testPngBarcodeReader() throws Exception {
        try (InputStream barCodeInputStream = new FileInputStream("TestBarcode128.png")) {
            String barcode = BarcodeReader.BarCodeReader(barCodeInputStream);
            assertEquals("Test", barcode);
        }
    }


    @Test
    public void testSmallBarcode() throws Exception {
        try (InputStream barCodeInputStream = new FileInputStream("TestBarcode128_Image.png")) {
            String barcode = BarcodeReader.BarCodeReaderTryHarder(barCodeInputStream);
            assertEquals("Test", barcode);
        }
    }

    @Test (expected = com.google.zxing.NotFoundException.class)
    public void testSmallBarcode_NoFound() throws Exception {
        try (InputStream barCodeInputStream = new FileInputStream("TestBarcode128_Image.png")) { // Too small
            BarcodeReader.BarCodeReader(barCodeInputStream);
        }
    }

    @Test
    public void testEntityBarcode() throws Exception {
        try (InputStream barCodeInputStream = new FileInputStream("BarcodeEntityID.tiff")) {
            String barcode = BarcodeReader.BarCodeReader(barCodeInputStream);
            assertEquals("YBF405BB5AD", barcode);
        }
    }

    @Test
    public void testEntityBarcode_Vertical() throws Exception {
        try (InputStream barCodeInputStream = new FileInputStream("BarcodeEntityID_Vertical.tiff")) {
            String barcode = BarcodeReader.BarCodeReaderTryHarder(barCodeInputStream);
            assertEquals("YBF405BB5AD", barcode);
        }
    }

    @Test
    public void testMultipleBarcode_Vertical() throws Exception {
        try (InputStream barCodeInputStream = new FileInputStream("3Barcode39_Vertical.tiff")) {
            String[] barcodes = BarcodeReader.MultipleBarCodeReaderTryHarder(barCodeInputStream);
            assertEquals(3, barcodes.length);
            assertEquals("YBF405BB5AD", barcodes[0]);
            assertEquals("XF900E086E4", barcodes[1]);
            assertEquals("1/0F88D", barcodes[2]);
        }
    }

}
