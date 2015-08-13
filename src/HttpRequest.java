import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest {
	private String header;
	private Map<String, String> headerFields = new HashMap<>();

	public HttpRequest(String header) {
		this.header = header;
		analizeHeaderFields(header);
	}

	public String extractMethod() {
		return header.split(" ")[0];
	}

	public String extractUrl() {
		String regex = "/\\w+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(header);

		return matcher.find() ? matcher.group() : null;
	}

	public Map<String, String> extractParameters() {
		String headerFirstLine = getLine(0);
		String parameters = headerFirstLine.split("\\?")[1].split(" ")[0];
		Map<String, String> retMap = new HashMap<>();
		for (String parameter : parameters.split("&")) {
			String[] splited = parameter.split("=");
			retMap.put(splited[0], splited[1]);
		}
		return retMap;
	}

	public String extractHttpVersion() {
		String firstLine = getLine(0);
		String[] splited = firstLine.split(" ");
		return splited[splited.length - 1];
	}

	public Map<String, String> getHeaderFields() {
		return Collections.unmodifiableMap(headerFields);
	}
	
	private String getLine(int lineNumber) {
		return this.header.split("\n")[lineNumber];
	}
	
	private void analizeHeaderFields(String header) {
		for (int i = 1; i < getHttpHeaderLineCount(); i++) {
			String[] splited = getLine(i).split(":");
			this.headerFields.put(splited[0].trim(), splited[1].trim());
		}
	}

	private Integer getHttpHeaderLineCount() {
		return this.header.split("\n").length;
	}
}
