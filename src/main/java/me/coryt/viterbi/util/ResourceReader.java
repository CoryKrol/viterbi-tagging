package me.coryt.viterbi.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class ResourceReader {
	public String asString(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public String readFileToString(String path) {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(ApplicationConstants.CLASSPATH_STRING + path);
		return asString(resource);
	}
	
	public String readFilesInDirToString(String path) throws IOException {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		List<Resource> resourceList = Arrays.asList(ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources("classpath*:" + path + "*"));
		
		String trainingData = "";
		for (Resource resource : resourceList) {
			trainingData += asString(resource) + "\n\n\n";
		}
		
		return trainingData;
	}
	
}
