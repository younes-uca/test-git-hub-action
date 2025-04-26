package ma.zyn.app.zynerator.transverse.cloud;


import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import io.minio.messages.Item;
import io.minio.messages.VersioningConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;

@Service
public class MinIOServiceImpl implements MinIOService {
    @Autowired
    private MinioClient minioClient;


    private String bucketName;

    @Override
    public boolean bucketExists(String name) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean objectExists(String bucketName, String objectName) throws MinioException {
        try {
            StatObjectResponse response = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return true; // Object exists
        } catch (MinioException e) {
            // Check if the exception is due to object not found
            if (e instanceof ErrorResponseException && ((ErrorResponseException) e).errorResponse().code().equals("NoSuchKey")) {
                return false; // Object not found
            }
            // If it's not NoSuchKey, throw the MinioException
            throw e;
        } catch (Exception e) {
            throw new MinioException("An error occurred while checking if the object " + objectName + " exists: " + e.getMessage());
        }
    }

    @Override
    public MinIOInfos uploadToMinio(MultipartFile file, String bucket) {
        if (!bucketExists(bucket))
            return null;
        try {
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            long size = file.getSize();
            InputStream inputStream = file.getInputStream();
            // Read the InputStream into a byte array
            byte[] bytes = file.getBytes();
            // Create a new InputStream from the byte array
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            String objectName = constructObjectName(originalFilename);
            // Upload the object using the new InputStream
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(objectName)
                            .stream(byteArrayInputStream, bytes.length, -1)
                            .contentType(contentType)
                            .build()
            );
            // Close the ByteArrayInputStream after use
            byteArrayInputStream.close();
            String etag = minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucket).object(objectName).build()
            ).etag();
            int resultStatus = (etag != null) ? 1 : 0;
            MinIOInfos minIOInfos = new MinIOInfos(bucket, originalFilename, size, bytes, etag, resultStatus);
            return minIOInfos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<MinIOInfos> uploadMultipleToMinio(List<MultipartFile> files, String bucket) {
        List<MinIOInfos> minIOInfosList = new ArrayList<>();
        if (!bucketExists(bucket))
            return null;
        for (MultipartFile file : files) {
            try {
                String originalFilename = file.getOriginalFilename();
                String contentType = file.getContentType();
                long size = file.getSize();
                InputStream inputStream = file.getInputStream();
                byte[] bytes = file.getBytes();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                String objectName = constructObjectName(originalFilename);
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName)
                                .stream(byteArrayInputStream, bytes.length, -1)
                                .contentType(contentType)
                                .build()
                );
                byteArrayInputStream.close();
                String etag = minioClient.statObject(
                        StatObjectArgs.builder().bucket(bucket).object(objectName).build()
                ).etag();

                int resultStatus = (etag != null) ? 1 : 0;
                MinIOInfos minIOInfos = new MinIOInfos(bucket, originalFilename, size, bytes, etag, resultStatus);
                minIOInfosList.add(minIOInfos);

            } catch (Exception e) {
                e.printStackTrace();
                // You might want to handle this differently, such as logging the error and continuing with the next file
            }
        }
        return minIOInfosList;
    }


    private String constructObjectName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    @Override
    public int saveBucket(String bucket) {
        if (bucketExists(bucket))
            return 0;
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            VersioningConfiguration config = new VersioningConfiguration(VersioningConfiguration.Status.ENABLED, false);
            minioClient.setBucketVersioning(SetBucketVersioningArgs.builder().bucket(bucket).config(config).build());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<String> getAllDocumentsNames(String bucket) throws MinioException {
        if (Boolean.FALSE.equals(bucketExists(bucket))) {
            throw new BucketNotFoundException("the bucket " + bucket + " does not exist");
        }
        List<String> documents = new ArrayList<>();
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucket).recursive(true).build());
            for (Result<Item> result : results) {
                Item item = result.get();
                documents.add(item.objectName());
            }
        } catch (Exception e) {
            throw new MinioException("Error while fetching files form the bucket " + bucket + ", error : " + e.getMessage());
        }
        return documents;
    }

    @Override
    public byte[] loadDocument(String bucket, String documentName) throws MinioException {
        if (!bucketExists(bucket)) {
            throw new BucketNotFoundException("The bucket " + bucket + " does not exist");
        }
        try {
            // Get the document object from MinIO
            GetObjectResponse response = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(documentName)
                            .build()
            );
            // Get the input stream containing the document data
            InputStream documentStream = response;
            // Create a byte array output stream to hold the document data
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // Buffer for reading data
            byte[] buffer = new byte[8192];
            int bytesRead;
            // Write the document data to the byte array output stream
            while ((bytesRead = documentStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            // Close the input stream for the document
            documentStream.close();

            // Return the document data as a byte array
            return baos.toByteArray();
        } catch (Exception e) {
            throw new MinioException("Error while downloading the document " + documentName + " from the bucket " + bucket + ", error : " + e.getMessage());
        }
    }
	
	
	@Override
    public int deleteMultiple(List<String> fileUrls) {
        return deleteMultiple(fileUrls,bucketName);
    }
	@Override
    public int deleteMultiple(List<String> fileUrls, String bucketName) {
        int result = -1;
		if (fileUrls == null || fileUrls.isEmpty()) {
            result = -1;
        }else{
			try {
				for (String fileUrl : fileUrls) {
					String objectName = getObjectNameFromUrl(fileUrl);
					if (objectName != null && !objectName.isEmpty()) {
						minioClient.removeObject(
								RemoveObjectArgs.builder()
										.bucket(bucketName)
										.object(objectName)
										.build()
						);
						
					}
				}
				result = 1;
			} catch (Exception e) {
				result = -2;
			}
		}
		return result;
    }


	// method to get object name from the url :
    private String getObjectNameFromUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            String path = url.getPath(); // Extracts "/backet-name/admin_1.png"

            // Remove the leading "/"
            path = path.startsWith("/") ? path.substring(1) : path;

            // Split the path to separate the bucket name
            String[] parts = path.split("/", 2);

            // Return only the object name
            return parts.length > 1 ? parts[1] : "";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public int setObjectTags(String bucketName, String objectName, Map<String, String> tags) throws MinioException {
        try {
            if (!bucketExists(bucketName)) {
                throw new BucketNotFoundException("The bucket " + bucketName + " does not exist");
            }
            if (!objectExists(bucketName, objectName)) {
                throw new BucketNotFoundException("The object " + bucketName + " does not exist in the bucket " + bucketName);
            }
            minioClient.setObjectTags(
                    SetObjectTagsArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .tags(tags)
                            .build()
            );
            return 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
	
	

	@Override
    public MinIOInfos uploadToMinioUsingByte(List<byte[]> fileBytesList, List<String> fileNames) {
		return uploadToMinioUsingByte(fileBytesList,fileNames,bucketName);
	}
	
	@Override
    public MinIOInfos uploadToMinioUsingByte(List<byte[]> fileBytesList, List<String> fileNames, String bucket) {
        if (fileBytesList.size() != fileNames.size()) {
            throw new IllegalArgumentException("fileBytesList and fileNames must have the same size");
        }
        if (!bucketExists(bucket)){
            return null;
        }

        List<String> urls = new ArrayList<>();
        String lastEtag = null;
        String lastFileName = "";
        byte[] lastFileBytes = null;
        int totalLength = 0;
        int resultStatus = 1;

        try {
            for (int i = 0; i < fileBytesList.size(); i++) {
                byte[] fileBytes = fileBytesList.get(i);
                String fileName = fileNames.get(i);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileBytes);

                // Detect content type
                String contentType = URLConnection.guessContentTypeFromStream(byteArrayInputStream);
                if (contentType == null) {
                    contentType = "application/octet-stream";
                }

                byteArrayInputStream.reset();

                // Upload to MinIO
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(fileName)
                                .stream(byteArrayInputStream, fileBytes.length, -1)
                                .contentType(contentType)
                                .build()
                );
                byteArrayInputStream.close();

                // Get ETag
                String etag = minioClient.statObject(
                        StatObjectArgs.builder().bucket(bucket).object(fileName).build()
                ).etag();
                lastEtag = etag;

                // Generate presigned URL
                String objectUrl = minioClient.getPresignedObjectUrl(
                        GetPresignedObjectUrlArgs.builder()
                                .bucket(bucket)
                                .object(fileName)
                                .method(Method.GET)
                                .expiry(7, TimeUnit.DAYS)
                                .build()
                );
                urls.add(objectUrl);

                // Track last file info
                lastFileName = fileName;
                lastFileBytes = fileBytes;
                totalLength += fileBytes.length;
            }

            String concatenatedUrls = String.join(", ", urls);
            return new MinIOInfos(bucket, lastFileName, totalLength, lastFileBytes, lastEtag, resultStatus, concatenatedUrls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
