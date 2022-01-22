package beans;

import java.util.ArrayList;

public class Album {
	
	private int idAlbum;
	private String albumName;
	private String theme;
	private String detail;
	private String visibility;
	private String profil;
	private ArrayList <Image> images;
	private User owner;
	
	
	public Album() {
	}

	public Album(int idAlbum, String albumName, String theme, String detail, String visibility, String profil, User owner) {
		
		this(albumName, theme, detail, visibility, owner);
		this.profil  = profil;
		this.idAlbum = idAlbum;
	}
	
	public Album(int idAlbum, String albumName, String theme, String detail, String visibility, User owner) {
		
		this(albumName, theme, detail, visibility, owner);
		this.idAlbum = idAlbum;
	}
	
	public Album(String name, String theme, String detail, String visibility) {
		
		this.albumName = name;
		this.theme = theme;
		this.detail = detail;
		this.visibility = visibility;
		
	}

	public Album(String name, String theme, String detail, String visibility, User owner) {
		
		this(name, theme, detail, visibility);
		this.owner = owner;
		
	}
	
	public int getIdAlbum() {
		return idAlbum;
	}
	public void setIdAlbum(int idAlbum) {
		this.idAlbum = idAlbum;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String name) {
		this.albumName = name;
	}
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public ArrayList <Image> getImages() {
		return images;
	}

	public void setAlbumImages(ArrayList <Image> images) {
		this.images= images;
	}
	
	public void setAlbumImages(Image image) {
		this.images.add(image);
	}


	public String getProfil() {
		return profil;
	}


	public void setProfil(String profil) {
		this.profil = profil;
	}
}
