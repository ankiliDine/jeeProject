package beans;

import java.util.Date;

public class Image {
	
	private int idImage;
	private String title;
	private String description;
	private Date creationDate;
	private Date updateDate;
	private Double height;
	private Double width;
	private String keyWords;
	private String accessibility;
	private String imagePath;
	private Album album;
	
	
	public Image() {
		
	}


	public Image(String title, String description, Date creationDate, Date updateDate, Double height, Double width,
			String keyWord, String accessibility, String imagePath) {
		super();
		this.title = title;
		this.description = description;
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.height = height;
		this.width = width;
		this.keyWords = keyWord;
		this.accessibility = accessibility;
		this.imagePath = imagePath;
	}

	public Image( String title, String description, Date creationDate, Date updateDate, Double height,
			Double width, String keyWords, String accessibility, String imagePath, Album album) {

		this( title,  description,  creationDate,  updateDate,  height,  width, keyWords,  accessibility,  imagePath);
		this.album = album;
		
	}
	
	public Image( int idImage, String title, String description, Date creationDate, Date updateDate, Double height,
			Double width, String keyWords, String accessibility, String imagePath, Album album) {

		this( title,  description,  creationDate,  updateDate,  height,  width, keyWords,  accessibility,  imagePath);
		this.album = album;
		this.idImage = idImage;
	}
	public int getIdImage() {
		return idImage;
	}
	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public String getAccessibility() {
		return accessibility;
	}
	public void setAccessibility(String accessibility) {
		this.accessibility = accessibility;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
	
	
}
