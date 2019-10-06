package ninja.bytecode.shuriken.objectstorage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import ninja.bytecode.shuriken.io.IO;

public class ObjectStorage
{
	private final AWSCredentialsProvider credentialsProvider;
	private final AWSCredentials credentials;
	private final AmazonS3 s3;
	private final String doAPIToken;
	private final String cdnEndpoint;
	private final String endpoint;
	private final String bucket;
	private final String root;
	private final String cdnID;

	public ObjectStorage(String authKey, String authSecret, String cdnEndpoint, String endpoint, String endpointRegion, String bucket, String root, String doAPIToken, String cdnID)
	{
		this.cdnEndpoint = cdnEndpoint;
		this.doAPIToken = doAPIToken;
		this.endpoint = endpoint;
		this.bucket = bucket;
		this.cdnID = cdnID;
		this.root = root.endsWith("/") ? root : "/";
		credentials = new BasicAWSCredentials(authKey, authSecret);
		credentialsProvider = new AWSStaticCredentialsProvider(credentials);
		s3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withEndpointConfiguration(new EndpointConfiguration(endpoint, endpointRegion)).build();
		assert s3.doesBucketExistV2(bucket) : " Bucket " + bucket + " does not exist";
	}

	public void purge(String path) throws IOException
	{
		URL url = new URL("https://api.digitalocean.com/v2/cdn/endpoints/" + cdnID + "/cache");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setDoInput(true);
		httpCon.setRequestProperty("Content-Type", "application/json");
		httpCon.addRequestProperty("Authorization", "Bearer " + doAPIToken);
		httpCon.setRequestMethod("DELETE");
		httpCon.connect();
		PrintWriter pw = new PrintWriter(httpCon.getOutputStream());
		pw.print("{\"files\": [\"" + path(path) + "\"]}");
		pw.flush();
		IO.readAll(httpCon.getInputStream());
		httpCon.disconnect();
	}

	public void write(String path, File f)
	{
		s3.putObject(bucket, path(path), f);
	}

	public InputStream read(String path)
	{
		return s3.getObject(bucket, path(path)).getObjectContent();
	}

	public long objectLength(String path)
	{
		return s3.getObject(bucket, path(path)).getObjectMetadata().getContentLength();
	}

	public boolean exists(String path)
	{
		return s3.doesObjectExist(bucket, path(path));
	}

	public void copy(String sourcePath, String destPath)
	{
		s3.copyObject(bucket, path(sourcePath), bucket, path(destPath));
	}

	public void delete(String path)
	{
		s3.deleteObject(bucket, path(path));
	}

	public String path(String path)
	{
		String p = root + (path.startsWith("/") ? path.substring(1) : path);
		return p.startsWith("/") ? p.substring(1) : p;
	}

	public String urlR(String p, long expiry)
	{
		return url(p, expiry, HttpMethod.GET);
	}

	public String urlW(String p, long expiry)
	{
		return url(p, expiry, HttpMethod.PUT, HttpMethod.DELETE);
	}

	public String url(String p, long expiry, HttpMethod... methods)
	{
		//@builder
		Date expiration = new Date();
		expiration.setTime(expiration.getTime() + expiry);
		GeneratePresignedUrlRequest g = new GeneratePresignedUrlRequest(bucket, path(p))
			.withExpiration(expiration);
		
		for(HttpMethod i : methods)
		{
			g.withMethod(i);
		}
				
		//@done
		String f = s3.generatePresignedUrl(g).toString();

		if(methods.length == 1 && methods[0].equals(HttpMethod.GET))
		{
			return f.replaceAll("\\Q" + bucket + "." + endpoint + "\\E", cdnEndpoint);
		}

		return f;
	}

	public AWSCredentialsProvider getCredentialsProvider()
	{
		return credentialsProvider;
	}

	public AWSCredentials getCredentials()
	{
		return credentials;
	}

	public AmazonS3 getS3()
	{
		return s3;
	}

	public String getDoAPIToken()
	{
		return doAPIToken;
	}

	public String getCdnEndpoint()
	{
		return cdnEndpoint;
	}

	public String getEndpoint()
	{
		return endpoint;
	}

	public String getBucket()
	{
		return bucket;
	}

	public String getRoot()
	{
		return root;
	}

	public String getCdnID()
	{
		return cdnID;
	}
}
