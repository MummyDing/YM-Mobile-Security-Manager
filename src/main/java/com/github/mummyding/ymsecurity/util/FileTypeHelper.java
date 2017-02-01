package com.github.mummyding.ymsecurity.util;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by MummyDing on 2017/1/31.
 */

public class FileTypeHelper {

    public enum FileType {
        UNKNOWN, COMPRESS_FILE, APK_FILE, LARGE_FILE, VIDEO_FILE, AUDIO_FILE, IMAGE_FILE, DOCUMENT_FILE, THUMB_FILE
    }

    // comma separated list of all file extensions supported by the media scanner
    public static String sFileExtensions;

    // Audio file types
    public static final int FILE_TYPE_MP3     = 1;
    public static final int FILE_TYPE_M4A     = 2;
    public static final int FILE_TYPE_WAV     = 3;
    public static final int FILE_TYPE_AMR     = 4;
    public static final int FILE_TYPE_AWB     = 5;
    public static final int FILE_TYPE_WMA     = 6;
    public static final int FILE_TYPE_OGG     = 7;
    private static final int FIRST_AUDIO_FILE_TYPE = FILE_TYPE_MP3;
    private static final int LAST_AUDIO_FILE_TYPE = FILE_TYPE_OGG;

    // MIDI file types
    public static final int FILE_TYPE_MID     = 11;
    public static final int FILE_TYPE_SMF     = 12;
    public static final int FILE_TYPE_IMY     = 13;
    private static final int FIRST_MIDI_FILE_TYPE = FILE_TYPE_MID;
    private static final int LAST_MIDI_FILE_TYPE = FILE_TYPE_IMY;

    // Video file types
    public static final int FILE_TYPE_MP4     = 21;
    public static final int FILE_TYPE_M4V     = 22;
    public static final int FILE_TYPE_3GPP    = 23;
    public static final int FILE_TYPE_3GPP2   = 24;
    public static final int FILE_TYPE_WMV     = 25;
    private static final int FIRST_VIDEO_FILE_TYPE = FILE_TYPE_MP4;
    private static final int LAST_VIDEO_FILE_TYPE = FILE_TYPE_WMV;

    // Image file types
    public static final int FILE_TYPE_JPEG    = 31;
    public static final int FILE_TYPE_GIF     = 32;
    public static final int FILE_TYPE_PNG     = 33;
    public static final int FILE_TYPE_BMP     = 34;
    public static final int FILE_TYPE_WBMP    = 35;
    private static final int FIRST_IMAGE_FILE_TYPE = FILE_TYPE_JPEG;
    private static final int LAST_IMAGE_FILE_TYPE = FILE_TYPE_WBMP;

    // Playlist file types
    public static final int FILE_TYPE_M3U     = 41;
    public static final int FILE_TYPE_PLS     = 42;
    public static final int FILE_TYPE_WPL     = 43;
    private static final int FIRST_PLAYLIST_FILE_TYPE = FILE_TYPE_M3U;
    private static final int LAST_PLAYLIST_FILE_TYPE = FILE_TYPE_WPL;

    // apk
    private static final int FILE_TYPE_APK = 44;

    // Compressed file types
    private static final int FILE_TYPE_Z = 45;
    private static final int FILE_TYPE_ZIP = 46;
    private static final int FILE_TYPE_GZ = 47;
    private static final int FILE_TYPE_GZIP = 48;
    private static final int FILE_TYPE_TGZ = 49;
    private static final int FILE_TYPE_RAR = 50;
    private static final int FILE_TYPE_7Z = 51;
    private static final int FIRST_COMPRESS_FILE_TYPE = FILE_TYPE_Z;
    private static final int LAST_COMPRESS_FILE_TYPE = FILE_TYPE_7Z;

    // Document file types
    private static final int FILE_TYPE_DOC = 52;
    private static final int FILE_TYPE_DOCX = 53;
    private static final int FILE_TYPE_PDF = 54;
    private static final int FILE_TYPE_TXT = 55;
    private static final int FILE_TYPE_XLS = 56;
    private static final int FILE_TYPE_PPT = 57;
    private static final int FILE_TYPE_PPS = 58;
    private static final int FILE_TYPE_PPTX = 59;
    private static final int FILE_TYPE_HTML = 60;
    private static final int FILE_TYPE_HTM = 61;
    private static final int FIRST_DOCUMENT_FILE_TYPE = FILE_TYPE_DOC;
    private static final int LAST_DOCUMENT_FILE_TYPE = FILE_TYPE_HTM;

    static class MediaFileType {

        int fileType;
        String mimeType;

        MediaFileType(int fileType, String mimeType) {
            this.fileType = fileType;
            this.mimeType = mimeType;
        }
    }

    private static HashMap<String, MediaFileType> sFileTypeMap
            = new HashMap<String, MediaFileType>();
    private static HashMap<String, Integer> sMimeTypeMap
            = new HashMap<String, Integer>();
    static void addFileType(String extension, int fileType, String mimeType) {
        sFileTypeMap.put(extension, new MediaFileType(fileType, mimeType));
        sMimeTypeMap.put(mimeType, new Integer(fileType));
    }

    static {
        addFileType("MP3", FILE_TYPE_MP3, "audio/mpeg");
        addFileType("M4A", FILE_TYPE_M4A, "audio/mp4");
        addFileType("WAV", FILE_TYPE_WAV, "audio/x-wav");
        addFileType("AMR", FILE_TYPE_AMR, "audio/amr");
        addFileType("AWB", FILE_TYPE_AWB, "audio/amr-wb");
        addFileType("WMA", FILE_TYPE_WMA, "audio/x-ms-wma");
        addFileType("OGG", FILE_TYPE_OGG, "application/ogg");

        addFileType("MID", FILE_TYPE_MID, "audio/midi");
        addFileType("XMF", FILE_TYPE_MID, "audio/midi");
        addFileType("RTTTL", FILE_TYPE_MID, "audio/midi");
        addFileType("SMF", FILE_TYPE_SMF, "audio/sp-midi");
        addFileType("IMY", FILE_TYPE_IMY, "audio/imelody");

        addFileType("MP4", FILE_TYPE_MP4, "video/mp4");
        addFileType("M4V", FILE_TYPE_M4V, "video/mp4");
        addFileType("3GP", FILE_TYPE_3GPP, "video/3gpp");
        addFileType("3GPP", FILE_TYPE_3GPP, "video/3gpp");
        addFileType("3G2", FILE_TYPE_3GPP2, "video/3gpp2");
        addFileType("3GPP2", FILE_TYPE_3GPP2, "video/3gpp2");
        addFileType("WMV", FILE_TYPE_WMV, "video/x-ms-wmv");

        addFileType("JPG", FILE_TYPE_JPEG, "image/jpeg");
        addFileType("JPEG", FILE_TYPE_JPEG, "image/jpeg");
        addFileType("GIF", FILE_TYPE_GIF, "image/gif");
        addFileType("PNG", FILE_TYPE_PNG, "image/png");
        addFileType("BMP", FILE_TYPE_BMP, "image/x-ms-bmp");
        addFileType("WBMP", FILE_TYPE_WBMP, "image/vnd.wap.wbmp");

        addFileType("M3U", FILE_TYPE_M3U, "audio/x-mpegurl");
        addFileType("PLS", FILE_TYPE_PLS, "audio/x-scpls");
        addFileType("WPL", FILE_TYPE_WPL, "application/vnd.ms-wpl");

        addFileType("APK", FILE_TYPE_APK, "application/vnd.android");

        addFileType("Z", FILE_TYPE_Z, "application/x-compressed");
        addFileType("ZIP", FILE_TYPE_ZIP, "application/zip");
        addFileType("GZ", FILE_TYPE_GZ, "application/x-compressed");
        addFileType("GZIP", FILE_TYPE_GZIP, "application/x-gzip");
        addFileType("TGZ", FILE_TYPE_TGZ, "application/gnutar");
        addFileType("RAR", FILE_TYPE_RAR, "application/x-rar-compressed");
        addFileType("7Z", FILE_TYPE_7Z, "application/x-7z-compressed");

        addFileType("DOC", FILE_TYPE_DOC, "application/msword");
        addFileType("DOCX", FILE_TYPE_DOCX, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        addFileType("PDF", FILE_TYPE_PDF, "application/pdf");
        addFileType("TXT", FILE_TYPE_TXT, "text/plain");
        addFileType("XLS", FILE_TYPE_XLS, "application/excel");
        addFileType("PPT", FILE_TYPE_PPT, "application/mspowerpoint");
        addFileType("PPS", FILE_TYPE_PPS, "application/vnd.ms-powerpoint");
        addFileType("PPTX", FILE_TYPE_PPTX, "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        addFileType("HTM", FILE_TYPE_HTML, "text/html");
        addFileType("HTML", FILE_TYPE_HTM, "text/html");

        // compute file extensions list for native Media Scanner
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = sFileTypeMap.keySet().iterator();

        while (iterator.hasNext()) {
            if (builder.length() > 0) {
                builder.append(',');
            }
            builder.append(iterator.next());
        }
        sFileExtensions = builder.toString();
    }

    public static final String UNKNOWN_STRING = "<unknown>";

    public static boolean isAudioFileType(int fileType) {
        return ((fileType >= FIRST_AUDIO_FILE_TYPE &&
                fileType <= LAST_AUDIO_FILE_TYPE) ||
                (fileType >= FIRST_MIDI_FILE_TYPE &&
                        fileType <= LAST_MIDI_FILE_TYPE));
    }

    public static boolean isVideoFileType(int fileType) {
        return (fileType >= FIRST_VIDEO_FILE_TYPE &&
                fileType <= LAST_VIDEO_FILE_TYPE);
    }

    public static boolean isImageFileType(int fileType) {
        return (fileType >= FIRST_IMAGE_FILE_TYPE &&
                fileType <= LAST_IMAGE_FILE_TYPE);
    }

    public static boolean isPlayListFileType(int fileType) {
        return (fileType >= FIRST_PLAYLIST_FILE_TYPE &&
                fileType <= LAST_PLAYLIST_FILE_TYPE);
    }

    public static boolean isCompressFileType(int fileType) {
        return (fileType >= FIRST_COMPRESS_FILE_TYPE &&
                fileType <= LAST_COMPRESS_FILE_TYPE);
    }

    public static boolean isDocumentFileType(int fileType) {
        return (fileType >= FIRST_DOCUMENT_FILE_TYPE &&
                fileType <= LAST_DOCUMENT_FILE_TYPE);
    }

    public static boolean isApkFileType(int fileType) {
        return fileType == FILE_TYPE_APK;
    }

    public static MediaFileType getMediaFileType(String path) {
        int lastDot = path.lastIndexOf(".");
        if (lastDot < 0)
            return null;
        return sFileTypeMap.get(path.substring(lastDot + 1).toUpperCase());
    }

    public static FileType getFileType(String path) {
        MediaFileType type = getMediaFileType(path);
        if (type == null) {
            return FileType.UNKNOWN;
        }
        if (isVideoFileType(type.fileType)) {
            return FileType.VIDEO_FILE;
        } else if (isAudioFileType(type.fileType)) {
            return FileType.AUDIO_FILE;
        } else if (isImageFileType(type.fileType)) {
            return FileType.IMAGE_FILE;
        } else if (isApkFileType(type.fileType)) {
            return FileType.APK_FILE;
        } else if (isCompressFileType(type.fileType)) {
          return FileType.COMPRESS_FILE;
        } else if (isDocumentFileType(type.fileType)) {
          return FileType.DOCUMENT_FILE;
        } else if (isLargeFile(path)) {
            return FileType.LARGE_FILE;
        }
        return FileType.UNKNOWN;
    }

    private static boolean isLargeFile(String path) {
        // TODO: 2017/2/1 待补充
        return false;
    }

}
