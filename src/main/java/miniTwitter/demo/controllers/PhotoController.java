package miniTwitter.demo.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudinary.utils.ObjectUtils;

import miniTwitter.demo.configs.CloudinaryConfig;
import miniTwitter.demo.models.Photo;
import miniTwitter.demo.models.User;
import miniTwitter.demo.repositories.PhotoRepository;
import miniTwitter.demo.repositories.UserRepository;



@Controller
public class PhotoController {
		
	@Autowired
    CloudinaryConfig cloudc;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PhotoRepository photoRepository;
    
	
	   @GetMapping("/upload")
	    public String uploadForm(){
	        return "upload";
	    }

	    @PostMapping("/upload")
	    public String singleImageUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,Principal p, Model model){

	        if (file.isEmpty()){
	            redirectAttributes.addFlashAttribute("message","Please select a file to upload");
	            return "redirect:uploadStatus";
	        }

	        try {
	            Map uploadResult =  cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));

	            model.addAttribute("message", "You successfully uploaded '" + file.getOriginalFilename() + "'");
	            model.addAttribute("imageurl", uploadResult.get("url"));
	            String filename = uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString();
	            
	            
	            User user = userRepository.findByEmail(p.getName());
	            
	            Photo photo = new Photo();
	            photo.setImage(cloudc.createUrl(filename, 100, 150, "fit", "sepia"));
	            photo.setFileName(filename);
	            photo.setCreatedAt(new Date());
	            photo.setUser(user);
	            photoRepository.save(photo);
	           
	            
	            model.addAttribute("imagename", filename);
	            
	        } catch (IOException e){
	            e.printStackTrace();
	            model.addAttribute("message", "Sorry I can't upload that!");
	        }
	        return "upload";
	    }
	    
	    @RequestMapping("/filter")
	    public String filter(String imagename, int width, int height, String action,String filter, Model model){
	    	
	    	if(width==0 ){
	    		width=150;
	    	}
	    	if(height==0){
	    		height=150;
	    	}
	    	 model.addAttribute("sizedimageurl", cloudc.createUrl(imagename, width, height, action, filter));
	    	 
	    	 return "upload";
	    }
	    
	    @RequestMapping(path="/getphotos")
	    public String getGallery(Principal p, Model model){
	    	
	    	User user = userRepository.findByEmail(p.getName());
	    	
	    	List<Photo> pictures = photoRepository.findByUser_Id(user.getId());
	    	
	    	model.addAttribute("user", user);
	    	model.addAttribute("photos", pictures);
	  
	    	return "gallery";
	    }
	    
	    @RequestMapping(path="/delete/{photoId}")
	    public String deletePhoto(@PathVariable Long photoId, Principal p, Model model){
	    	
	    	User user = userRepository.findByEmail(p.getName());
	    	Photo photo = photoRepository.findOne(photoId);
	    	photoRepository.delete(photo);
	    	
	    	List<Photo> pictures = photoRepository.findByUser_Id(user.getId());
	    	model.addAttribute("user", user);
	    	model.addAttribute("photos", pictures);
	    	
	    	return "redirect: /newsfeed";
	    }
	    
	    @RequestMapping("/like/{photoId}")
	    public String likePic(@PathVariable("photoId") Long photoId, Principal principal, Model model){
	    	Photo pic = photoRepository.findOne(photoId);
	    	pic.setLiked(true);
	    	photoRepository.save(pic);
	    	return "newsfeed";
	    }

}
