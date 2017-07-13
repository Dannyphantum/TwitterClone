package miniTwitter.demo.models;

import com.cloudinary.StoredFile;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long photoId;

    @Basic
    private String title;

    @Basic
    private String image;
    
    @Basic
    private String fileName;
    
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
    
    @Basic
    private Date createdAt = new Date();
    
    @Basic
    private Boolean liked;
    
    @Basic 
    private String filter;
    
    public Long getPhotoId() {
		return photoId;
	}

    
	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    
    public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public StoredFile getUpload() {
        StoredFile file = new StoredFile();
        file.setPreloadedFile(image);
        return file;
    }

    public void setUpload(StoredFile file) {
        this.image = file.getPreloadedFile();
    }


	public Boolean isLiked() {
		return liked;
	}


	public void setLiked(Boolean liked) {
		this.liked = liked;
	}


	public String getFilter() {
		return filter;
	}


	public void setFilter(String filter) {
		this.filter = filter;
	}
    
}
