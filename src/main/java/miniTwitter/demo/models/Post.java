package miniTwitter.demo.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.*;

import javax.persistence.*;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long post_id;

   @NotEmpty
   private String content;
   
   @ManyToOne
   @JoinColumn(name="user_id")
   private User postedBy;
   
   
	private Date postedDate;
	
	private Boolean liked;


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public User getPostedBy() {
		return postedBy;
	}


	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}


	public Date getPostedDate() {
		return postedDate;
	}


	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}


	public long getPost_id() {
		return post_id;
	}


	public Boolean isLiked() {
		return liked;
	}


	public void setLiked(Boolean liked) {
		this.liked = liked;
	}

}