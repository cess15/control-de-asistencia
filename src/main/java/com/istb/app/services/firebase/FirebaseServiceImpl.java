package com.istb.app.services.firebase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.StorageOptions;
import com.istb.app.models.FileUpload;

@Service
public class FirebaseServiceImpl implements FirebaseService {

	private StorageOptions options;

	@Value("${firebase.storage.bucket}")
	private String bucket;

	@Value("${firebase.storage.projectId}")
	private String projectId;

	@Value("${firebase.storage.privateKeyId}")
	private String privateKeyId;

	@Value("${firebase.storage.privateKey}")
	private String privateKey;

	@Value("${firebase.storage.clientX509CertUrl}")
	private String clientX509CertUrl;

	@Value("${firebase.storage.clientId}")
	private String clientId;

	@Value("${firebase.storage.clientEmail}")
	private String clientEmail;

	@Override
	public FileUpload uploadFile(MultipartFile multipartFile) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileUpload> uploadFiles(MultipartFile[] files) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteFile(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
	}

	private String getURL(String name, String destination) {
		return "https://storage.googleapis.com/".concat(this.bucket).concat("/").concat(destination).concat("/")
				.concat(name);
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
		FileOutputStream fos = new FileOutputStream(convertedFile);
		fos.write(file.getBytes());
		fos.close();
		return convertedFile;
	}
}
