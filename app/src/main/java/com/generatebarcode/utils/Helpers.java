package com.generatebarcode.utils;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.onbarcode.barcode.android.IBarcode;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

/**
 * Created by manjoor on 27-Sep-18.
 */

public class Helpers {
    public static List barcodeList() {
        List<Map> list = new ArrayList<>();
        Map map;
        map = new HashMap();
        map.put("code_name", "CODE-128");
        map.put("code_des", "Max 80 ASCII character");
        map.put("code_type", "" + IBarcode.CODE128);
        map.put("code_info", "" + ClassString.CODE128);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "CODE-39");
        map.put("code_des", "[A-Z]; [0-9]; [- . $ / + %]");
        map.put("code_type", "" + IBarcode.CODE39);
        map.put("code_info", "" + ClassString.CODE39);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "CODE-93");
        map.put("code_des", "[A-Z]; [0-9]; space; [- . $ / + %]");
        map.put("code_type", "" + IBarcode.CODE93);
        map.put("code_info", "" + ClassString.CODE93);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "EAN-13");
        map.put("code_des", "12 digit + 1 checksum [0-9]");
        map.put("code_type", "" + IBarcode.EAN13);
        map.put("code_info", "" + ClassString.EAN13);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "EAN-8");
        map.put("code_des", "7 digit + 1 checksum [0-9]");
        map.put("code_type", "" + IBarcode.EAN8);
        map.put("code_info", "" + ClassString.EAN8);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "CODABAR");
        map.put("code_des", "[0-9]; [- $ : / . +]");
        map.put("code_type", "" + IBarcode.CODABAR);
        map.put("code_info", "" + ClassString.CODABAR);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "UPC-E");
        map.put("code_des", "8 digit [0-9]");
        map.put("code_type", "" + IBarcode.UPCE);
        map.put("code_info", "" + ClassString.UPCE);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "UPC-A");
        map.put("code_des", "12 digit [0-9]");
        map.put("code_type", "" + IBarcode.UPCA);
        map.put("code_info", "" + ClassString.UPCA);
        list.add(map);
        map = new HashMap();
        map.put("code_name", "ITF-14");
        map.put("code_des", "13 digit + 1 checksum[0-9]");
        map.put("code_type", "" + IBarcode.ITF14);
        map.put("code_info", "" + ClassString.ITF14);
        list.add(map);
        return list;
    }

    public static List<String> barcodeStringList() {
        List<String> list = new ArrayList<>();
        list.add("Select Barcode Type");
        list.add("CODE-128 (code_des,Max 80 ASCII character)");
        list.add("CODE-39 ([A-Z]; [0-9]; [- . $ / + %])");
        list.add("CODE-93 ([A-Z]; [0-9]; [_ - . $ / + %])");
        list.add("EAN-13 (12 digit + 1 checksum [0-9])");
        list.add("EAN-8 (7 digit + 1 checksum [0-9])");
        list.add("CODABAR ([0-9]; [- $ : / . +])");
        list.add("UPC-E (8 digit [0-9])");
        list.add("UPC-A (12 digit [0-9])");
        list.add("ITF (13 digit + 1 checksum[0-9])");
        return list;
    }

    public static Bitmap encodeAsBitmap(String contents, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(contentsToEncode, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private static final String ALPHA_UPPER_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-.$/+% ";

    public static String randomAlphaNumericUpper(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_UPPER_NUMERIC_STRING.length());
            builder.append(ALPHA_UPPER_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private static final String NUMERIC = "0123456789";

    public static String randomNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * NUMERIC.length());
            builder.append(NUMERIC.charAt(character));
        }
        return builder.toString();
    }

    public static Bitmap barcodeBitmap(int code_type, String input_data,int width,int height) {
        Bitmap bitmap = null;
        if (code_type == IBarcode.CODE128) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.CODE_128, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
                System.out.println("ppppppppp  "+e.getMessage());
            }
        } else if (code_type == IBarcode.CODE39) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.CODE_39, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.CODE93) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.CODE_93, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.EAN13) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.EAN_13, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.EAN8) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.EAN_8, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.CODABAR) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.CODABAR, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.UPCE) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.UPC_E, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.UPCA) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.UPC_A, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else if (code_type == IBarcode.ITF14) {
            try {
                bitmap = Helpers.encodeAsBitmap(input_data, BarcodeFormat.ITF, width, height);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static String barcodeRandomString(int code_type) {
        String input_data = "";
        if (code_type == IBarcode.CODE128) {
            input_data = randomAlphaNumeric(12);
        } else if (code_type == IBarcode.CODE39) {
            input_data = randomAlphaNumericUpper(12);
        } else if (code_type == IBarcode.CODE93) {
            input_data = randomAlphaNumericUpper(12);
        } else if (code_type == IBarcode.EAN13) {
            input_data = randomNumeric(13);
        } else if (code_type == IBarcode.EAN8) {
            input_data = randomNumeric(8);
        } else if (code_type == IBarcode.CODABAR) {
            input_data = randomNumeric(12);
        } else if (code_type == IBarcode.UPCE) {
            input_data = randomNumeric(8);
        } else if (code_type == IBarcode.UPCA) {
            input_data = randomNumeric(12);
        } else if (code_type == IBarcode.ITF14) {
            input_data = randomNumeric(14);
        }
        return input_data;
    }
}
