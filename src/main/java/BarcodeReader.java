import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

import com.google.zxing.multi.GenericMultipleBarcodeReader;

/**
 * Created by qiz on 1/13/16.
 */
public class BarcodeReader {

    /**
     *  Barcode reader Try Harder
     *  Image IO has built-in support for GIF, PNG, JPEG, BMP, and WBMP; JAI support TIFF
     *  @param barCodeInputStream inputStream from image contains only one barcode and in horizontal
     *                            Image should not contain other characters
     *
     *  @return barcode String
     */

    public static String BarCodeReader (InputStream barCodeInputStream) throws Exception {

        BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);
        LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();
        Result result = reader.decode(bitmap);
        return result.getText();

    }

    /**
     *  Basic barcode reader
     *  Image IO has built-in support for GIF, PNG, JPEG, BMP, and WBMP; JAI support TIFF
     *  @param barCodeInputStream inputStream from image contains only one barcode
     *                            allows image contain other information or in vertical
     *  @return barcode String
     */

    public static String BarCodeReaderTryHarder (InputStream barCodeInputStream) throws Exception {

        BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);
        LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Map<DecodeHintType,Object> hints = new EnumMap<>(DecodeHintType.class);
        // Spend more time to try to find a barcode; optimize for accuracy, not speed.
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        //hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));

        Reader reader = new MultiFormatReader();
        Result result = reader.decode(bitmap, hints);
        return result.getText();
    }

    // Zxing's data-matrix detection algorithm assumes that the barcode is centered. Or more precisely, that the center of the image is inside the data-matrix.
    // Zxing's multiple reader specially fails when barcodes are grid-aligned.

    /**
     *  Multiple barcode reader Try Harder
     *  @param barCodeInputStream inputStream from image contains multiple barcode
     *                            allows image contain other information or in vertical
     *  @return barcode String
     */

    public static String[] MultipleBarCodeReaderTryHarder (InputStream barCodeInputStream) throws Exception {

        Map<DecodeHintType,Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);
        LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        Reader reader = new MultiFormatReader();
        GenericMultipleBarcodeReader multiReader = new GenericMultipleBarcodeReader(reader);
        Result[] result = multiReader.decodeMultiple(bitmap, hints);

        int numBarcodes = result.length;
        String[] barcodes = new String[numBarcodes];
        for (int i = 0; i < numBarcodes; i++) {
            barcodes[i] = result[i].getText();
            System.out.println(barcodes[i]);
        }
        return barcodes;
    }

}
