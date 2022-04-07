package tech.grasshopper.filter;

import static io.qameta.allure.attachment.http.HttpResponseAttachment.Builder.create;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.internal.NameAndValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import tech.grasshopper.attachment.ExtentHttpRequestAttachment;
import tech.grasshopper.attachment.ExtentHttpRequestAttachment.ExtentHttpRequestAttachmentBuilder;
import tech.grasshopper.renderer.JsonAttachmentRenderer;

/**
 * Extent Allure logger filter for Rest-assured.
 */
public class ExtentRestAssuredFilter implements OrderedFilter {

	// private String requestTemplatePath = "extent-http-request.ftl";
	// private String responseTemplatePath = "extent-http-response.ftl";
	private String requestAttachmentName = "Request";
	private String responseAttachmentName = "Response";

	@Override
	public Response filter(final FilterableRequestSpecification requestSpec,
			final FilterableResponseSpecification responseSpec, final FilterContext filterContext) {
		final Prettifier prettifier = new Prettifier();

		ExtentHttpRequestAttachmentBuilder extentRequestAttachmentBuilder = ExtentHttpRequestAttachment.builder();
		extentRequestAttachmentBuilder.url(requestSpec.getURI()).name(requestAttachmentName)
				.method(requestSpec.getMethod()).headers(toMapConverter(requestSpec.getHeaders()))
				.cookies(toMapConverter(requestSpec.getCookies())).requestParameters(requestSpec.getRequestParams())
				.queryParameters(requestSpec.getQueryParams()).formParameters(requestSpec.getFormParams())
				.pathParameters(requestSpec.getNamedPathParams()).multiParts(requestSpec.getMultiPartParams());

		if (Objects.nonNull(requestSpec.getBody()))
			extentRequestAttachmentBuilder.body(prettifier.getPrettifiedBodyIfPossible(requestSpec));

		new DefaultAttachmentProcessor().addAttachment(extentRequestAttachmentBuilder.build(),
				new JsonAttachmentRenderer());

		final Response response = filterContext.next(requestSpec, responseSpec);

		final HttpResponseAttachment responseAttachment = create(responseAttachmentName)
				.setResponseCode(response.getStatusCode()).setHeaders(toMapConverter(response.getHeaders()))
				.setCookies(response.getCookies())
				.setBody(prettifier.getPrettifiedBodyIfPossible(response, response.getBody())).build();

		new DefaultAttachmentProcessor().addAttachment(responseAttachment, new JsonAttachmentRenderer());

		return response;
	}

	private static Map<String, String> toMapConverter(final Iterable<? extends NameAndValue> items) {
		final Map<String, String> result = new HashMap<>();
		items.forEach(h -> result.put(h.getName(), h.getValue()));
		return result;
	}

	@Override
	public int getOrder() {
		return Integer.MAX_VALUE;
	}
}
