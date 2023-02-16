package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

//이미지업로드하는애 아니고
//그냥 저장하는애임

@Service
public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;

	public List<GalleryVo> getImages() {
		List<GalleryVo> vo = galleryRepository.getImages();
		return vo;
	}

	public void removeImage(Long no) {
		galleryRepository.removeImage(no);

	}

	public void addImage(GalleryVo vo) {
		galleryRepository.addImage(vo);
	}
}
