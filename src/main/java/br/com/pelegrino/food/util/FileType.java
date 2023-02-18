package br.com.pelegrino.food.util;

public enum FileType {
	
	PNG("image/png", "png"),
	JPG("image/jpeg", "jpg");
	
	String mimeType;
	String extension;
	
	FileType(String mimeType, String extension) {
		this.mimeType = mimeType;
		this.extension = extension;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public boolean sameof(String mimeType) {
		return this.mimeType.equalsIgnoreCase(mimeType);
	}
	
	public static FileType of(String mimeType) {
		for (FileType fileType : values()) {
			if (fileType.sameof(mimeType)) {
				return fileType;
			}
		}
		return null;
	}

}
