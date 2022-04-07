package tech.grasshopper.renderer;

import java.io.StringWriter;
import java.io.Writer;

import com.google.gson.Gson;

import io.qameta.allure.attachment.AttachmentContent;
import io.qameta.allure.attachment.AttachmentData;
import io.qameta.allure.attachment.AttachmentRenderException;
import io.qameta.allure.attachment.AttachmentRenderer;
import io.qameta.allure.attachment.DefaultAttachmentContent;

public class JsonAttachmentRenderer implements AttachmentRenderer<AttachmentData> {

	@Override
	public AttachmentContent render(AttachmentData attachmentData) throws AttachmentRenderException {
		Gson gson = new Gson();

		try (Writer writer = new StringWriter()) {
			gson.toJson(attachmentData, writer);
			return new DefaultAttachmentContent(writer.toString(), "application/json", ".json");
		} catch (Exception e) {
			throw new AttachmentRenderException("Could't render http attachment file", e);
		}
	}
}