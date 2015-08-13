import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class Main {

	private final static String HEADER = "GET /create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1\nHost: localhost:8080\nConnection: keep-alive\nAccept: */*";
	HttpRequest request = null;

	@Before
	public void before() {
		request = new HttpRequest(HEADER);
	}

	@Test
	public void runner() {
		assertNotNull(request);
	}

	@Test
	public void extractHttpMethod() throws Exception {
		List<String> httpMethods = new ArrayList<String>(
				Arrays.asList(new String[] { "GET", "HEAD", "PUT", "DELETE",
						"POST" }));

		String extractedMethod = request.extractMethod();
		assertNotNull(extractedMethod);
		assertTrue(httpMethods.contains(extractedMethod));
		assertEquals("GET", extractedMethod);
	}

	@Test
	public void extractUrl() throws Exception {
		String extractedUrl = request.extractUrl();
		assertNotNull(extractedUrl);
		assertEquals("/create", extractedUrl);
	}

	@Test
	public void extractParameters() throws Exception {
		Map<String, String> parameterMap = request.extractParameters();

		assertEquals(4, parameterMap.size());
		assertEquals("javajigi", parameterMap.get("userId"));
		assertEquals("javajigi%40slipp.net", parameterMap.get("email"));
	}

	@Test
	public void extractHttpVersion() throws Exception {
		String expected = "HTTP/1.1";
		assertEquals(expected, request.extractHttpVersion());
	}

	@Test
	public void analizeHeaderFields() throws Exception {
		Map<String, String> headerFields = request.getHeaderFields();
		assertEquals(3, headerFields.size());
		assertEquals("*/*", headerFields.get("Accept"));
	}
}
