package tech.grasshopper.attachment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.qameta.allure.attachment.AttachmentData;
import io.restassured.internal.support.Prettifier;
import io.restassured.parsing.Parser;
import io.restassured.specification.MultiPartSpecification;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class ExtentHttpRequestAttachment implements AttachmentData {

	@NonNull
	private String name;

	@NonNull
	private String url;

	@NonNull
	private String method;

	private String body;

	@Default
	private Map<String, String> headers = new HashMap<>();

	@Default
	private Map<String, String> cookies = new HashMap<>();

	@Default
	private Map<String, String> requestParameters = new HashMap<>();

	@Default
	private Map<String, String> queryParameters = new HashMap<>();

	@Default
	private Map<String, String> formParameters = new HashMap<>();

	@Default
	private Map<String, String> pathParameters = new HashMap<>();

	private List<Map<String, String>> multiParts;

	public static class ExtentHttpRequestAttachmentBuilder {

		public ExtentHttpRequestAttachmentBuilder multiParts(List<MultiPartSpecification> parts) {
			multiParts = new ArrayList<>();

			for (MultiPartSpecification part : parts) {
				if (Objects.nonNull(part.getControlName())) {
					Map<String, String> data = new HashMap<>();
					this.multiParts.add(data);
					
					data.put("name", part.getControlName());
					data.put("mime", Objects.nonNull(part.getMimeType()) ? part.getMimeType() : "");
					data.put("file", Objects.nonNull(part.getFileName()) ? part.getFileName() : "");

					if (Objects.nonNull(part.getMimeType()) && Objects.nonNull(part.getContent())) {
						if (part.getContent() instanceof InputStream) {
							data.put("content", "<inputstream>");
						} else if (part.getContent() instanceof byte[]) {
							data.put("content", "<bytes>");
						} else {
							Parser parser = Parser.fromContentType(part.getMimeType());
							String prettified = new Prettifier().prettify(part.getContent().toString(), parser);
							data.put("content", prettified);
						}
					} else
						data.put("content", "");
				}
			}
			return this;
		}
	}
}
